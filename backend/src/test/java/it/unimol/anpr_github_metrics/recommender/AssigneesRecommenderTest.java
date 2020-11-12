package it.unimol.anpr_github_metrics.recommender;

import com.jcabi.github.Github;
import it.unimol.anpr_github_metrics.analytics.FixedIssuesAnalytics;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.Repository;
import it.unimol.anpr_github_metrics.beans.User;
import it.unimol.anpr_github_metrics.github.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Stefano Dalla Palma
 */
public class AssigneesRecommenderTest {
    public static final String RepositoryName = "grosa1/Spoon-Knife";
    public static final IssueExtractorFactory.InstantiationStrategy Environment = IssueExtractorFactory.InstantiationStrategy.PRODUCTION;

    @Before
    public void setup() {
        IssueExtractorFactory.setImplementorStrategy(Environment);
    }

    @Ignore
    @Test
    public void getRecommendation() throws Exception {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        Repository repository = new Repository();
        repository.setName(RepositoryName);

        User stefanodallapalma = new User();
        stefanodallapalma.setLogin("stefanodallapalma");
        stefanodallapalma.setUrl("https://github.com/stefanodallapalma");

        User intersimone999 = new User();
        intersimone999.setLogin("intersimone999");
        intersimone999.setUrl("https://github.com/intersimone999");

        User grosa1 = new User();
        grosa1.setLogin("grosa1");
        grosa1.setUrl("https://github.com/grosa1");


        ArrayList<User> users = new ArrayList<>();
        users.add(stefanodallapalma);
        users.add(intersimone999);
        users.add(grosa1);

        ArrayList<Issue> fixedIssues = new ArrayList<>(IssueExtractorFactory.getInstance(github).getFixedIssues(repository));

        AssigneesRecommender recommender = new AssigneesRecommender(github, users, fixedIssues);

        Issue issue = new Issue();
        // At the moment, only the title and the body are needed for recommendation
        issue.setTitle("This is a fixed issue");
        issue.setBody("How can I resolve a fixed issues with 1 comment and no label?");

        ArrayList<RecommendedUser> recommendation = recommender.getRecommendation(issue);

        System.out.println(recommendation.toString());

        assertEquals(ArrayList.class, recommendation.getClass());
        assertEquals(1, recommendation.size());
    }
}