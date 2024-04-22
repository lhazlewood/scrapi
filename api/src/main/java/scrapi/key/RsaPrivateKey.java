package scrapi.key;

import scrapi.util.Classes;

import java.math.BigInteger;
import java.util.Optional;

public interface RsaPrivateKey extends PrivateKey<java.security.PrivateKey, RsaPublicKey>, RsaKey<java.security.PrivateKey> {

    /**
     * Returns the private exponent {@code d}, a positive integer, if it is available. The value may not be
     * {@link Optional#isPresent() present} if the implementation does not expose key material, as might be the case
     * with some JCA {@link java.security.Provider Provider}s and/or Hardware Security Modules.
     *
     * @return the private exponent {@code d}, a positive integer, if it is available.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>.
     */
    Optional<BigInteger> privateExponent();

    interface Mutators<T extends Mutators<T>> extends RsaKey.Mutators<T>, PrivateKey.Mutators<RsaPublicKey, T> {
        T privateExponent(BigInteger privateExponent);
    }

    interface Builder extends Mutators<Builder>, PrivateKey.Builder<RsaPublicKey, RsaPrivateKey, Builder> {
        Builder bitLength(int bitLength);
    }

    static Builder builder() {
        return Classes.newInstance("scrapi.impl.key.DefaultRsaPrivateKeyBuilder");
    }
}
