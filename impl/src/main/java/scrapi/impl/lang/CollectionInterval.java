package scrapi.impl.lang;

import scrapi.util.Assert;
import scrapi.util.Collections;
import scrapi.util.Strings;

import java.util.Collection;

public class CollectionInterval<C extends Comparable<C>> implements Interval<C> {

    private final Collection<? extends C> values;
    private final String TO_STRING_VAL;

    public CollectionInterval(Collection<? extends C> values) {
        this.values = Assert.notEmpty(values, "values cannot be null or empty.");
        this.TO_STRING_VAL = "equal to one of " + Collections.toDelimitedString(values, Strings.COMMA, "{", "}");
    }

    @Override
    public int hashCode() {
        return this.values.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof CollectionInterval)) return false;
        return this.values.equals(((CollectionInterval<?>) o).values);
    }

    @Override
    public String toString() {
        return TO_STRING_VAL;
    }

    @Override
    public boolean test(C c) {
        if (c == null) return false;
        for (C val : this.values) {
            if (val.compareTo(c) == 0) return true;
        }
        return false;
    }
}
