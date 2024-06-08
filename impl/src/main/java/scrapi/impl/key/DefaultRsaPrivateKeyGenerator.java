package scrapi.impl.key;

import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;
import scrapi.util.Assert;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;

public class DefaultRsaPrivateKeyGenerator extends
        AbstractKeyGenerator<RsaPrivateKey, RsaPrivateKey.Generator>
        implements RsaPrivateKey.Generator {

    /**
     * RSA 2048-bit keys only have 112 bits of security strength per
     * <a href="https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-57pt1r5.pdf">
     * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-57pt1r5.pdf</a>, Table 2 (page 54), and
     * NIST/FIPS will not allow smaller lengths, so we won't either for safety.  Applications that require smaller
     * lengths will need to use the JCA APIs directly.
     */
    private static final int MIN_KEY_SIZE = 2048;

    /**
     * Per NIST doc above, RSA 3072-bit keys have 128 bits of security strength, so that's our default.
     */
    private static final int DEFAULT_KEY_SIZE = 3072;

    public DefaultRsaPrivateKeyGenerator() {
        super(AbstractRsaKey.JCA_ALG_NAME, "RSA modulus size", MIN_KEY_SIZE, DEFAULT_KEY_SIZE);
    }

    @Override
    public RsaPrivateKey get() {
        int size = resolveSize();
        KeyPair pair = jca().generateKeyPair(size);
        RSAPublicKey jcaPub = Assert.isInstance(RSAPublicKey.class, pair.getPublic(), DefaultRsaPublicKey.JCA_PUB_TYPE_MSG);
        RsaPublicKey pub = new DefaultRsaPublicKey(jcaPub);
        PrivateKey jcaPriv = Assert.notNull(pair.getPrivate(), "RSA KeyPair private key cannot be null.");
        return DefaultRsaPrivateKey.of(jcaPriv, pub);
    }
}
