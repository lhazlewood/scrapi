package scrapi.lang;

/**
 * Type-safe interface that reflects the <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder pattern</a>.
 *
 * @param <T> The type of object that will be created when {@link #build()} is invoked.
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface Builder<T> {

    /**
     * Creates and returns a new instance of type {@code T}.
     *
     * @return a new instance of type {@code T}.
     */
    T build();
}
