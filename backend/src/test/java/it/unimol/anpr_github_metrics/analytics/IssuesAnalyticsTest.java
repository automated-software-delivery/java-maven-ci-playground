package it.unimol.anpr_github_metrics.analytics;

import com.jcabi.github.Github;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.Repository;
import it.unimol.anpr_github_metrics.github.Authenticator;
import it.unimol.anpr_github_metrics.github.AuthenticatorTest;
import it.unimol.anpr_github_metrics.github.IssueExtractorFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IssuesAnalyticsTest {
    public static final Github NullGithub = null;
    public static final String RepositoryName = "grosa1/Spoon-Knife";
    public static final IssueExtractorFactory.InstantiationStrategy Environment = IssueExtractorFactory.InstantiationStrategy.TESTING;

    @Before
    public void setup() {
        IssueExtractorFactory.setImplementorStrategy(Environment);
    }

    @Test
    public void getAverageFirstResponseTime() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        IssuesAnalytics analytics = new IssuesAnalytics(github);
        long meanFirstResponseTime = analytics.getAverageFirstResponseTime(repository.getName());

        switch (Environment){
                case TESTING:
                    assertTrue(meanFirstResponseTime >= 0);
                    assertEquals(18000000,meanFirstResponseTime);
                break;

            case PRODUCTION:
                //assertEquals(0,meanFirstResponseTime);
                break;
        }
    }

    @Test
    public void getAverageTimeFromLastComment() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        IssuesAnalytics analytics = new IssuesAnalytics(github);
        long averageTimeFromLastComment = analytics.getAverageTimeFromLastComment(repository.getName());

        switch (Environment){
            case TESTING:
                assertTrue(averageTimeFromLastComment >= 0);
                assertTrue(averageTimeFromLastComment >= 1879200000);
                System.out.println(averageTimeFromLastComment);
                break;

            case PRODUCTION:
                assertTrue(averageTimeFromLastComment >= 0);
                break;
        }
    }

    @Test
    public void getFirstResponseTimeDistribution() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);


        IssuesAnalytics analytics = new IssuesAnalytics(github);
        HashMap<Issue, Long> distribution = analytics.getFirstResponseTimeDistribution(repository.getName());
        assertEquals(HashMap.class, distribution.getClass());

        switch (Environment){
            case TESTING:
                assertEquals(6, distribution.size());
                break;

            case PRODUCTION:
                //assertEquals(0, distribution.size());
                break;
        }
    }

    @Test
    public void getTimesFromLastComment() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        IssuesAnalytics analytics = new IssuesAnalytics(github);
        HashMap<Issue, Long> issueTime = analytics.getTimesFromLastComment(repository.getName());
        assertEquals(HashMap.class, issueTime.getClass());
    }
}