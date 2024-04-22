package scrapi.impl.key;

import scrapi.key.KeyBuilder;
import scrapi.key.RsaKey;

import java.math.BigInteger;

abstract class AbstractRsaKeyBuilder<K extends RsaKey<?>, T extends KeyBuilder<K, T> & RsaKey.Mutators<T>>
        extends AbstractKeyBuilder<K, T> implements RsaKey.Mutators<T> {

    protected BigInteger modulus;
    protected BigInteger publicExponent;

    @Override
    public T modulus(BigInteger modulus) {
        this.modulus = modulus;
        return self();
    }

    @Override
    public T publicExponent(BigInteger publicExponent) {
        this.publicExponent = publicExponent;
        return self();
    }
}
