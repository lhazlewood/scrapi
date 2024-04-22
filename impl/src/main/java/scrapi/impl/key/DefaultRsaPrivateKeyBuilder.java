package scrapi.impl.key;

import scrapi.impl.jca.JcaTemplate;
import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;
import scrapi.util.Assert;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class DefaultRsaPrivateKeyBuilder
        extends AbstractRsaPrivateKeyBuilder<RsaPrivateKey, RsaPrivateKey.Builder>
        implements RsaPrivateKey.Builder {

    @Override
    public RsaPrivateKey build() {
        KeyPair pair = new JcaTemplate("RSA").generateKeyPair(this.bitLength);
        RSAPublicKey jcaPub = Assert.isInstance(RSAPublicKey.class, pair.getPublic(),
                "RSA PublicKey must be a java.security.interfaces.RSAPublicKey instance.");
        RsaPublicKey pub = new DefaultRsaPublicKey(jcaPub);
        RSAPrivateKey jcaPriv = Assert.isInstance(RSAPrivateKey.class, pair.getPrivate(),
                "RSA PrivateKey must be a java.security.interfaces.RSAPrivateKey instance.");
        return new DefaultRsaPrivateKey(jcaPriv, pub);
    }
}
