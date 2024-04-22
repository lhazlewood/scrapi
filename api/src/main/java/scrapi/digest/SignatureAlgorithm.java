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
package scrapi.digest;

import scrapi.jca.Randomizable;
import scrapi.key.KeyBuilderSupplier;
import scrapi.key.Keyed;
import scrapi.key.PrivateKey;
import scrapi.key.PublicKey;

public interface SignatureAlgorithm<U extends PublicKey<?>, R extends PrivateKey<?, U>, KB extends PrivateKey.Builder<U, R, KB>>
        extends DigestAlgorithm<SignatureAlgorithm<U, R, KB>>,
        Keyed<R, Digester<?>>,
        Randomizable<SignatureAlgorithm<U, R, KB>>,
        KeyBuilderSupplier<R, KB> {

    Verifier<?> key(U publicKey);
}
