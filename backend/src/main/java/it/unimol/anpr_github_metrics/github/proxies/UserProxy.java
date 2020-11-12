package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.*;

import javax.json.JsonObject;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Simone Scalabrino.
 */
public class UserProxy implements User, Proxy<User> {
    private final GithubProxy github;
    private final User origin;
    private String storedLogin;
    private JsonObject storedJson;

    public UserProxy(GithubProxy github, User origin) {
        this.github = github;
        this.origin = origin;
    }

    @Override
    public Github github() {
        return this.github;
    }

    @Override
    public String login() throws IOException {
        if (this.storedLogin == null)
            this.storedLogin = this.origin.login();

        return this.storedLogin;
    }

    @Override
    public UserOrganizations organizations() {
        return this.origin.organizations();
    }

    @Override
    public PublicKeys keys() {
        return this.origin.keys();
    }

    @Override
    public UserEmails emails() {
        return this.origin.emails();
    }

    @Override
    public List<Notification> notifications() throws IOException {
        return this.origin.notifications();
    }

    @Override
    public void markAsRead(Date date) {
        this.origin.markAsRead(date);
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
    public User getOrigin() {
        return this.origin;
    }
}
