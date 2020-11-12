package it.unimol.anpr_github_metrics.github.proxies;

/**
 * @author Simone Scalabrino.
 */
public interface Proxy<Type> {
    Type getOrigin();

    default void invalidate() {
        throw new RuntimeException("Unable to invalidate this proxy");
    }
}
