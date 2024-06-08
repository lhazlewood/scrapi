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
package scrapi.key;

import java.security.KeyPair;

public interface PrivateKey<K extends java.security.PrivateKey, P extends PublicKey<?>> extends AsymmetricKey<K>, ConfidentialKey<K> {

    P publicKey();

    KeyPair toJcaKeyPair();

    interface Generator<K extends PrivateKey<?, ?>, T extends Generator<K, T>>
            extends KeyGenerator<K, T> {
    }

    interface Mutator<U extends PublicKey<?>, T extends Mutator<U, T>> {
        T publicKey(U publicKey);
    }

    interface Builder<P extends PublicKey<?>, K extends PrivateKey<?, P>, T extends Builder<P, K, T>>
            extends Mutator<P, T>, KeyBuilder<K, T> {
    }
}
