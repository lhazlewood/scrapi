package scrapi.digest;

import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;

public interface RsaSignatureAlgorithm extends SignatureAlgorithm<RsaPublicKey, RsaPrivateKey, RsaPrivateKey.Builder> {

}
