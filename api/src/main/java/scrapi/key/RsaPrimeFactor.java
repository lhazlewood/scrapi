package scrapi.key;

import java.math.BigInteger;

public interface RsaPrimeFactor {

    /**
     * Returns the factor's prime, a positive integer.
     *
     * @return the factor's prime, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    BigInteger prime();

    /**
     * Returns the factor's CRT exponent, a positive integer.
     *
     * @return the factor's CRT exponent, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    BigInteger exponent();

    /**
     * Returns the factor's CRT coefficient, a positive integer.
     *
     * @return the factor's CRT coefficient, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    BigInteger coefficient();

    interface Mutator<T extends Mutator<T>> {

        T prime(BigInteger prime);

        T exponent(BigInteger exponent);

        T coefficient(BigInteger coefficient);
    }

    interface Builder extends Mutator<Builder>, scrapi.lang.Builder<RsaPrimeFactor> {
    }
}
