package scrapi.impl.util;

import scrapi.util.Assert;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class BitLengthValidator implements UnaryOperator<Integer> {

    private final String name;
    private final Predicate<Integer> valid;

    public BitLengthValidator(String name, Predicate<Integer> i) {
        this.name = Assert.hasText(name, "name must not be null or empty.");
        this.valid = Assert.notNull(i, "interval must not be null.");
    }

    @Override
    public Integer apply(Integer size) throws IllegalArgumentException {
        if (size == null) {
            String msg = name + " must not be null.";
            throw new IllegalArgumentException(msg);
        }
        if (size % Byte.SIZE != 0) {
            String msg = name + " value '" + size + "' must be a multiple of " + Byte.SIZE + ".";
            throw new IllegalArgumentException(msg);
        }
        if (!valid.test(size)) {
            String msg = "Invalid " + name + " value '" + size + "': must be " + valid + ".";
            throw new IllegalArgumentException(msg);
        }
        return size;
    }
}
