package scrapi.impl.util;

import scrapi.impl.lang.PredicateValidator;
import scrapi.impl.lang.Validator;
import scrapi.util.Assert;

import java.util.Objects;
import java.util.function.Predicate;

public class DefaultParameter<T> implements Parameter<T> {

    private final String ID;
    private final String NAME;
    private final boolean SECRET;
    private final Validator<T> validator;

    public DefaultParameter(String id, String name, boolean secret, Predicate<T> validator, String validationExMsgSuffix) {
        this.ID = Assert.hasText(id, "id cannot be null or empty");
        this.NAME = Assert.hasText(name, "name cannot be null or empty");
        this.SECRET = secret;
        String exMsg = this + validationExMsgSuffix;
        this.validator = new PredicateValidator<>(validator, exMsg);
    }

    @Override
    public String name() {
        return this.NAME;
    }

    @Override
    public boolean isSecret() {
        return this.SECRET;
    }

    @Override
    public String id() {
        return this.ID;
    }

    @Override
    public T validate(T value) throws IllegalArgumentException {
        if (value == null) {
            String msg = this + " must not be null.";
            throw new IllegalArgumentException(msg);
        }
        return this.validator.validate(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, NAME, SECRET);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof DefaultParameter)) return false;
        DefaultParameter<?> other = (DefaultParameter<?>) obj;
        return this.ID.equals(other.ID) && this.NAME.equals(other.NAME) && this.SECRET == other.SECRET;
    }

    @Override
    public String toString() {
        return "'" + this.ID + "' (" + this.NAME + ")";
    }
}
