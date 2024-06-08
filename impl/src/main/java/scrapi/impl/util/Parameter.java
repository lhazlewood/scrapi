package scrapi.impl.util;

import scrapi.impl.lang.Validator;
import scrapi.lang.Identified;

public interface Parameter<T> extends Identified<String>, Validator<T> {

    String name();

    boolean isSecret();
}
