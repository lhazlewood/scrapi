package scrapi.key;

import scrapi.jca.Providable;

import java.util.function.Supplier;

/**
 * A {@code KeyBuilder} produces {@link Key}s suitable for use with an associated cryptographic algorithm. As with
 * the {@link Supplier} superinterface, there is no requirement that a new or distinct result be returned each time
 * the {@link #get()} method is invoked.
 *
 * @param <K> the type of key to produce
 * @param <T> the factory subtype for method chaining
 * @since SCRAPI_RELEASE_VERSION
 */
public interface KeyBuilder<K extends Key<?>, T extends KeyBuilder<K, T>> extends Providable<T>, Supplier<K> {
}
