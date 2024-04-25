package scrapi.impl.key;

import scrapi.impl.jca.JcaTemplate;
import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;
import scrapi.util.Assert;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;

@SuppressWarnings("unused") // used reflectively by RsaPrivateKey.builder()
public class DefaultRsaPrivateKeyBuilder
        extends AbstractRsaPrivateKeyBuilder<RsaPrivateKey, RsaPrivateKey.Builder>
        implements RsaPrivateKey.Builder {

    private static final String RSA_PUB_TYPE_MSG = "RSA PublicKey must be a " + RSAKey.class.getName() + " instance.";

    @Override
    public RsaPrivateKey build() {
        KeyPair pair = new JcaTemplate("RSA").generateKeyPair(this.bitLength);
        RSAPublicKey jcaPub = Assert.isInstance(RSAPublicKey.class, pair.getPublic(), RSA_PUB_TYPE_MSG);
        RsaPublicKey pub = new DefaultRsaPublicKey(jcaPub);
        PrivateKey jcaPriv = Assert.notNull(pair.getPrivate(), "RSA KeyPair private key cannot be null.");
        return new DefaultRsaPrivateKey(jcaPriv, pub);
    }
}
