package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.Issue;
import com.jcabi.github.Issues;
import com.jcabi.github.Repo;
import com.jcabi.github.Search;
import it.unimol.anpr_github_metrics.utils.DateUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author Simone Scalabrino.
 */
public class IssuesProxy implements Issues, Proxy<Issues> {
    private final RepoProxy repo;
    private final Issues origin;

    private final Map<Integer, Issue> issueCache;
    private final IterableProxy<Issue, Integer> issuesProxies;

    public IssuesProxy(RepoProxy repo, Issues origin) {
        this.repo = repo;
        this.origin = origin;
        this.issueCache = new HashMap<>();

        this.issuesProxies = new IterableProxy<>();
    }

    @Override
    public Repo repo() {
        return this.repo;
    }

    @Override
    public Issue get(int i) {
        if (!this.issueCache.containsKey(i))
            this.issueCache.put(i, new IssueProxy(this.repo, this.origin.get(i)));

        return this.issueCache.get(i);
    }

    @Override
    public Issue create(String s, String s1) throws IOException {
        return this.origin.create(s, s1);
    }

    @Override
    public Iterable<Issue> iterate(Map<String, String> map) {
        return this.issuesProxies.getProxied(map,
                origin::iterate,
                issue -> {
                    Issue issueProxy = new IssueProxy(this.repo, issue);
                    this.issueCache.put(issueProxy.number(), issueProxy);
                    return issueProxy;
                });
    }

    @Override
    public Iterable<Issue> search(Sort sort, Search.Order order, EnumMap<Qualifier, String> enumMap) throws IOException {
        return this.origin.search(sort, order, enumMap);
    }

    @Override
    public Issues getOrigin() {
        return this.origin;
    }
}
