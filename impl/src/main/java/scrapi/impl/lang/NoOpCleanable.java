package scrapi.impl.lang;

import java.lang.ref.Cleaner;

public final class NoOpCleanable implements Cleaner.Cleanable {

    public static final NoOpCleanable INSTANCE = new NoOpCleanable();

    private NoOpCleanable() {
    }

    @Override
    public void clean() {
    }
}
