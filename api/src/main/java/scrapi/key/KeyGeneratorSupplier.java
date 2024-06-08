package scrapi.key;

/**
 * A {@code KeyGeneratorSupplier} produces {@link KeyGenerator}s that may be used to generate new
 * {@link Key}s suitable for use with an associated cryptographic algorithm.
 *
 * @param <K> type of {@link Key} generated
 * @param <G> type of {@link KeyGenerator} created each time the {@link #keygen()} method is invoked.
 * @see #keygen()
 * @see KeyGenerator
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface KeyGeneratorSupplier<K extends Key<?>, G extends KeyGenerator<K, G>> {

    /**
     * Returns a new {@link KeyBuilder} instance that will produce keys with a length sufficient
     * to be used by the component's associated cryptographic algorithm.
     *
     * @return a new {@link KeyBuilder} instance that will produce keys with a length sufficient
     * to be used by the component's associated cryptographic algorithm.
     */
    G keygen();
}
