package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.Commit;
import com.jcabi.github.Repo;
import com.jcabi.github.RepoCommit;

import javax.json.JsonObject;
import java.io.IOException;

/**
 * @author Simone Scalabrino.
 */
public class RepoCommitProxy implements RepoCommit, Proxy<RepoCommit> {

    private final RepoProxy repo;
    private final RepoCommit origin;
    private JsonObject storedJson;

    public RepoCommitProxy(RepoProxy repo, RepoCommit origin) {
        this.repo = repo;
        this.origin = origin;
    }

    @Override
    public Repo repo() {
        return this.repo;
    }

    @Override
    public String sha() {
        return this.origin.sha();
    }

    @Override
    public JsonObject json() throws IOException {
        if (this.storedJson == null)
            this.storedJson = this.origin.json();

        return this.storedJson;
    }

    @Override
    public RepoCommit getOrigin() {
        return this.origin;
    }

    @Override
    public int compareTo(RepoCommit repoCommit) {
        return this.origin.compareTo(repoCommit);
    }
}
