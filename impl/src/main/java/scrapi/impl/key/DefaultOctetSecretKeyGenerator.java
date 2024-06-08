package scrapi.impl.key;

import scrapi.key.OctetSecretKey;

import javax.crypto.SecretKey;

public class DefaultOctetSecretKeyGenerator extends AbstractKeyGenerator<OctetSecretKey, OctetSecretKey.Generator>
        implements OctetSecretKey.Generator {

    public DefaultOctetSecretKeyGenerator(String jcaName, int minSize) {
        super(jcaName, minSize, minSize);
    }

    @Override
    public OctetSecretKey get() {
        int size = resolveSize();
        SecretKey jcaKey = jca().generateSecretKey(size);
        return new DefaultOctetSecretKey(jcaKey);
    }
}
