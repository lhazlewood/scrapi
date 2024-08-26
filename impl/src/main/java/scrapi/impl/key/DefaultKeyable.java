package scrapi.impl.key;

import scrapi.key.Key;

public final class DefaultKeyable<K extends Key<?>> extends KeyableSupport<K, DefaultKeyable<K>> {

    public DefaultKeyable(String jcaName) {
        super(jcaName);
    }
}
