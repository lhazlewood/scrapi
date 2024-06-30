package scrapi.key;

import javax.crypto.SecretKey;

public interface Password extends ConfidentialKey<SecretKey> {

    char[] chars();

}
