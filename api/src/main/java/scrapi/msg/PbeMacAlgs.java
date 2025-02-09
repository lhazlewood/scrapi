package scrapi.msg;

import scrapi.lang.Registry;
import scrapi.util.Classes;

final class PbeMacAlgs {

    private PbeMacAlgs() {
    } // prevent instantiation

    private static final String IMPL_CLASSNAME = "scrapi.impl.msg.DefaultPbeMacAlgorithmRegistry";
    static final Registry<String, PbeMacAlgorithm> REGISTRY = Classes.newInstance(IMPL_CLASSNAME);
}
