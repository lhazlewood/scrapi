package scrapi.impl.key;

import scrapi.key.KeyBuilder;
import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;

import java.math.BigInteger;

abstract class AbstractRsaPrivateKeyBuilder<K extends RsaPrivateKey, T extends KeyBuilder<K, T> & RsaPrivateKey.Mutators<T>>
        extends AbstractRsaKeyBuilder<K, T> implements RsaPrivateKey.Mutators<T> {

    protected RsaPublicKey publicKey;
    protected BigInteger privateExponent;
    protected int bitLength;

    @Override
    public T publicKey(RsaPublicKey publicKey) {
        this.publicKey = publicKey;
        if (publicKey != null) {
            modulus(publicKey.modulus());
            publicExponent(publicKey.publicExponent());
            bitLength(publicKey.bitLength().orElseThrow(() ->
                    new IllegalArgumentException("RsaPublicKey bitLength cannot be null.")));
        }
        return self();
    }

    @Override
    public T privateExponent(BigInteger privateExponent) {
        this.privateExponent = privateExponent;
        return self();
    }

    public T bitLength(int bitLength) {
        this.bitLength = bitLength;
        return self();
    }
}
