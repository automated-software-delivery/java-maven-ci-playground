package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.*;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Simone Scalabrino.
 */
public class RepoCommitsProxy implements RepoCommits, Proxy<RepoCommits> {
    private final RepoProxy repo;
    private final RepoCommits origin;
    private final Map<String, RepoCommitProxy> commitsCache;
    private final IterableProxy<RepoCommit, String> commitsProxies;

    public RepoCommitsProxy(RepoProxy repo, RepoCommits origin) {
        this.repo = repo;
        this.origin = origin;
        this.commitsCache = new HashMap<>();
        this.commitsProxies = new IterableProxy<>();
    }

    @Override
    public Iterable<RepoCommit> iterate(Map<String, String> map) {
        return this.commitsProxies.getProxied(map,
                origin::iterate,
                commit -> {
                    RepoCommitProxy proxiedCommit = new RepoCommitProxy(repo, commit);
                    commitsCache.put(proxiedCommit.sha(), proxiedCommit);
                    return proxiedCommit;
                });
    }

    @Override
    public RepoCommit get(String s) {
        if (!this.commitsCache.containsKey(s))
            this.commitsCache.put(s, new RepoCommitProxy(this.repo, this.origin.get(s)));

        return this.commitsCache.get(s);
    }

    @Override
    public CommitsComparison compare(String s, String s1) {
        return this.origin.compare(s, s1);
    }

    @Override
    public String diff(String s, String s1) throws IOException {
        return this.origin.diff(s, s1);
    }

    @Override
    public String patch(String s, String s1) throws IOException {
        return this.origin.patch(s, s1);
    }

    @Override
    public JsonObject json() throws IOException {
        return this.origin.json();
    }

    @Override
    public RepoCommits getOrigin() {
        return this.origin;
    }
}
