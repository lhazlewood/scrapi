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

import scrapi.key.ConfidentialKey;
import scrapi.key.Key;
import scrapi.key.KeyGenerator;
import scrapi.key.KeyGeneratorSupplier;
import scrapi.key.Keyable;
import scrapi.util.Assert;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface AuthenticityAlgorithm<
        PK extends ConfidentialKey<?>,
        VK extends Key<?>,
        P extends MessageConsumer<P> & Supplier<byte[]>,
        V extends MessageConsumer<V> & Predicate<byte[]>,
        PP extends Keyable<PK, PP>,
        VP extends Keyable<VK, VP>,
        G extends KeyGenerator<PK, G>
        >
        extends IntegrityAlgorithm, KeyGeneratorSupplier<PK, G> {

    default P producer(PK key) {
        Assert.notNull(key, "key cannot be null.");
        return producer(c -> c.key(key));
    }

    P producer(Consumer<PP> c);

    default V verifier(VK key) {
        Assert.notNull(key, "key cannot be null.");
        return verifier(c -> c.key(key));
    }

    V verifier(Consumer<VP> c);
}
