package scrapi;

/**
 * Supplies the length in bits (not bytes) of the implementing object.
 *
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface BitLength {

    /**
     * Returns the length in bits <em>(not bytes)</em> of the associated object.
     *
     * @return the length in bits <em>(not bytes)</em> of the associated object.
     */
    long getBitLength();
}
