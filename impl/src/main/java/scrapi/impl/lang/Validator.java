package scrapi.impl.lang;

import scrapi.util.Assert;

@FunctionalInterface
public interface Validator<T> {

    T validate(T value) throws IllegalArgumentException;

    default Validator<T> and(Validator<T> other) {
        Assert.notNull(other, "other validator cannot be null.");
        return (t) -> other.validate(validate(t));
    }

}
