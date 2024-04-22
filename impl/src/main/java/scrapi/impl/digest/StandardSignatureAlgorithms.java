package scrapi.impl.digest;

import scrapi.digest.SignatureAlgorithm;
import scrapi.impl.lang.IdentifiableRegistry;
import scrapi.util.Collections;

public final class StandardSignatureAlgorithms extends IdentifiableRegistry<String, SignatureAlgorithm<?, ?, ?>> {
    public StandardSignatureAlgorithms() {
        super("Signature Algorithm", Collections.<SignatureAlgorithm<?, ?, ?>>of(
                new DefaultRsaSignatureAlgorithm("SHA1withRSA", 160),
                new DefaultRsaSignatureAlgorithm("SHA224withRSA", 224),
                new DefaultRsaSignatureAlgorithm("SHA256withRSA", 256),
                new DefaultRsaSignatureAlgorithm("SHA384withRSA", 384),
                new DefaultRsaSignatureAlgorithm("SHA512withRSA", 512),
                new DefaultRsaSignatureAlgorithm("SHA512/224withRSA", 224),
                new DefaultRsaSignatureAlgorithm("SHA512/256withRSA", 256),
                new DefaultRsaSignatureAlgorithm("SHA3-224withRSA", 224),
                new DefaultRsaSignatureAlgorithm("SHA3-256withRSA", 256),
                new DefaultRsaSignatureAlgorithm("SHA3-384withRSA", 384),
                new DefaultRsaSignatureAlgorithm("SHA3-512withRSA", 512)
        ));
    }
}
