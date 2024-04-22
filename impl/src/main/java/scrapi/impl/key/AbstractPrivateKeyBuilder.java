package scrapi.impl.key;

import scrapi.key.PrivateKey;
import scrapi.key.PublicKey;

abstract class AbstractPrivateKeyBuilder<U extends PublicKey<?>, R extends PrivateKey<?, U>, T extends PrivateKey.Builder<U, R, T>>
        extends AbstractKeyBuilder<R, T> implements PrivateKey.Builder<U, R, T> {

    protected U publicKey;

    @Override
    public T publicKey(U publicKey) {
        this.publicKey = publicKey;
        return self();
    }
}
