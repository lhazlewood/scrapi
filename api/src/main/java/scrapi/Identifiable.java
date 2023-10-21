package scrapi;

/**
 * An object that may be uniquely identified by an {@link #getId() id} relative to other instances of the same type.
 *
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface Identifiable {

    /**
     * Returns the unique string identifier of the associated object.
     *
     * @return the unique string identifier of the associated object.
     */
    String getId();
}
