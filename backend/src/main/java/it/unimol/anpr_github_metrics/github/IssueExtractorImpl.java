package it.unimol.anpr_github_metrics.github;


import com.jcabi.github.*;
import it.unimol.anpr_github_metrics.beans.*;
import it.unimol.anpr_github_metrics.beans.Commit;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.User;
import it.unimol.anpr_github_metrics.utils.DateUtils;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.IOException;
import java.security.cert.CollectionCertStoreParameters;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Simone Scalabrino.
 */
public class IssueExtractorImpl implements IssueExtractor {
    private Github github;

    public IssueExtractorImpl(Github github) {
        this.github = github;
    }

    public Collection<User> getContributors(Repository repository) throws GithubException {
        if (repository.getContributors() == null) {
            Repo remoteRepository = github.repos().get(new Coordinates.Simple(repository.getName()));

            Set<User> contributors = new HashSet<>();
            for (com.jcabi.github.RepoCommit remoteCommit : remoteRepository.commits().iterate(new HashMap<>())) {
                User user;
                try {
                    String login = remoteCommit.json().getJsonObject("author").getString("login");
                    user = loadUser(login);
                } catch (IOException e) {
                    throw new GithubException();
                }
                contributors.add(user);
            }
            repository.setContributors(contributors);
        }
        return repository.getContributors();
    }

    public Collection<Issue> getClosedIssues(Repository repository) throws GithubException {
        Collection<Issue> allIssues = this.getIssues(repository);

        return allIssues.stream().filter(Issue::isClosed).collect(Collectors.toList());
    }

    public Collection<Issue> getClosedIssues(User user, Repository repository) throws GithubException {
        Collection<Issue> allIssues = this.getIssues(repository);

        return allIssues.stream().filter(issue -> issue.isClosed() && issue.getAuthor().equals(user)).collect(Collectors.toList());
    }

    public Collection<Issue> getFixedIssues(Repository repository) throws GithubException {
        Collection<Issue> allIssues = this.getIssues(repository);

        return allIssues.stream().filter(Issue::isFixed).collect(Collectors.toList());
    }

    public Collection<Issue> getFixedIssues(User user, Repository repository) throws GithubException {
        Collection<Issue> allIssues = this.getIssues(repository);

        return allIssues.stream().filter(issue -> issue.isFixed() && issue.getAuthor().equals(user)).collect(Collectors.toList());
    }

    public Collection<Issue> getOpenIssues(Repository repository) throws GithubException {
        Collection<Issue> allIssues = this.getIssues(repository);

        return allIssues.stream().filter(issue -> !issue.isClosed()).collect(Collectors.toList());
    }

    public Collection<Issue> getOpenIssues(User user, Repository repository) throws GithubException {
        Collection<Issue> allIssues = this.getIssues(repository);

        return allIssues.stream().filter(issue -> !issue.isClosed() && issue.getAuthor().equals(user)).collect(Collectors.toList());
    }

    public Collection<Commit> getCommitsInvolvedInIssue(Issue issue) throws GithubException {
        Repo remoteRepository = github.repos().get(new Coordinates.Simple(issue.getRepository().getName()));

        com.jcabi.github.Issue remoteIssue = remoteRepository.issues().get(issue.getNumber());

        Collection<Commit> commits = new ArrayList<>();

        try {
            //Note that events older than 90 days are not reported!
            //TODO remove events dependency, just rely on issues, if possible
            for (Event event : remoteIssue.events()) {
                String eventType = event.json().getString("event");
                if (eventType.equals("closed") || eventType.equals("referenced")) {
                    if (!event.json().isNull("commit_id")) {
                        String commitId = event.json().getString("commit_id");

                        Commit commit = this.loadCommit(remoteRepository, commitId);
                        commits.add(commit);
                    }
                }
            }
        } catch (IOException e) {
            throw new GithubException();
        }

        return commits;
    }

    @Override
    public Collection<Issue> getIssues(Repository repository) throws GithubException {
        if (repository.getIssues() == null) {
            List<Issue> issues = new ArrayList<>();
            Repo remoteRepository = github.repos().get(new Coordinates.Simple(repository.getName()));
            Map<String, String> params = new HashMap<>();
            params.put("state", "all");

            ExecutorService executorService = Executors.newCachedThreadPool();
            Collection<Callable<Issue>> callables = new ArrayList<>();

            for (com.jcabi.github.Issue remoteIssue : remoteRepository.issues().iterate(params)) {
                callables.add(()->loadIssue(repository, remoteIssue));
            }

            try {
                List<Future<Issue>> futures = executorService.invokeAll(callables);

                for(Future<Issue> future : futures){
                    Issue issue = null;
                    try {
                        issue = future.get();
                        issues.add(issue);
                    } catch (InterruptedException | ExecutionException e) {
                        // Ignore the issue
                    }
                }

            } catch (InterruptedException e) {
                throw new GithubException();
            }

            repository.setIssues(issues);
        }
        return repository.getIssues();
    }

    private Commit loadCommit(Repo remoteRepository, String commitId) throws IOException {
        RepoCommit remoteCommit = remoteRepository.commits().get(commitId);
        return loadCommit(remoteCommit);
    }

    private Commit loadCommit(RepoCommit remoteCommit) throws IOException {
        JsonObject commitJson = remoteCommit.json();

        Collection<Commit.FileChange> changes = new ArrayList<>();
        JsonArray fileArray = commitJson.getJsonArray("files");
        for (JsonValue jsonValue : fileArray) {
            assert jsonValue instanceof JsonObject;
            JsonObject fileJson = (JsonObject) jsonValue;
            Commit.FileChange fileChange = new Commit.FileChange();

            fileChange.setFileName(fileJson.getString("filename"));
            fileChange.setAddedLines(fileJson.getInt("additions"));
            fileChange.setRemovedLines(fileJson.getInt("deletions"));

            changes.add(fileChange);
        }

        Commit commit = new Commit();
        commit.setHash(remoteCommit.sha());
        commit.setAuthor(loadUser(commitJson.getJsonObject("author").getString("login")));
        commit.setMessage(commitJson.getJsonObject("commit").getString("message"));
        commit.setChanges(changes);

        return commit;
    }

    private Issue loadIssue(Repository repository, com.jcabi.github.Issue remoteIssue) throws IOException, InterruptedException {
        JsonObject issueJson = remoteIssue.json();
        com.jcabi.github.Issue.Smart smartIssue = new com.jcabi.github.Issue.Smart(remoteIssue);

        Date lastClosedDate = null;
        Date lastReopenedDate = new Date(0);

        Date lastDuplicatedMarkDate = null;
        Date lastUnduplicatedMarkDate = new Date(0);

        boolean fixed = false;

        for (Event event : remoteIssue.events()) {
            switch (event.json().getString("event")) {
                case "closed":
                    Date newClosed = DateUtils.getMandatoryDate(event.json().getString("created_at"));
                    if (lastClosedDate == null || newClosed.compareTo(lastClosedDate) > 0)
                        lastClosedDate = newClosed;

                    if (!event.json().isNull("commit_id")) {
                        fixed = true;
                    }

                    break;
                case "reopened":
                    Date newReopenedDate = DateUtils.getMandatoryDate(event.json().getString("created_at"));
                    if (newReopenedDate.compareTo(lastReopenedDate) > 0)
                        lastReopenedDate = newReopenedDate;
                    break;
                case "marked_as_duplicated":
                    Date newDuplicated = DateUtils.getMandatoryDate(event.json().getString("created_at"));
                    if (lastDuplicatedMarkDate == null || newDuplicated.compareTo(lastDuplicatedMarkDate) > 0)
                        lastDuplicatedMarkDate = newDuplicated;
                    break;
                case "unmarked_as_duplicated":
                    Date newUnduplicated = DateUtils.getMandatoryDate(event.json().getString("created_at"));
                    if (newUnduplicated.compareTo(lastUnduplicatedMarkDate) > 0)
                        lastUnduplicatedMarkDate = newUnduplicated;
                    break;
            }
        }

        Issue issue = new Issue();
        issue.setNumber(remoteIssue.number());
        issue.setAuthor(loadUser(issueJson.getJsonObject("user").getString("login")));

        issue.setUrl(issueJson.getString("html_url"));
        issue.setTitle(issueJson.getString("title"));
        issue.setBody(issueJson.getString("body"));

        issue.setCreatedTime(DateUtils.getMandatoryDate(issueJson.getString("created_at")));
        issue.setUpdatedTime(DateUtils.getOptionalDate(issueJson.getString("updated_at")));
        if (!issueJson.isNull("closed_at"))
            issue.setClosedTime(DateUtils.getMandatoryDate(issueJson.getString("closed_at")));

        issue.setFixed(fixed);

        issue.setClosed(!smartIssue.isOpen());

        issue.setLocked(issueJson.getBoolean("locked"));


        ExecutorService executorService = Executors.newCachedThreadPool();
        Collection<Callable<IssueComment>> commentCallables = new ArrayList<>();

        Collection<IssueComment> comments = new ArrayList<>();
        for (Comment remoteComment : remoteIssue.comments().iterate(issue.getCreatedTime())) {
            commentCallables.add(()->{
                JsonObject commentJson = remoteComment.json();

                IssueComment comment = new IssueComment();
                comment.setAuthor(loadUser(commentJson.getJsonObject("user").getString("login")));
                comment.setBody(commentJson.getString("body"));
                comment.setCreatedTime(DateUtils.getMandatoryDate(commentJson.getString("created_at")));
                comment.setUpdatedTime(DateUtils.getMandatoryDate(commentJson.getString("updated_at")));
                comment.setUrl(commentJson.getString("html_url"));
                comment.setIssue(issue);

                return comment;
            });
        }

        List<Future<IssueComment>> futures = null;
        futures = executorService.invokeAll(commentCallables);

        for(Future<IssueComment> future : futures){
            IssueComment comment = null;
            try {
                comment = future.get();
                comments.add(comment);
            } catch (InterruptedException | ExecutionException e) {
                // Ignore the comment
            }
        }


        issue.setComments(comments);
        issue.setRepository(repository);

        Collection<Issue.Label> labels = new HashSet<>();
        for (Label label : remoteIssue.labels().iterate()) {
            if (label.name().equalsIgnoreCase("bug")) {
                labels.add(Issue.Label.BUG);
            } else if (label.name().equalsIgnoreCase("enhancement")) {
                labels.add(Issue.Label.ENHANCEMENT);
            } else if (label.name().equalsIgnoreCase("help wanted")) {
                labels.add(Issue.Label.HELP);
            } else if (label.name().equalsIgnoreCase("question")) {
                labels.add(Issue.Label.QUESTION);
            } else if (label.name().equalsIgnoreCase("invalid")) {
                issue.setInvalid(true);
            }
        }
        issue.setLabels(labels);

        return issue;
    }

    private User loadUser(String userLogin) throws IOException {
        com.jcabi.github.User remoteUser = this.github.users().get(userLogin);
        User user = new User();
        user.setLogin(userLogin);
        user.setUrl(remoteUser.json().getString("html_url"));

        return user;
    }
}
