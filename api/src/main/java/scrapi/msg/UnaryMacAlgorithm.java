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

import scrapi.key.KeyGenerator;
import scrapi.key.Keyable;
import scrapi.key.SymmetricKey;
import scrapi.util.Assert;

/**
 * A Unary MAC algorithm requires only a single {@code key} parameter to produce a {@link Hasher} used
 * for MAC computation.
 */
public interface UnaryMacAlgorithm<
        K extends SymmetricKey,
        P extends Keyable<K, P>,
        G extends KeyGenerator<K, G>,
        D extends Digest<T>,
        T extends UnaryMacAlgorithm<K, P, G, D, T>
        > extends MacAlgorithm<K, P, G, D, T> {

    default Hasher<D> with(K key) {
        Assert.notNull(key, "Key cannot be null.");
        return with(c -> c.key(key));
    }
}
