package scrapi.lang;

/**
 * An <a href="https://en.wikipedia.org/wiki/Ordered_pair">ordered pair</a>, also known as a 2-tuple.
 *
 * @param <A> the first element
 * @param <B> the second element
 * @since SCRAPI_RELEASE_VERSION
 */
public interface Pair<A, B> {

    /**
     * Returns the first element in the pair.
     *
     * @return the first element in the pair.
     */
    A getA();

    /**
     * Returns the second element in the pair.
     *
     * @return the second element in the pair.
     */
    B getB();

    /**
     * Creates and returns a new {@code Pair} instance with the specified {@code a} and {@code b} values.
     *
     * @param a   the first element in the pair.
     * @param b   the second element in the pair.
     * @param <A> the type of the first element in the pair.
     * @param <B> the type of the second element in the pair.
     * @return a new {@code Pair} instance with the specified {@code a} and {@code b} values.
     * @throws NullPointerException if either {@code a} or {@code b} arguments are {@code null}.
     */
    static <A, B> Pair<A, B> of(A a, B b) throws NullPointerException {
        return new DefaultPair<>(a, b);
    }
}
