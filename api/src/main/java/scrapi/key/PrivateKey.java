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

import scrapi.Destroyable;

public interface PrivateKey<J extends java.security.PrivateKey, U extends PublicKey<?>> extends AsymmetricKey<J>, Destroyable {

    U publicKey();

    interface Mutators<U extends PublicKey<?>, T extends Mutators<U, T>> {
        T publicKey(U publicKey);
    }

    interface Builder<P extends PublicKey<?>, K extends PrivateKey<?, P>, T extends Builder<P, K, T>>
            extends Mutators<P, T>, KeyBuilder<K, T> {
    }
}
