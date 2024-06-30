package scrapi.msg;

import scrapi.key.Keyable;
import scrapi.key.PublicKey;
import scrapi.lang.Builder;

public interface VerifierBuilder<K extends PublicKey<?>> extends Keyable<K, VerifierBuilder<K>>, Builder<Verifier> {
}
