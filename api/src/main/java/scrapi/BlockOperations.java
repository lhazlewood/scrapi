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

import java.nio.ByteBuffer;
import java.util.function.Function;

public interface BlockOperations extends Function<byte[], byte[]> {

    byte[] apply(byte[] input);

    byte[] apply(byte[] input, int inputOffset, int inputLen);

    int apply(byte[] input, int inputOffset, int inputLen, byte[] output);

    int apply(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset);

    int apply(ByteBuffer input, ByteBuffer output);
}
