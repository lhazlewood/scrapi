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
import scrapi.lang.Builder;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface AuthenticityAlgorithm<
        DK extends ConfidentialKey<?>,
        VK extends Key<?>,
        D extends MessageConsumer<D> & Supplier<byte[]>,
        V extends MessageConsumer<V> & Predicate<byte[]>,
        DB extends Keyable<DK, DB> & Builder<D>,
        VB extends Keyable<VK, VB> & Builder<V>,
        G extends KeyGenerator<DK, G>
        >
        extends IntegrityAlgorithm<D, V, DB, VB>, KeyGeneratorSupplier<DK, G> {
}
