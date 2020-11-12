package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.Coordinates;
import com.jcabi.github.Github;
import com.jcabi.github.Repo;
import com.jcabi.github.Repos;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simone Scalabrino.
 */
public class ReposProxy implements Repos, Proxy<Repos> {
    private final GithubProxy github;
    private final Repos origin;
    private Map<String, Repo> cachedRepos;

    public ReposProxy(GithubProxy proxy, Repos origin) {
        this.github = proxy;
        this.origin = origin;
        this.cachedRepos = new HashMap<>();
    }

    @Override
    public Github github() {
        return this.github;
    }

    @Override
    public Repo create(RepoCreate repoCreate) throws IOException {
        return this.origin.create(repoCreate);
    }

    @Override
    public Repo get(Coordinates coordinates) {
        if (!this.cachedRepos.containsKey(coordinates.toString()))
            this.cachedRepos.put(coordinates.toString(), new RepoProxy(this.github, this.origin.get(coordinates), coordinates));

        return this.cachedRepos.get(coordinates.toString());
    }

    @Override
    public void remove(Coordinates coordinates) throws IOException {
        this.origin.remove(coordinates);
    }

    @Override
    public Iterable<Repo> iterate(String s) {
        return this.origin.iterate(s);
    }

    @Override
    public Repos getOrigin() {
        return this.origin;
    }
}
