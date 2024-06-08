package scrapi.impl.lang;

import java.util.function.Predicate;

public class Predicates {

    public static <C extends Comparable<C>> Predicate<C> lt(C val) {
        return c -> c != null && c.compareTo(val) < 0;
    }

    public static <C extends Comparable<C>> Predicate<C> le(C val) {
        return c -> c != null && c.compareTo(val) <= 0;
    }

    public static <C extends Comparable<C>> Predicate<C> eq(C val) {
        return c -> c != null && c.compareTo(val) == 0;
    }

    public static <C extends Comparable<C>> Predicate<C> ge(C val) {
        return c -> c != null && c.compareTo(val) >= 0;
    }

    public static <C extends Comparable<C>> Predicate<C> gt(C val) {
        return c -> c != null && c.compareTo(val) > 0;
    }

    public static <C extends Comparable<C>> Predicate<C> neq(C val) {
        return c -> c != null && c.compareTo(val) != 0;
    }
}
