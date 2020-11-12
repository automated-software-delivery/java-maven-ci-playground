package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.*;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Simone Scalabrino.
 */

public class IssueProxy implements Issue, Proxy<Issue> {
    private final RepoProxy repo;
    private final Issue origin;
    private final Set<Event> events;
    private boolean invalidatedEventsCache;



    private JsonObject storedJson;
    private CommentsProxy commentsProxy;

    public IssueProxy(RepoProxy repo, Issue origin) {
        this.repo = repo;
        this.origin = origin;
        this.events = new HashSet<>();

        this.invalidatedEventsCache = false;
    }

    @Override
    public Repo repo() {
        return this.repo;
    }

    @Override
    public int number() {
        return this.origin.number();
    }

    @Override
    public Comments comments() {
        if (this.commentsProxy == null)
            this.commentsProxy = new CommentsProxy(this, this.origin.comments());

        return this.commentsProxy;
    }

    @Override
    public IssueLabels labels() {
        return this.origin.labels();
    }

    @Override
    public Iterable<Event> events() throws IOException {
        if (this.invalidatedEventsCache) {
            this.events.clear();
        }

        if (this.events.isEmpty()) {
            for (Event event : this.origin.events()) {
                this.events.add(new EventProxy(this.repo, event));
            }

            this.invalidatedEventsCache = false;

            new Thread(() -> {
                try {
                    //Invalidates the cache in 6 hours
                    Thread.sleep(6*60*60*1000);
                } catch (InterruptedException ignore) {
                }
                invalidate();
            }).start();
        }

        return this.events;
    }

    @Override
    public boolean exists() throws IOException {
        return this.origin.exists();
    }

    @Override
    public void patch(JsonObject jsonObject) throws IOException {
        this.origin.patch(jsonObject);
    }

    @Override
    public JsonObject json() throws IOException {
        if (this.storedJson == null)
            this.storedJson = this.origin.json();

        return this.storedJson;
    }

    @Override
    public Issue getOrigin() {
        return this.origin;
    }

    @Override
    public int compareTo(Issue issue) {
        return this.origin.compareTo(issue);
    }

    @Override
    public void invalidate() {
        this.invalidatedEventsCache = true;
    }
}
