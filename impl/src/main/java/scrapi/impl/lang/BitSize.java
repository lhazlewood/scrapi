package scrapi.impl.lang;

import scrapi.util.Assert;
import scrapi.util.Bytes;

public class BitSize implements Comparable<Integer> {

    private final int value;

    public BitSize(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(Integer value) {
        return Integer.compare(this.value, Assert.notNull(value, "value cannot be null."));
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof BitSize) {
            BitSize other = (BitSize) obj;
            return value == other.value;
        }
        return false;
    }

    @Override
    public String toString() {
        return Bytes.bitsMsg(this.value);
    }
}
