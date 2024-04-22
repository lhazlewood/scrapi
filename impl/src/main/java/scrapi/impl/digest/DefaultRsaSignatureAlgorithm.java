package scrapi.impl.digest;

import scrapi.digest.RsaSignatureAlgorithm;
import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;

public class DefaultRsaSignatureAlgorithm
        extends DefaultSignatureAlgorithm<RsaPublicKey, RsaPrivateKey, RsaPrivateKey.Builder>
        implements RsaSignatureAlgorithm {

    public DefaultRsaSignatureAlgorithm(String id, int bitLength) {
        super(id, null, null, bitLength, () -> RsaPrivateKey.builder().bitLength(4096));
    }
}