package scrapi.impl.lang;

import scrapi.util.Assert;

import java.util.function.Predicate;

public class PredicateValidator<T> implements Validator<T> {

    private final Predicate<T> requirement;
    private final String exMsg;

    public PredicateValidator(Predicate<T> requirement, String exMsg) {
        this.exMsg = Assert.hasText(exMsg, "exMsg cannot be null or empty.");
        this.requirement = Assert.notNull(requirement, "requirement cannot be null.");
    }

    @Override
    public T validate(T value) throws IllegalArgumentException {
        if (!requirement.test(value)) {
            throw new IllegalArgumentException(exMsg);
        }
        return value;
    }
}
