package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.Event;
import com.jcabi.github.Repo;

import javax.json.JsonObject;
import java.io.IOException;

/**
 * @author Simone Scalabrino.
 */
public class EventProxy implements Event, Proxy<Event> {

    private final RepoProxy repo;
    private final Event origin;
    private Integer number;
    private JsonObject storedJson;

    public EventProxy(RepoProxy repo, Event origin) {
        this.repo = repo;
        this.origin = origin;
    }

    @Override
    public Repo repo() {
        return this.repo;
    }

    @Override
    public int number() {
        if (this.number == null)
            this.number = this.origin.number();

        return this.number;
    }

    @Override
    public JsonObject json() throws IOException {
        if (this.storedJson == null)
            this.storedJson = this.origin.json();

        return this.storedJson;
    }

    @Override
    public Event getOrigin() {
        return this.origin;
    }

    @Override
    public int compareTo(Event event) {
        return this.origin.compareTo(event);
    }
}
