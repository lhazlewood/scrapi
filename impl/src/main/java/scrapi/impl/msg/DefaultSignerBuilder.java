package scrapi.impl.msg;

import scrapi.impl.key.KeyableSupport;
import scrapi.key.PrivateKey;
import scrapi.msg.Signer;
import scrapi.msg.SignerBuilder;

public class DefaultSignerBuilder<K extends PrivateKey<?, ?>>
        extends KeyableSupport<K, SignerBuilder<K>> implements SignerBuilder<K> {

    public DefaultSignerBuilder(String jcaName) {
        super(jcaName);
    }

    @Override
    public Signer build() {
        return new JcaSignatureSupport.JcaSigner(this.jcaName, this.provider, this.random, this.key);
    }
}
