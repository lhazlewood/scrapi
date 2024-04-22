package scrapi.impl.key;

import scrapi.key.RsaPublicKey;

import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;

public class DefaultRsaPublicKey extends AbstractKey<RSAPublicKey> implements RsaPublicKey {

    public DefaultRsaPublicKey(RSAPublicKey key) {
        super(key);
    }

    @Override
    public BigInteger modulus() {
        return this.key.getModulus();
    }

    @Override
    public BigInteger publicExponent() {
        return this.key.getPublicExponent();
    }
}
