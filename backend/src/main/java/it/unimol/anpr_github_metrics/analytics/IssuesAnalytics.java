package it.unimol.anpr_github_metrics.analytics;

import com.jcabi.github.Github;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.IssueComment;
import it.unimol.anpr_github_metrics.beans.Repository;
import it.unimol.anpr_github_metrics.github.GithubException;
import it.unimol.anpr_github_metrics.github.IssueExtractor;
import it.unimol.anpr_github_metrics.github.IssueExtractorFactory;

import java.util.*;

/**
 * This class implements the services' logic for analytics
 *
 * @author Stefano Dalla Palma.
 */
public class IssuesAnalytics {
    protected final Github github;

    public IssuesAnalytics(Github github) {
        this.github = github;
    }


    /**
     * This method gets the mean time between a ticket creation and the first comment
     *
     * @param repoName the name of the repository to analyze
     * @return a long indicating the average interval between the creation of a ticket and first comment expressed in milliseconds
     * @throws GithubException if an error in encountered with github api
     */
    public long getAverageFirstResponseTime(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getIssues(repository));

        long firstReponseTime = 0L;
        int numberOfComments = 0;

        for (Issue issue : issues) {
            List<IssueComment> comments = new ArrayList<>(issue.getComments());
            comments.sort(Comparator.comparing(IssueComment::getCreatedTime));

            if (comments.size() > 0) {
                firstReponseTime += comments.get(0).getCreatedTime().getTime() - issue.getCreatedTime().getTime();
                numberOfComments++;
            }
        }

        return (numberOfComments > 0) ? firstReponseTime/numberOfComments : 0;
    }

    /**
     * This method gets the average time between the last comment of tickets and the current time
     *
     * @param repoName the name of the repository to analyze
     * @return a long indicating the average interval between the last comment and the current time, expressed in milliseconds
     * @throws GithubException if an error in encountered with github api
     */
    public long getAverageTimeFromLastComment(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getIssues(repository));

        long timeFromLastComment = 0L;
        long now = new Date().getTime();
        int numberOfCommentedIssues = 0;

        for(Issue issue : issues){
            List<IssueComment> comments = new ArrayList<>(issue.getComments());
            comments.sort(Comparator.comparing(IssueComment::getCreatedTime));

            if(comments.size() > 0){
                timeFromLastComment += now - comments.get(comments.size()-1).getCreatedTime().getTime();
                numberOfCommentedIssues++;
            }
        }

        return numberOfCommentedIssues > 0 ? timeFromLastComment/numberOfCommentedIssues : 0;
    }

    /**
     * This method gets the distribution of the intervals between a ticket creation and the first comment
     *
     * @param repoName the name of the repository to analyze
     * @return a HashMap indicating the interval between a ticket creation and the first comment date (value) associated to a ticket (key)
     * @throws GithubException if an error in encountered with github api
     */
    public HashMap<Issue, Long> getFirstResponseTimeDistribution(String repoName) throws GithubException {

        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getIssues(repository));

        HashMap<Issue, Long> distribution = new HashMap<>();

        for (Issue issue : issues) {
            List<IssueComment> comments = new ArrayList<>(issue.getComments());
            comments.sort(Comparator.comparing(IssueComment::getCreatedTime));

            if (!comments.isEmpty()) {
                long firstResponseTime = comments.get(0).getCreatedTime().getTime() - issue.getCreatedTime().getTime();
                distribution.put(issue, firstResponseTime);
            }

        }

        return distribution;
    }

    /**
     * This method returns a map of issues and the associated time from latest comment
     *
     * @param repoName the name of the repository to analyze
     * @return an ArrayList of open issues that have no comment
     * @throws GithubException if an error in encountered with github api
     */
    public HashMap<Issue, Long> getTimesFromLastComment(String repoName) throws GithubException {
        long actualDate = new Date().getTime();
        HashMap<Issue, Long> map = new HashMap<>(); // The issue-inactivityTime map

        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);
        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getIssues(repository));

        for (Issue issue : issues) {
            if (issue.getUpdatedTime() != null) {
                long lastCommentDate = issue.getUpdatedTime().getTime();
                map.put(issue, actualDate - lastCommentDate);
            }
        }

        return map;
    }
}
