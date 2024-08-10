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

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public interface HashAlgorithm extends IntegrityAlgorithm<Hasher, Hasher, Supplier<Hasher>, Supplier<Hasher>>, DigestSized, Hasher {

    default Supplier<Hasher> verifier() {
        return creator();
    }

    @Override
    default boolean test(byte[] bytes) {
        return creator().get().test(bytes);
    }

    @Override
    default byte[] get() {
        return creator().get().get();
    }

    @Override
    default Hasher apply(byte input) {
        return creator().get().apply(input);
    }

    @Override
    default Hasher apply(byte[] input) {
        return creator().get().apply(input);
    }

    @Override
    default Hasher apply(byte[] input, int offset, int len) {
        return creator().get().apply(input);
    }

    @Override
    default Hasher apply(ByteBuffer input) {
        return creator().get().apply(input);
    }
}
