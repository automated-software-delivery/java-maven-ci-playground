package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.Coordinates;
import com.jcabi.github.Github;
import com.jcabi.github.Repo;
import com.jcabi.github.Repos;
import it.unimol.anpr_github_metrics.github.Authenticator;
import it.unimol.anpr_github_metrics.github.AuthenticatorTest;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * @author Simone Scalabrino.
 */
public class GithubProxyTest {
    @Test
    public void testProxies() {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();

        assertTrue(github instanceof GithubProxy);

        Repos repos = github.repos();

        assertTrue(repos == github.repos());
    }

    @Test
    @Ignore
    public void testCommitProxies() {
        Github github = Authenticator.getInstance().authenticate(AuthenticatorTest.TOKEN).getGitHub();
        Repos repos = github.repos();

        Repo repo = repos.get(new Coordinates.Simple("intersimone999/xlint"));
        repo.commits().iterate(new HashMap<>());
        repo.commits().iterate(new HashMap<>());
    }
}