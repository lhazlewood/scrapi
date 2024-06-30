package scrapi.impl.key;

import scrapi.impl.alg.AlgorithmSupport;
import scrapi.key.Key;
import scrapi.key.Keyable;

public abstract class KeyableSupport<K extends Key<?>, T>
        extends AlgorithmSupport<T> implements Keyable<K, T> {

    protected K key;

    public KeyableSupport(String jcaName) {
        super(jcaName);
    }

    @Override
    public T key(K key) {
        this.key = key;
        return self();
    }

    public K key() {
        return this.key;
    }
}
