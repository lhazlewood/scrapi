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

import scrapi.DataConsumer;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public interface HashAlgorithm extends DigestAlgorithm<HashAlgorithm>, Supplier<Hasher>, DataConsumer<Hasher> {

    @Override
    default Hasher apply(byte input) {
        return get().apply(input);
    }

    @Override
    default Hasher apply(byte[] input) {
        return get().apply(input);
    }

    @Override
    default Hasher apply(byte[] input, int offset, int len) {
        return get().apply(input, offset, len);
    }

    @Override
    default Hasher apply(ByteBuffer input) {
        return get().apply(input);
    }
}
