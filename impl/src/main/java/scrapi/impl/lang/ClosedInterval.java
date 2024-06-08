package scrapi.impl.lang;

import scrapi.util.Assert;

import java.util.Objects;

class ClosedInterval<C extends Comparable<C>> implements Interval<C> {

    private final C min;
    private final C max;

    protected ClosedInterval(C min, C max) {
        this.min = Assert.notNull(min, "min cannot be null.");
        this.max = Assert.notNull(max, "max cannot be null.");
        Assert.gt(max, min, "Max value must be larger than Min value.");
    }

    @Override
    public boolean test(C c) {
        return c != null && min.compareTo(c) <= 0 && this.max.compareTo(c) >= 0;
    }

    @Override
    public String toString() {
        return ">= " + this.min + " and <= " + this.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClosedInterval)) return false;
        ClosedInterval<?> that = (ClosedInterval<?>) o;
        return this.min.equals(that.min) && this.max.equals(that.max);
    }
}
