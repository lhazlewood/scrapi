package scrapi.key;

import java.math.BigInteger;

public interface RsaKey<J extends java.security.Key> extends AsymmetricKey<J> {

    /**
     * Returns the modulus {@code n}, a positive integer.
     *
     * @return the modulus {@code n}, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.1">RFC 8017, Section 3.1</a>.
     */
    BigInteger modulus();

    /**
     * Returns the public exponent {@code e}, a positive integer.
     *
     * @return the public exponent {@code e}, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.1">RFC 8017, Section 3.1</a>.
     */
    BigInteger publicExponent();

    interface Mutator<T extends Mutator<T>> {

        T modulus(BigInteger modulus);

        T publicExponent(BigInteger publicExponent);
    }
}
