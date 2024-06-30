package scrapi.impl.msg;

import scrapi.impl.key.KeyableSupport;
import scrapi.key.PublicKey;
import scrapi.msg.Verifier;
import scrapi.msg.VerifierBuilder;

public class DefaultVerifierBuilder<K extends PublicKey<?>>
        extends KeyableSupport<K, VerifierBuilder<K>> implements VerifierBuilder<K> {

    public DefaultVerifierBuilder(String jcaName) {
        super(jcaName);
    }

    @Override
    public Verifier build() {
        return new JcaSignatureSupport.JcaVerifier(this.jcaName, this.provider, this.key);
    }
}
