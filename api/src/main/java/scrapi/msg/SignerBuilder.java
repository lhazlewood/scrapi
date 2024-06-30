package scrapi.msg;

import scrapi.alg.Providable;
import scrapi.alg.Randomizable;
import scrapi.key.Keyable;
import scrapi.key.PrivateKey;
import scrapi.lang.Builder;

public interface SignerBuilder<K extends PrivateKey<?, ?>>
        extends Providable<SignerBuilder<K>>,
        Randomizable<SignerBuilder<K>>,
        Keyable<K, SignerBuilder<K>>,
        Builder<Signer> {
}
