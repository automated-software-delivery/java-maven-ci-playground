package it.unimol.anpr_github_metrics.github;

import it.unimol.anpr_github_metrics.beans.Commit;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.User;

import it.unimol.anpr_github_metrics.beans.Repository;

import java.util.Collection;
import java.util.List;

public interface IssueExtractor {

    /**
     * Get the list of all contributors of a repository
     * @param repository the repository
     * @return a list of User
     * @throws GithubException If there is any error in the GitHub APIs
     */
    public Collection<User> getContributors(Repository repository) throws GithubException;


    /**
     * Get the list of all the closed issues in a repository
     * @param repository the repository
     * @return a list of fixed issues
     * @throws GithubException If there is any error in the GitHub APIs
     */
    public Collection<Issue> getClosedIssues(Repository repository) throws GithubException;

    /**
     * Get the list of all the closed issues in a repository by a given user
     * @param user the user
     * @param repository the repository
     * @return a list of fixed issues
     * @throws GithubException If there is any error in the GitHub APIs
     */
    public Collection<Issue> getClosedIssues(User user, Repository repository) throws GithubException;


    /**
     * Get the list of all the fixed issues in a repository
     * @param repository the repository
     * @return a list of fixed issues
     * @throws GithubException If there is any error in the GitHub APIs
     */
    public Collection<Issue> getFixedIssues(Repository repository) throws GithubException;

    /**
     * Get the list of all the fixed issues in a repository by a given user
     * @param user the user
     * @param repository the repository
     * @return a list of fixed issues
     * @throws GithubException If there is any error in the GitHub APIs
     */
    public Collection<Issue> getFixedIssues(User user, Repository repository) throws GithubException;

    /**
     * Get the list of all the opened issues in a repository
     * @param repository Repository
     * @return All the open issues in the repository
     * @throws GithubException If there is any error in the GitHub APIs
     */
    public Collection<Issue> getOpenIssues(Repository repository) throws GithubException;


    /**
     * Get the list of all the issues in a repository opened by a given user
     * @param user The user who opened the issues
     * @param repository Repository
     * @return All the open issues opened by the user
     * @throws GithubException If there is any error in the GitHub APIs
     */
    public Collection<Issue> getOpenIssues(User user, Repository repository) throws GithubException;

    /**
     * Gets the list of all the commits involved in an issue
     * @param issue Issue
     * @return List of all the involved commits
     * @throws GithubException If there is any error in the GitHub APIs
     */
    public Collection<Commit> getCommitsInvolvedInIssue(Issue issue) throws GithubException;

    /**
     * Gets the list of all the issues of a repository
     * @param repository Repository
     * @return All the issues
     * @throws GithubException If there is any error in the GitHub APIs
     */
    public Collection<Issue> getIssues(Repository repository) throws GithubException;
}