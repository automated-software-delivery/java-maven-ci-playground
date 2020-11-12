package it.unimol.anpr_github_metrics.analytics;

import com.jcabi.github.Github;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.Repository;
import it.unimol.anpr_github_metrics.github.GithubException;
import it.unimol.anpr_github_metrics.github.IssueExtractor;
import it.unimol.anpr_github_metrics.github.IssueExtractorFactory;

import java.util.ArrayList;

/**
 * This class is about open issues analytics
 * @author Stefano Dalla Palma
 */
public class OpenIssuesAnalytics extends IssuesAnalytics {

    public OpenIssuesAnalytics(Github github) {
        super(github);
    }


    /**
     * This method returns all open issues having comments
     *
     * @param repoName the name of the repository to analyze
     * @return an ArrayList of open issues that have at least one comment
     * @throws GithubException if an error in encountered with github api
     */
    public ArrayList<Issue> getCommentedOpenIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getOpenIssues(repository));
        issues.removeIf(issue -> issue.getComments().isEmpty());

        return issues;
    }


    /**
     * This method returns all open issues having labels
     *
     * @param repoName the name of the repository to analyze
     * @return an ArrayList of open issues that have at least one label
     * @throws GithubException if an error in encountered with github api
     */
    public ArrayList<Issue> getLabeledOpenIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getOpenIssues(repository));
        issues.removeIf(issue -> issue.getLabels().isEmpty());

        return issues;
    }


    /**
     * This method returns the number of open issues having comments
     *
     * @param repoName the name of the repository to analyze
     * @return an integer representing the number of open issues that have at least one comment
     * @throws GithubException if an error in encountered with github api
     */
    public int getNumberOfCommentedOpenIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        int numberOfCommentedIssues = 0;

        ArrayList<Issue> openIssues = new ArrayList<>(issueFactory.getOpenIssues(repository));
        for(Issue issue : openIssues){
            if(!issue.getComments().isEmpty()){
                numberOfCommentedIssues++;
            }
        }
        return numberOfCommentedIssues;
    }


    /**
     * This method returns the number of open issues having labels
     *
     * @param repoName the name of the repository to analyze
     * @return an integer representing the number of open issues that have at least one label
     * @throws GithubException if an error in encountered with github api
     */
    public int getNumberOfLabeledOpenIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        int numberOfLabeledIssues = 0;

        ArrayList<Issue> openIssues = new ArrayList<>(issueFactory.getOpenIssues(repository));
        for(Issue issue : openIssues){
            if(!issue.getLabels().isEmpty()){
                numberOfLabeledIssues++;
            }
        }
        return numberOfLabeledIssues;
    }


    /**
     * This method returns the number of ticket currently open
     *
     * @param repoName the name of the repository to analyze
     * @return an integer representing the number of tickets open
     * @throws GithubException if an error in encountered with github api
     */
    public int getNumberOfOpenIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        return issueFactory.getOpenIssues(repository).size();
    }

    /**
     * This method returns the number of open issues having no comments
     *
     * @param repoName the name of the repository to analyze
     * @return an integer representing the number of open issues that have no comments
     * @throws GithubException if an error in encountered with github api
     */
    public int getNumberOfUncommentedOpenIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        int numberOfUncommentedIssues = 0;

        ArrayList<Issue> openIssues = new ArrayList<>(issueFactory.getOpenIssues(repository));
        for(Issue issue : openIssues){
            if(issue.getComments().isEmpty()){
                numberOfUncommentedIssues++;
            }
        }
        return numberOfUncommentedIssues;
    }

    /**
     * This method returns the number of open issues having no label
     *
     * @param repoName the name of the repository to analyze
     * @return an integer representing the number of open issues that have no labels
     * @throws GithubException if an error in encountered with github api
     */
    public int getNumberOfUnlabeledOpenIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        int numberOfUnlabeledIssues = 0;

        ArrayList<Issue> openIssues = new ArrayList<>(issueFactory.getOpenIssues(repository));
        for(Issue issue : openIssues){
            if(issue.getLabels().isEmpty()){
                numberOfUnlabeledIssues++;
            }
        }
        return numberOfUnlabeledIssues;
    }


    /**
     * This method returns all the issues having no comments
     *
     * @param repoName the name of the repository to analyze
     * @return an ArrayList of open issues that have no comments
     * @throws GithubException if an error in encountered with github api
     */
    public ArrayList<Issue> getUncommentedOpenIssue(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getOpenIssues(repository));
        issues.removeIf(issue -> !issue.getComments().isEmpty());

        return issues;
    }

    /**
     * This method returns all the open issues having no labels
     *
     * @param repoName the name of the repository to analyze
     * @return an ArrayList of open issues that have no labels
     * @throws GithubException if an error in encountered with github api
     */
    public ArrayList<Issue> getUnlabeledOpenIssues(String repoName) throws GithubException {
        IssueExtractor issueFactory = IssueExtractorFactory.getInstance(this.github);
        Repository repository = new Repository();
        repository.setName(repoName);

        ArrayList<Issue> issues = new ArrayList<>(issueFactory.getOpenIssues(repository));
        issues.removeIf(issue -> !issue.getLabels().isEmpty());

        return issues;
    }

}
