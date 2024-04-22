package scrapi.impl.key;

import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;
import scrapi.util.Assert;

import java.math.BigInteger;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Optional;

public class DefaultRsaPrivateKey extends AbstractPrivateKey<java.security.PrivateKey, RsaPublicKey>
        implements RsaPrivateKey {

    public DefaultRsaPrivateKey(java.security.PrivateKey key, RsaPublicKey publicKey) {
        super(key, publicKey);
        Assert.isInstance(RSAKey.class, key, "PrivateKey must be an instanceof java.security.interfaces.RSAKey");
    }

    @Override
    public Optional<BigInteger> privateExponent() {
        BigInteger privExp = null;
        if (this.key instanceof RSAPrivateKey) {
            privExp = ((RSAPrivateKey) this.key).getPrivateExponent();
        }
        return Optional.ofNullable(privExp);
    }

    @Override
    public BigInteger modulus() {
        return Assert.isInstance(RSAKey.class, this.key, "RSAKey type required.").getModulus();
    }

    @Override
    public BigInteger publicExponent() {
        return publicKey().publicExponent();
    }
}
