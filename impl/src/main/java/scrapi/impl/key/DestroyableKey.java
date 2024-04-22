package scrapi.impl.key;

import scrapi.Destroyable;

abstract class DestroyableKey<J extends java.security.Key> extends AbstractKey<J> implements Destroyable {

    private volatile boolean destroyed;

    public DestroyableKey(J key) {
        super(key);
    }

    public DestroyableKey(J key, int bitLength) {
        super(key, bitLength);
    }

    @Override
    public void destroy() {
        try {
            doDestroy();
        } finally {
            this.destroyed = true;
        }
    }

    void doDestroy() {
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
