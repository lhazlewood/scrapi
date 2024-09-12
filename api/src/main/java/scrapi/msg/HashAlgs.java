package scrapi.msg;

import scrapi.lang.Registry;
import scrapi.util.Classes;

final class HashAlgs {

    //prevent instantiation
    private HashAlgs() {
    }

    private static final String IMPL_CLASSNAME = "scrapi.impl.msg.StandardHashAlgorithms";
    static final Registry<String, HashAlgorithm> REGISTRY = Classes.newInstance(IMPL_CLASSNAME);
}
