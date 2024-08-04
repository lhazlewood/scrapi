package scrapi.key;

import scrapi.alg.Providable;

import java.util.function.Supplier;

/**
 * A KeyFactory produces Key results. There is no requirement that a new or distinct result be returned each time
 * the factory is invoked.
 *
 * @param <K> the type of key provided in the factory result
 * @param <R> the type of result which contains the key
 * @param <T> the KeyFactory subtype, useful for method chaining.
 */
public interface KeyFactory<K extends Key<?>, R extends Supplier<K>, T extends KeyFactory<K, R, T>>
        extends Providable<T>, Supplier<R> {
}
