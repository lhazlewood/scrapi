package scrapi.impl.key;

import scrapi.key.AsymmetricKey;
import scrapi.key.RsaKey;
import scrapi.util.Collections;

import java.security.Key;
import java.security.interfaces.RSAKey;
import java.util.Optional;
import java.util.Set;

abstract class AbstractRsaKey<K extends java.security.Key> extends AbstractKey<K> implements AsymmetricKey<K>, RsaKey<K> {

    static final String JCA_ALG_NAME = "RSA";
    static final int MIN_SIZE = 1024;

    // Defined in https://www.rfc-editor.org/rfc/rfc8017#appendix-A.1:
    private static final String RSA_ENC_OID = "1.2.840.113549.1.1.1"; // RFC 8017's "rsaEncryption"

    // Defined in https://www.rfc-editor.org/rfc/rfc8017#appendix-A.2.3:
    static final String PSS_JCA_ALG_NAME = "RSASSA-PSS";
    static final String PSS_OID = "1.2.840.113549.1.1.10"; // RFC 8017's "id-RSASSA-PSS"

    // Defined in https://www.rfc-editor.org/rfc/rfc8017#appendix-A.2.4:
    private static final String RS256_OID = "1.2.840.113549.1.1.11"; // RFC 8017's "sha256WithRSAEncryption"
    private static final String RS384_OID = "1.2.840.113549.1.1.12"; // RFC 8017's "sha384WithRSAEncryption"
    private static final String RS512_OID = "1.2.840.113549.1.1.13"; // RFC 8017's "sha512WithRSAEncryption"

    private static final Set<String> PSS_JCA_ALG_NAMES = Collections.setOf(PSS_JCA_ALG_NAME, PSS_OID);

    private static final Set<String> JCA_ALG_NAMES =
            Collections.setOf("RSA", PSS_JCA_ALG_NAME, PSS_OID, RS256_OID, RS384_OID, RS512_OID, RSA_ENC_OID);

    private static final String RSA_KEY_TYPE_MSG = "Key must be an instance of " + RSAKey.class.getName();

    protected static boolean isRsaAlgName(java.security.Key key) {
        String alg = findAlgorithm(key);
        return alg != null && JCA_ALG_NAMES.contains(alg);
    }

    protected static <T extends java.security.Key> T assertRsaAlgName(T key) {
        if (!isRsaAlgName(key)) {
            String msg = key.getClass() + " must have a valid RSA algorithm name";
            throw new IllegalArgumentException(msg);
        }
        return key;
    }

    protected static boolean isPssAlgName(Key key) {
        String alg = findAlgorithm(key);
        return PSS_JCA_ALG_NAMES.contains(alg);
    }

    public AbstractRsaKey(K key) {
        super(assertRsaAlgName(key));
    }

    @Override
    public Optional<Integer> bitLength() {
        return Optional.of(modulus().bitLength());
    }
}
