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
package scrapi;

import scrapi.util.Bytes;

import java.nio.ByteBuffer;
import java.util.function.Function;

public interface DataConsumer<T extends DataConsumer<T>> extends Function<byte[], T> {

    /**
     * Processes the specified byte.
     *
     * @param input the byte to process
     */
    default T apply(byte input) {
        return apply(new byte[]{input});
    }

    /**
     * Processes the specified array of bytes.
     *
     * @param input the array of bytes.
     */
    @Override
    default T apply(byte[] input) {
        return apply(input, 0, Bytes.length(input));
    }

    /**
     * Processes the specified array of bytes, starting at the specified offset.
     *
     * @param input  the array of bytes.
     * @param offset the offset to start from in the array of bytes.
     * @param len    the number of bytes to use, starting at {@code offset}.
     */
    default T apply(byte[] input, int offset, int len) {
        return apply(ByteBuffer.wrap(input, offset, len));
    }

    /**
     * Processes the specified ByteBuffer using {@code input.remaining()} bytes starting at {@code input.position()}.
     * Upon return, the buffer's position will be equal to its limit; its limit will not have changed.
     *
     * @param input the ByteBuffer to process.
     */
    T apply(ByteBuffer input);
}
