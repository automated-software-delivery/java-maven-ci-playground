package it.unimol.anpr_github_metrics.github;

import com.jcabi.github.Repos;
import it.unimol.anpr_github_metrics.beans.Commit;
import it.unimol.anpr_github_metrics.beans.Issue;
import it.unimol.anpr_github_metrics.beans.Repository;
import it.unimol.anpr_github_metrics.beans.User;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Simone Scalabrino.
 */
public class IssueExtractorImplTest {

    @Before
    public void setup() {
        IssueExtractorFactory.setImplementorStrategy(IssueExtractorFactory.InstantiationStrategy.TESTING);
    }

    @Ignore
    @Test
    public void getContributors() throws Exception {
        IssueExtractorImpl implementor = new IssueExtractorImpl(Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub());

        Repository repository = new Repository();
        repository.setName("grosa1/Spoon-Knife");

        Collection<User> contributors = implementor.getContributors(repository);

        List<User> sortedContributors = new ArrayList<>(contributors);
        sortedContributors.sort(Comparator.comparing(User::getLogin));

        assertEquals(3, contributors.size());
        assertEquals("intersimone999", sortedContributors.get(0).getLogin());
        assertEquals("octocat", sortedContributors.get(1).getLogin());
    }

    @Ignore
    @Test
    public void getFixedIssues() throws Exception {
        IssueExtractorImpl implementor = new IssueExtractorImpl(Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub());

        Repository repository = new Repository();
        repository.setName("grosa1/Spoon-Knife");

        Collection<Issue> issues = implementor.getFixedIssues(repository);

        assertEquals(4, issues.size());
    }

    @Test
    public void getFixedIssuesWithUser() throws Exception {
        //TODO write test
    }

    @Ignore
    @Test
    public void getOpenIssues() throws Exception {
        IssueExtractorImpl implementor = new IssueExtractorImpl(Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub());

        Repository repository = new Repository();
        repository.setName("grosa1/Spoon-Knife");

        Collection<Issue> issues = implementor.getOpenIssues(repository);

        assertEquals(3, issues.size());
//        assertEquals("stefanodallapalma", new ArrayList<>(issues).get(0).getAuthor().getLogin());
    }

    @Test
    public void getOpenIssuesWithUser() throws Exception {
        //TODO write test
    }

    @Test
    public void getCommitsInvolvedInIssue() throws Exception {
        //TODO: test case fails because events older than 90 days are not reported!
//        IssueExtractorImpl implementor = new IssueExtractorImpl(Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub());
//
//        Repository repository = new Repository();
//        repository.setName("grosa1/Spoon-Knife");
//
//        List<Issue> issues = new ArrayList<>(implementor.getIssues(repository));
//
//        Optional<Issue> first = issues.stream().filter(Issue::isClosed).findFirst();
//        assertTrue(first.isPresent());
//
//        Collection<Commit> commitsInvolvedInIssue = implementor.getCommitsInvolvedInIssue(first.get());
//
//        assertEquals(2, commitsInvolvedInIssue.size());
//
//        List<Commit> sortedCommits = new ArrayList<>(commitsInvolvedInIssue);
//        sortedCommits.sort(Comparator.comparing(Commit::getHash));
//
//        assertEquals("05a0502774ea50f6b4ed195f33c8e443615d63ee", sortedCommits.get(0).getHash());
//        assertEquals("c428a7409a2ead4caf60bc6d47e2a487d06ab690", sortedCommits.get(1).getHash());
    }

}