package scrapi.impl.msg;

import scrapi.impl.lang.IdentifiableRegistry;
import scrapi.msg.HashAlgorithm;
import scrapi.msg.HmacAlgorithm;
import scrapi.util.Collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultHmacAlgorithmRegistry extends IdentifiableRegistry<String, HmacAlgorithm> {

    // ------------------------------------------------------------------------------------------------
    // https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#mac-algorithms
    // ------------------------------------------------------------------------------------------------
    private static List<HmacAlgorithm> createAlgs() {
        Collection<HashAlgorithm> hashAlgs = HashAlgorithm.registry().values();
        List<HmacAlgorithm> hmacs = new ArrayList<>(hashAlgs.size() - 1); // no MD2, see below
        for (HashAlgorithm hashAlg : hashAlgs) {

            if (HashAlgorithm.MD2.equals(hashAlg)) continue; // no JCA standard hmac alg for this one

            HmacAlgorithm hmac = new DefaultHmacAlgorithm(hashAlg);
            hmacs.add(hmac);
        }
        return Collections.immutable(hmacs);
    }

    public DefaultHmacAlgorithmRegistry() {
        super("Hmac Algorithm", createAlgs());
    }
}