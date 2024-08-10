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

import java.util.function.Supplier;

public interface MacAlgorithm<
        K extends SymmetricKey,
        DB extends Keyable<K, DB> & Supplier<Hasher>,
        G extends KeyGenerator<K, G>
        >
        extends AuthenticityAlgorithm<K, K, Hasher, Hasher, DB, DB, G>, DigestSized {

    @Override
    default Hasher key(K key) {
        Assert.notNull(key, "Key cannot be null.");
        DB builder = creator();
        builder.key(key);
        return builder.get();
    }

    @Override
    default DB verifier() {
        return creator();
    }
}
