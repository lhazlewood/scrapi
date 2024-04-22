package scrapi.impl.key;

import scrapi.key.Key;
import scrapi.key.KeyBuilder;

import java.security.Provider;
import java.security.SecureRandom;

abstract class AbstractKeyBuilder<K extends Key<?>, T extends KeyBuilder<K, T>> implements KeyBuilder<K, T> {

    protected Provider provider;
    protected SecureRandom random;

    @SuppressWarnings("unchecked")
    protected final T self() {
        return (T) this;
    }

    @Override
    public T provider(Provider provider) {
        this.provider = provider;
        return self();
    }

    @Override
    public T random(SecureRandom random) {
        this.random = random;
        return self();
    }
}
