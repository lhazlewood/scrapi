package scrapi.impl.msg;

import scrapi.alg.Size;
import scrapi.impl.key.DefaultKeyable;
import scrapi.impl.key.DefaultOctetSecretKeyGenerator;
import scrapi.key.KeyGenerator;
import scrapi.key.Keyable;
import scrapi.key.OctetSecretKey;
import scrapi.msg.Digest;
import scrapi.msg.HashAlgorithm;
import scrapi.msg.Hasher;
import scrapi.msg.HmacAlgorithm;

import java.security.Provider;
import java.util.function.Consumer;

public class DefaultHmacAlgorithm extends AbstractMacAlgorithm<
        OctetSecretKey,
        Keyable.Param<OctetSecretKey>,
        KeyGenerator.Basic<OctetSecretKey>,
        Digest<HmacAlgorithm>,
        HmacAlgorithm
        > implements HmacAlgorithm {

    private static String hmacId(HashAlgorithm alg) {
        String suffix = suffix(alg);
        return "Hmac" + suffix;
    }

    DefaultHmacAlgorithm(HashAlgorithm alg) {
        this(hmacId(alg), null, alg.size());
    }

    private DefaultHmacAlgorithm(String id, Provider provider, Size digestSize) {
        super(id, provider, digestSize, () -> new DefaultOctetSecretKeyGenerator(id, digestSize));
    }

    @Override
    public Hasher<Digest<HmacAlgorithm>> with(Consumer<Keyable.Param<OctetSecretKey>> p) {
        DefaultKeyable<OctetSecretKey> param = new DefaultKeyable<>(this.ID);
        param.provider(this.PROVIDER);
        p.accept(param);
        return new DefaultMacHasher<>(this, param.provider(), param.key());
    }
}
