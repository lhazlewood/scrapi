package scrapi.key;

import scrapi.alg.Randomizable;

import java.util.function.Supplier;

public interface KeyGen<K extends Key<?>, R extends Supplier<K>, T extends KeyGen<K, R, T>>
        extends Randomizable<T>, KeyFactory<K, R, T> {

    default K generate() {
        return get().get();
    }
}
