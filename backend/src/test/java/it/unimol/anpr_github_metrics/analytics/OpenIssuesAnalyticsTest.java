package it.unimol.anpr_github_metrics.analytics;

import com.jcabi.github.Github;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.Repository;
import it.unimol.anpr_github_metrics.github.Authenticator;
import it.unimol.anpr_github_metrics.github.AuthenticatorTest;
import it.unimol.anpr_github_metrics.github.IssueExtractorFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author Stefano Dalla Palma.
 */
public class OpenIssuesAnalyticsTest {
    public static final Github NullGithub = null;
    public static final String RepositoryName = "grosa1/Spoon-Knife";
    public static final IssueExtractorFactory.InstantiationStrategy Environment = IssueExtractorFactory.InstantiationStrategy.TESTING;

    @Before
    public void setup() {
        IssueExtractorFactory.setImplementorStrategy(Environment);
    }

    @Test
    public void getCommentedOpenIssues() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);
        ArrayList<Issue> issues = analytics.getCommentedOpenIssues(repository.getName());

        assertEquals(ArrayList.class, issues.getClass());

        switch (Environment){
            case TESTING:
                assertEquals(2,issues.size());
                break;

            case PRODUCTION:
                assertEquals(1, issues.size());
                break;
        }
    }

    @Test
    public void getLabeledOpenIssues() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);
        ArrayList<Issue> issues = analytics.getCommentedOpenIssues(repository.getName());

        assertEquals(ArrayList.class, issues.getClass());

        switch (Environment){
            case TESTING:
                assertEquals(2,issues.size());
                break;

            case PRODUCTION:
                assertEquals(1, issues.size());
                break;
        }
    }

    @Test
    public void getNumberOfCommentedOpenIssues() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);
        int numberOfCommented = analytics.getNumberOfCommentedOpenIssues(repository.getName());

        switch (Environment){
            case TESTING:
                assertEquals(2,numberOfCommented);
                break;

            case PRODUCTION:
                assertEquals(1, numberOfCommented);
                break;
        }
    }

    @Test
    public void getNumberOfLabeledOpenIssues() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);
        int numberOflabeled = analytics.getNumberOfLabeledOpenIssues(repository.getName());

        switch (Environment){
            case TESTING:
                assertEquals(2,numberOflabeled);
                break;

            case PRODUCTION:
                assertEquals(1, numberOflabeled);
                break;
        }
    }

    @Test
    public void getNumberOfOpenIssues() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);
        int openIssues = analytics.getNumberOfOpenIssues(repository.getName());

        switch(Environment){
            case TESTING:
                assertEquals(4, openIssues);
                break;

            case PRODUCTION:
                assertEquals(3, openIssues);
                break;
        }
    }

    @Test
    public void getNumberOfUncommentedOpenIssues() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);
        int numberOfUncommented = analytics.getNumberOfUncommentedOpenIssues(repository.getName());

        switch(Environment){
            case TESTING:
                assertEquals(2,numberOfUncommented);
                break;

            case PRODUCTION:
                assertEquals(2,numberOfUncommented);
                break;
        }
    }

    @Test
    public void getNumberOfUnlabeledOpenIssues() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);
        int numberOfUnlabeled = analytics.getNumberOfUnlabeledOpenIssues(repository.getName());

        switch(Environment){
            case TESTING:
                assertEquals(2,numberOfUnlabeled);
                break;

            case PRODUCTION:
                assertEquals(2, numberOfUnlabeled);
                break;
        }
    }

    @Test
    public void getUncommentedOpenIssue() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);
        ArrayList<Issue> issues = analytics.getUncommentedOpenIssue(repository.getName());

        assertEquals(ArrayList.class, issues.getClass());

        switch(Environment){
            case TESTING:
                assertEquals(2, issues.size());
                break;

            case PRODUCTION:
                assertEquals(2, issues.size());
                break;
        }

    }

    @Test
    public void getUnlabeledOpenIssue() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        OpenIssuesAnalytics analytics = new OpenIssuesAnalytics(github);
        ArrayList<Issue> issues = analytics.getUnlabeledOpenIssues(repository.getName());

        assertEquals(ArrayList.class, issues.getClass());

        switch(Environment){
            case TESTING:
                assertEquals(2, issues.size());
                break;

            case PRODUCTION:
                assertEquals(2, issues.size());
                break;
        }

    }

}
