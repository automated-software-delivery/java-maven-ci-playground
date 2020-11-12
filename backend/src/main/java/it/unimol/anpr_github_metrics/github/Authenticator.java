package it.unimol.anpr_github_metrics.github;

import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import it.unimol.anpr_github_metrics.github.proxies.GithubProxy;

/**
 * Authenticates a GitHub user
 * @author Simone Scalabrino.
 */

//TODO: gestire cache
public class Authenticator {
    public static final String TEST = "88fb84222da601030cc138a8d6b8aa179418ed18";
    private Github github;
    private String token;
    private static boolean cachingEnabled;


    static {
        cachingEnabled = true;
    }

    public static void enableCaching() {
        cachingEnabled = true;
    }

    public static void disableCaching() {
        cachingEnabled = false;
    }

    /**
     * Returns an authenticator instance
     * @return Instance of the authenticator
     */
    public static Authenticator getInstance() {
        return new Authenticator();
    }

    /**
     * Authenticates the user
     * @param username Username
     * @param password Password
     */
    public Authenticator authenticate(String username, String password) {
        Github realGithub = new RtGithub(username, password);
        if (cachingEnabled)
            this.github = new GithubProxy(realGithub);
        else
            this.github = realGithub;

        return this;
    }

    /**
     * Authenticates the user
     * @param oauth Authentication token
     * @return A Github object
     */
    public Authenticator authenticate(String oauth) {
        Github realGithub = new RtGithub(oauth);
        this.token = oauth;
        if (cachingEnabled)
            this.github = new GithubProxy(realGithub);
        else
            this.github = realGithub;

        return this;
    }

    public String getToken() {
        return this.token;
    }

    /**
     * Returns the Github object for queries
     * @return Github object
     */
    public Github getGitHub() {
        return this.github;
    }
}
