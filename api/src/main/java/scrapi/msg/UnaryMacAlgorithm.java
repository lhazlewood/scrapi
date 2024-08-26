package scrapi.msg;

import scrapi.key.KeyGenerator;
import scrapi.key.Keyable;
import scrapi.key.SymmetricKey;
import scrapi.util.Assert;

/**
 * A Unary MAC algorithm requires only a single {@code key} operand to produce a {@link Hasher} used
 * for MAC computation.
 */
public interface UnaryMacAlgorithm<
        K extends SymmetricKey,
        P extends Keyable<K, P>,
        G extends KeyGenerator<K, G>
        > extends MacAlgorithm<K, P, G> {

    default Hasher with(K key) {
        Assert.notNull(key, "Key cannot be null.");
        return with(c -> c.key(key));
    }
}
