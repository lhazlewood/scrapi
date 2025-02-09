package scrapi.msg;

import scrapi.lang.Registry;
import scrapi.util.Classes;

final class HmacAlgs {

    private HmacAlgs() {
    } // prevent instantiation

    private static final String IMPL_CLASSNAME = "scrapi.impl.msg.DefaultHmacAlgorithmRegistry";
    static final Registry<String, HmacAlgorithm> REGISTRY = Classes.newInstance(IMPL_CLASSNAME);
}
