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

import scrapi.alg.Sized;
import scrapi.key.KeyGenerator;
import scrapi.key.Keyable;
import scrapi.key.SymmetricKey;

import java.util.function.Consumer;

public interface MacAlgorithm<
        K extends SymmetricKey,
        P extends Keyable<K, P>,
        G extends KeyGenerator<K, G>,
        D extends Digest<T>,
        T extends MacAlgorithm<K, P, G, D, T>
        >
        extends AuthenticityAlgorithm<K, K, P, P, D, Hasher<D>, Hasher<D>, G, T>, Sized {

    default Hasher<D> verifier(Consumer<P> p) {
        return with(p);
    }
}
