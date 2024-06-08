package scrapi.impl.util;

import scrapi.impl.lang.Predicates;

import java.math.BigInteger;

public class Parameters {

    public static Parameter<BigInteger> positiveBigInt(String id, String name, boolean secret) {
        return new DefaultParameter<>(id, name, secret, Predicates.gt(BigInteger.ZERO), " must be >= 0");
    }
}
