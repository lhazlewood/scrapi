package scrapi.impl.lang;

import java.util.Collection;
import java.util.function.Predicate;

public interface Interval<C extends Comparable<C>> extends Predicate<C> {

    static <C extends Comparable<C>> Interval<C> of(C value) {
        return new EndpointInterval<>(value);
    }

    static <C extends Comparable<C>> Interval<C> of(C min, C max) {
        return new ClosedInterval<>(min, max);
    }

    static <C extends Comparable<C>> Interval<C> of(Collection<C> values) {
        return new CollectionInterval<>(values);
    }

    static <C extends Comparable<C>> Interval<C> gte(C min) {
        return new LeftClosedInterval<>(min);
    }

}
