package scrapi.key;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Consumer;

/**
 * An RSA Private Key using Chinese Remainder Theorem (CRT) values as defined by
 * <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>'s second
 * representation.
 *
 * @since SCRAPI_RELEASE_VERSION
 */
public interface MultiPrimeRsaPrivateKey extends RsaPrivateKey {

    /**
     * Returns the first factor {@code p}, a positive integer.
     *
     * @return the first factor {@code p}, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    BigInteger prime1();

    /**
     * Returns the second factor {@code q}, a positive integer.
     *
     * @return the second factor {@code q}, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    BigInteger prime2();

    /**
     * Returns the first factor's CRT exponent, a positive integer.
     *
     * @return the first factor's CRT exponent, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    BigInteger exponent1();

    /**
     * Returns the second factor's CRT exponent, a positive integer.
     *
     * @return the second factor's CRT exponent, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    BigInteger exponent2();

    /**
     * Returns the (first) CRT coefficient, a positive integer.
     *
     * @return the (first) CRT coefficient, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    BigInteger coefficient();

    /**
     * Returns any additional (possibly empty) prime factors of the {@link #modulus() modulus} {@code n}.
     *
     * @return any additional (possibly empty) prime factors of the {@link #modulus() modulus} {@code n}.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    List<RsaPrimeFactor> otherFactors();

    interface Mutators<T extends Mutators<T>> extends RsaPrivateKey.Mutators<T> {

        Builder prime1(BigInteger prime1);

        Builder prime2(BigInteger prime2);

        Builder exponent1(BigInteger exponent1);

        Builder exponent2(BigInteger exponent2);

        Builder coefficient(BigInteger coefficient);

        Builder add(RsaPrimeFactor factor);

        Builder add(Consumer<RsaPrimeFactor.Mutators<?>> c);
    }

    interface Builder extends Mutators<Builder>, PrivateKey.Builder<RsaPublicKey, MultiPrimeRsaPrivateKey, Builder> {
    }
}
