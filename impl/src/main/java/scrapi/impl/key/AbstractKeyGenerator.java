package scrapi.impl.key;

import scrapi.key.Key;
import scrapi.key.KeyGenerator;
import scrapi.util.Assert;

abstract class AbstractKeyGenerator<K extends Key<?>, T extends KeyGenerator<K, T>> extends AbstractKeyFactory<K, T>
        implements KeyGenerator<K, T> {

    protected final int DEFAULT_SIZE;
    protected int size = 0; // zero means not configured

    AbstractKeyGenerator(String jcaName, int minSize, int defaultSize) {
        this(jcaName, "size", minSize, defaultSize);
    }

    AbstractKeyGenerator(String jcaName, String sizeName, int minSize, int defaultSize) {
        super(jcaName, sizeName, minSize);
        this.DEFAULT_SIZE = Assert.gte(defaultSize, this.MIN_SIZE, "defaultSize must be >= minSize");
    }

    public final T size(int sizeInBits) {
        this.size = this.SIZE_VALIDATOR.apply(sizeInBits);
        return self();
    }

    protected int resolveSize() {
        return this.size < MIN_SIZE ? DEFAULT_SIZE : this.size;
    }
}
