package it.unimol.anpr_github_metrics.github;

import it.unimol.anpr_github_metrics.beans.*;

import java.util.*;

public class IssueExtractorStub implements IssueExtractor {

    private static final  Date creationDate = new Date(2017-1900, 9, 1),
            closedDate = new Date(2017-1900, 9, 1),
            dateComment = new Date(2017-1900, 9, 1) ;

    static {
        creationDate.setHours(0);
        creationDate.setMinutes(0);
        creationDate.setSeconds(0);

        closedDate.setHours(10);
        closedDate.setMinutes(0);
        closedDate.setSeconds(0);

        dateComment.setHours(5);
        dateComment.setMinutes(0);
        dateComment.setSeconds(0);
    }

    @Override
    public Collection<User> getContributors(Repository repository) throws GithubException {
        return null;
    }

    @Override
    public Collection<Issue> getClosedIssues(Repository repository) throws GithubException {
        // User
        User user = new User();
        user.setUrl("userUrl");
        user.setLogin("userLogin");

        // Comments
        Collection<IssueComment> comments;
        IssueComment comment = new IssueComment();
        comment.setAuthor(user);
        comment.setBody("This is a comment.");
        comment.setCreatedTime(dateComment);

        // Labels
        Collection<Issue.Label> labels = new HashSet<>();
        labels.add(Issue.Label.HELP);

        Collection<Issue> issues = new ArrayList<>();

        // Issues 1 - No comment, no label
        Issue issue1 = new Issue();
        issue1.setAuthor(user);
        issue1.setTitle("Uncommented and Unlabeled Closed Issue");
        issue1.setBody("This is an uncommented and unlabeled closed issue");
        issue1.setCreatedTime(creationDate);
        issue1.setClosedTime(closedDate);
        issue1.setComments(new ArrayList<>());   // No comments
        issue1.setLabels(new HashSet<>());       // No labels
        issue1.setRepository(repository);

        // Issues 2 - 1 Comment, no label
        Issue issue2 = new Issue();
        issue2.setAuthor(user);
        issue2.setTitle("Commented Closed Issue");
        issue2.setBody("This an closed issue with 1 comment and no label.");
        issue2.setRepository(repository);

        issue2.setCreatedTime(creationDate);
        issue2.setClosedTime(closedDate);

        comment.setIssue(issue2);
        comments = new ArrayList<>();
        comments.add(comment);
        issue2.setComments(comments);       // 1 comment
        issue2.setLabels(new HashSet<>());  // No labels

        // Issues 3 - No comment, 1 label
        Issue issue3 = new Issue();
        issue3.setAuthor(user);
        issue3.setTitle("Labeled Closed Issue");
        issue3.setBody("This an closed issue with 1 label and no comment.");
        issue3.setCreatedTime(creationDate);
        issue3.setClosedTime(closedDate);
        issue3.setRepository(repository);

        issue3.setComments(new ArrayList<>());   // No comment
        issue3.setLabels(labels);                // 1 label

        // Issues 4 - 1 comment, 1 label
        Issue issue4 = new Issue();
        issue4.setAuthor(user);
        issue4.setTitle("Labeled and Commented Closed Issue");
        issue4.setBody("This is a closed issue with one label and one comment");
        issue4.setCreatedTime(creationDate);
        issue4.setClosedTime(closedDate);
        issue4.setRepository(repository);

        comment.setIssue(issue4);
        comments = new ArrayList<>();
        comments.add(comment);
        issue4.setComments(comments);   // 1 comment
        issue4.setLabels(labels);       // 1 label

        issues.add(issue1);
        issues.add(issue2);
        issues.add(issue3);
        issues.add(issue4);

        return issues;
    }

    @Override
    public Collection<Issue> getClosedIssues(User user, Repository repository) throws GithubException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Collection<Issue> getFixedIssues(Repository repository) throws GithubException {
        // User
        User user = new User();
        user.setUrl("userUrl");
        user.setLogin("userLogin");

        // Comments
        Collection<IssueComment> comments;
        IssueComment comment = new IssueComment();
        comment.setAuthor(user);
        comment.setBody("This is a comment.");
        comment.setCreatedTime(dateComment);

        // Labels
        Collection<Issue.Label> labels = new HashSet<>();
        labels.add(Issue.Label.HELP);


        Collection<Issue> issues = new ArrayList<>();

        // Issues 1 - No comment, no label
        Issue issue1 = new Issue();
        issue1.setAuthor(user);
        issue1.setTitle("Uncommented and Unlabeled Fixed Issue");
        issue1.setBody("This is an uncommented and unlabeled fixed issue");
        issue1.setCreatedTime(creationDate);
        issue1.setClosedTime(closedDate);
        issue1.setComments(new ArrayList<>());   // No comments
        issue1.setLabels(new HashSet<>());       // No labels
        issue1.setFixed(true);

        // Issues 2 - 1 Comment, no label
        Issue issue2 = new Issue();
        issue2.setAuthor(user);
        issue2.setTitle("Commented Fixed Issue");
        issue2.setBody("This an fixed issue with 1 comment and no label.");
        issue2.setCreatedTime(creationDate);
        issue2.setClosedTime(closedDate);

        comment.setIssue(issue2);
        comments = new ArrayList<>();
        comments.add(comment);
        issue2.setComments(comments);       // 1 comment
        issue2.setLabels(new HashSet<>());  // No labels
        issue2.setFixed(true);

        // Issues 3 - No comment, 1 label
        Issue issue3 = new Issue();
        issue3.setAuthor(user);
        issue3.setTitle("Labeled Fixed Issue");
        issue3.setBody("This an fixed issue with 1 label and no comment.");
        issue3.setCreatedTime(creationDate);
        issue3.setClosedTime(closedDate);
        issue3.setComments(new ArrayList<>());   // No comment
        issue3.setLabels(labels);                // 1 label
        issue3.setFixed(true);

        // Issues 4 - 1 comment, 1 label
        Issue issue4 = new Issue();
        issue4.setAuthor(user);
        issue4.setTitle("Labeled and Commented Fixed Issue");
        issue4.setBody("This is a fixed issue with one label and one comment");
        issue4.setCreatedTime(creationDate);
        issue4.setClosedTime(closedDate);
        issue4.setFixed(true);

        comment.setIssue(issue4);
        comments = new ArrayList<>();
        comments.add(comment);
        issue4.setComments(comments);   // 1 comment
        issue4.setLabels(labels);       // 1 label

        issues.add(issue1);
        issues.add(issue2);
        issues.add(issue3);
        issues.add(issue4);

        return issues;
    }

    @Override
    public Collection<Issue> getFixedIssues(User user, Repository repository) throws GithubException {
        return null;
    }

    @Override
    public Collection<Issue> getOpenIssues(Repository repository) throws GithubException {

        // User
        User user = new User();
        user.setUrl("userUrl");
        user.setLogin("userLogin");

        // Comments
        Collection<IssueComment> comments;
        IssueComment comment = new IssueComment();
        comment.setAuthor(user);
        comment.setBody("This is a comment.");
        comment.setCreatedTime(dateComment);

        // Labels
        Collection<Issue.Label> labels = new HashSet<>();
        labels.add(Issue.Label.HELP);


        Collection<Issue> issues = new ArrayList<>();

        // Issues 1 - No comment, no label
        Issue issue1 = new Issue();
        issue1.setAuthor(user);
        issue1.setTitle("Uncommented and Unlabeled Open Issue");
        issue1.setBody("This is an uncommented and unlabeled open issue");
        issue1.setLabels(new HashSet<>());

        issue1.setCreatedTime(creationDate);

        issue1.setComments(new ArrayList<>());   // No comments
        issue1.setLabels(new HashSet<>());       // No labels

        // Issues 2 - 1 Comment, no label
        Issue issue2 = new Issue();
        issue2.setAuthor(user);
        issue2.setTitle("Commented Open Issue");
        issue2.setBody("This an open issue with 1 comment and no label.");

        issue2.setCreatedTime(creationDate);

        comment.setIssue(issue2);
        comments = new ArrayList<>();
        comments.add(comment);
        issue2.setComments(comments);       // 1 comment
        issue2.setLabels(new HashSet<>());  // No labels

        // Issues 3 - No comment, 1 label
        Issue issue3 = new Issue();
        issue3.setAuthor(user);
        issue3.setTitle("Labeled Open Issue");
        issue3.setBody("This an open issue with 1 label and no comment.");

        issue3.setCreatedTime(creationDate);

        issue3.setComments(new ArrayList<>());   // No comment
        issue3.setLabels(labels);                // 1 label

        // Issues 4 - 1 comment, 1 label
        Issue issue4 = new Issue();
        issue4.setAuthor(user);
        issue4.setTitle("Labeled and Commented Open Issue");
        issue4.setBody("This is a open issue with one label and one comment");

        issue4.setCreatedTime(creationDate);

        comment.setIssue(issue4);
        comments = new ArrayList<>();
        comments.add(comment);
        issue4.setComments(comments);   // 1 comment
        issue4.setLabels(labels);       // 1 label

        issues.add(issue1);
        issues.add(issue2);
        issues.add(issue3);
        issues.add(issue4);

        return issues;
    }

    @Override
    public Collection<Issue> getOpenIssues(User user, Repository repository) throws GithubException {
        throw  new RuntimeException("Not implemented");
    }

    @Override
    public Collection<Commit> getCommitsInvolvedInIssue(Issue issue) throws GithubException {
        throw new RuntimeException("Not implemented");
    }


    @Override
    public Collection<Issue> getIssues(Repository repository) throws GithubException {
        ArrayList<Issue> issues = new ArrayList<>();
        issues.addAll(getClosedIssues(repository));
        issues.addAll(getFixedIssues(repository));
        issues.addAll(getOpenIssues(repository));

        return issues;
    }
}
