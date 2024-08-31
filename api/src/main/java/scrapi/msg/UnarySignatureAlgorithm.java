/*
 * Copyright © 2024 Les Hazlewood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package scrapi.msg;

import scrapi.alg.Randomizable;
import scrapi.key.KeyGenerator;
import scrapi.key.Keyable;
import scrapi.key.PrivateKey;
import scrapi.key.PublicKey;

/**
 * A Unary {@code SignatureAlgorithm} requires only a single {@code key} parameter to produce either a
 * {@link Signer} or {@link Verifier}.
 *
 * @since SCRAPI_RELEASE_VERSION
 */
public interface UnarySignatureAlgorithm<
        S extends PrivateKey<?, P>,
        P extends PublicKey<?>,
        SP extends Keyable<S, SP> & Randomizable<SP>,
        VP extends Keyable<P, VP>,
        G extends KeyGenerator<S, G>,
        T extends UnarySignatureAlgorithm<S, P, SP, VP, G, T>
        >
        extends SignatureAlgorithm<S, P, SP, VP, G, T> {

    default Signer<T> with(S priv) {
        return with(c -> c.key(priv));
    }

    default Verifier with(P pub) {
        return verifier(c -> c.key(pub));
    }
}
