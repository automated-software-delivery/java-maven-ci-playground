package it.unimol.anpr_github_metrics.github.proxies;

import com.jcabi.github.Issue;
import it.unimol.anpr_github_metrics.utils.DateUtils;

import java.util.*;

/**
 * @author Simone Scalabrino.
 */
public class IterableProxy<CachedType, Identifier> {
    private final Map<Map<String, String>, Set<CachedType>> iterableCache;
    private final Map<Map<String, String>, Date> iterableCacheLastUpdate;

    public IterableProxy() {
        this.iterableCache = new HashMap<>();
        this.iterableCacheLastUpdate = new HashMap<>();
    }

    public Iterable<CachedType> getProxied(
            Map<String, String> parametersMap,
            OriginalIterator<CachedType> origin,
            ProxyBuilder<CachedType> cacheAction) {
        // If the "since" parameter is used, just fall back to the normal implementation, because
        // we can't handle it at the moment.
        if (parametersMap.containsKey("since"))
            return origin.iterate(parametersMap);

        if (!this.iterableCache.containsKey(parametersMap)) {
            Set<CachedType> matching = new HashSet<>();

            for (CachedType cached : origin.iterate(parametersMap)) {
                CachedType proxy = cacheAction.getProxy(cached);
                matching.add(proxy);
            }

            this.iterableCache.put(parametersMap, matching);
            this.iterableCacheLastUpdate.put(parametersMap, new Date());
        } else {
            Set<CachedType> matching = this.iterableCache.get(parametersMap);

            Map<String, String> newRequest = new HashMap<>(parametersMap);
            String sinceString = DateUtils.GITHUB_DATE.format(this.iterableCacheLastUpdate.get(parametersMap));
            newRequest.put("since", sinceString);

            for (CachedType cached : origin.iterate(newRequest)) {
                CachedType proxy = cacheAction.getProxy(cached);
                matching.add(proxy);
            }

            this.iterableCacheLastUpdate.put(parametersMap, new Date());
        }

        return this.iterableCache.get(parametersMap);
    }

    public interface OriginalIterator<T> {
        Iterable<T> iterate(Map<String, String> map);
    }

    public interface ProxyBuilder<T> {
        T getProxy(T original);
    }
}
