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

public interface SignatureAlgorithm<
        S extends PrivateKey<?, P>,
        P extends PublicKey<?>,
        SP extends Keyable<S, SP> & Randomizable<SP>,
        VP extends Keyable<P, VP>,
        G extends KeyGenerator<S, G>,
        T extends SignatureAlgorithm<S, P, SP, VP, G, T>
        >
        extends AuthenticityAlgorithm<S, P, SP, VP, Signature<T>, Signer<T>, Verifier, G, T> {
}
