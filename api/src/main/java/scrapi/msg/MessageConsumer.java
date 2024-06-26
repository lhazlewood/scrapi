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
import java.util.function.Function;

public interface MessageConsumer<T extends MessageConsumer<T>> extends Function<byte[], T> {

    /**
     * Processes the specified byte.
     *
     * @param input the byte to process
     */
    T apply(byte input);

    /**
     * Processes the specified array of bytes.
     *
     * @param input the array of bytes.
     */
    @Override
    T apply(byte[] input);

    /**
     * Processes the specified array of bytes, starting at the specified offset.
     *
     * @param input  the array of bytes.
     * @param offset the offset to start from in the array of bytes.
     * @param len    the number of bytes to use, starting at {@code offset}.
     */
    T apply(byte[] input, int offset, int len);

    /**
     * Processes the specified ByteBuffer using {@code input.remaining()} bytes starting at {@code input.position()}.
     * Upon return, the buffer's position will be equal to its limit; its limit will not have changed.
     *
     * @param input the ByteBuffer to process.
     */
    T apply(ByteBuffer input);
}
