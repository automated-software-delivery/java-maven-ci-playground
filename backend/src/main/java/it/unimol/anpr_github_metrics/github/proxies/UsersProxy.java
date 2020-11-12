package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.Github;
import com.jcabi.github.User;
import com.jcabi.github.Users;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Simone Scalabrino.
 */
public class UsersProxy implements Users, Proxy<Users> {

    private final GithubProxy github;
    private final Users origin;
    private final Map<String, UserProxy> usersCache;
    private User selfUser;

    public UsersProxy(GithubProxy github, Users origin) {
        this.github = github;
        this.origin = origin;
        this.usersCache = new HashMap<>();
    }

    @Override
    public Github github() {
        return this.github;
    }

    @Override
    public User self() {
        if (selfUser == null)
            selfUser = this.origin.self();

        return selfUser;
    }

    @Override
    public User get(String s) {
        if (!this.usersCache.containsKey(s))
            this.usersCache.put(s, new UserProxy(this.github, this.origin.get(s)));

        return this.usersCache.get(s);
    }

    @Override
    public Iterable<User> iterate(String s) {
        return this.origin.iterate(s);
    }

    @Override
    public Users getOrigin() {
        return this.origin;
    }
}
