package it.unimol.anpr_github_metrics.analytics;

import com.jcabi.github.Github;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.Repository;
import it.unimol.anpr_github_metrics.github.GithubException;
import it.unimol.anpr_github_metrics.github.IssueExtractor;
import it.unimol.anpr_github_metrics.github.IssueExtractorFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is about closed issues analytics
 * @author Stefano Dalla Palma
 */
public class ClosedIssuesAnalytics extends IssuesAnalytics {

    public ClosedIssuesAnalytics(Github github) {
        super(github);
    }


    /**
     * This method gets the mean time between a ticket creation and its closing
     *
     * @param repoName the name of the repository to analyze
     * @return a long indicating the average interval between the creation of a ticket and its closing
     * @throws GithubException if an error in encountered with github api
     */
    public long getAverageClosingTime(String repoName) throws GithubException {

        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getIssues(repository));

        long closingTime = 0L;
        int numberOfClosedIssues= 0;

        for (Issue issue : issues) {
            if (issue.getClosedTime() != null) {
                closingTime += issue.getClosedTime().getTime() - issue.getCreatedTime().getTime();
                numberOfClosedIssues++;
            }
        }

        return (numberOfClosedIssues > 0) ? closingTime/numberOfClosedIssues : 0;
    }


    /**
     * This method gets the distribution of the intervals between a ticket creation and its closing
     *
     * @param repoName the name of the repository to analyze
     * @return a HashMap indicating the interval between a ticket creation and its creation date (value) associated to a ticket (key)
     * @throws GithubException if an error in encountered with github api
     */
    public HashMap<Issue, Long> getClosingTimeDistribution(String repoName) throws GithubException {

        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getClosedIssues(repository));

        HashMap<Issue, Long> distribution = new HashMap<>();    //The ticket distribution over closing time

        issues.forEach(issue -> {
            long closingTime = issue.getClosedTime().getTime() - issue.getCreatedTime().getTime();
            distribution.put(issue, closingTime);
        });

        return distribution;
    }


    /**
     * This method returns all closed issues having comments
     *
     * @param repoName the name of the repository to analyze
     * @return an ArrayList of closed issues that have at least one comment
     * @throws GithubException if an error in encountered with github api
     */
    public ArrayList<Issue> getCommentedClosedIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getClosedIssues(repository));
        issues.removeIf(issue -> issue.getComments().isEmpty());

        return issues;
    }


    /**
     * This method returns all closed issues having labels
     *
     * @param repoName the name of the repository to analyze
     * @return an ArrayList of closed issues that have at least one label
     * @throws GithubException if an error in encountered with github api
     */
    public ArrayList<Issue> getLabeledClosedIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getClosedIssues(repository));
        issues.removeIf(issue -> issue.getLabels().isEmpty());

        return issues;
    }



    /**
     * This method returns the number of closed issues
     *
     * @param repoName the name of the repository to analyze
     * @return an integer representing the number of closed issues that have at least one comment
     * @throws GithubException if an error in encountered with github api
     */
    public int getNumberOfClosedIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        return issueFactory.getClosedIssues(repository).size();
    }

    /**
     * This method returns the number of closed issues having comments
     *
     * @param repoName the name of the repository to analyze
     * @return an integer representing the number of closed issues that have at least one comment
     * @throws GithubException if an error in encountered with github api
     */
    public int getNumberOfCommentedClosedIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        int numberOfCommentedIssues = 0;

        ArrayList<Issue> closedIssues = new ArrayList<>(issueFactory.getClosedIssues(repository));
        for(Issue issue : closedIssues){
            if(!issue.getComments().isEmpty()){
                numberOfCommentedIssues++;
            }
        }
        return numberOfCommentedIssues;
    }


    /**
     * This method returns the number of closed issues having labels
     *
     * @param repoName the name of the repository to analyze
     * @return an integer representing the number of closed issues that have at least one label
     * @throws GithubException if an error in encountered with github api
     */
    public int getNumberOfLabeledClosedIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        int numberOfLabeledIssues = 0;

        ArrayList<Issue> closedIssues = new ArrayList<>(issueFactory.getClosedIssues(repository));
        for(Issue issue : closedIssues){
            if(!issue.getLabels().isEmpty()){
                numberOfLabeledIssues++;
            }
        }
        return numberOfLabeledIssues;
    }


    /**
     * This method returns the number of closed issues having no comments
     *
     * @param repoName the name of the repository to analyze
     * @return an integer representing the number of closed issues that have no comments
     * @throws GithubException if an error in encountered with github api
     */
    public int getNumberOfUncommentedClosedIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        int numberOfUncommentedIssues = 0;

        ArrayList<Issue> closedIssues = new ArrayList<>(issueFactory.getClosedIssues(repository));
        for(Issue issue : closedIssues){
            if(issue.getComments().isEmpty()){
                numberOfUncommentedIssues++;
            }
        }
        return numberOfUncommentedIssues;
    }


    /**
     * This method returns the number of closed issues having no labels
     *
     * @param repoName the name of the repository to analyze
     * @return an integer representing the number of closed issues that have no labels
     * @throws GithubException if an error in encountered with github api
     */
    public int getNumberOfUnlabeledClosedIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        int numberOfUnlabeledIssues = 0;

        ArrayList<Issue> closedIssues = new ArrayList<>(issueFactory.getClosedIssues(repository));
        for(Issue issue : closedIssues){
            if(issue.getLabels().isEmpty()){
                numberOfUnlabeledIssues++;
            }
        }
        return numberOfUnlabeledIssues;
    }


    /**
     * This method returns all closed issues having no comments
     *
     * @param repoName the name of the repository to analyze
     * @return an ArrayList of closed issues that have no comments
     * @throws GithubException if an error in encountered with github api
     */
    public ArrayList<Issue> getUncommentedClosedIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getClosedIssues(repository));
        issues.removeIf(issue -> !issue.getComments().isEmpty());

        return issues;
    }

    /**
     * This method returns all the closed issues having no labels
     *
     * @param repoName the name of the repository to analyze
     * @return an ArrayList of closed issues that have no labels
     * @throws GithubException if an error in encountered with github api
     */
    public ArrayList<Issue> getUnlabeledClosedIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getClosedIssues(repository));
        issues.removeIf(issue -> !issue.getLabels().isEmpty());

        return issues;
    }
}
