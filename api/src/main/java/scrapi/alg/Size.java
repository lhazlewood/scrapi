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
package scrapi.alg;

import scrapi.util.Bytes;

public interface Size extends Comparable<Size> {

    int bits();

    int bytes();

    static Size bits(int bits) {
        return DefaultSize.bits(bits);
    }

    static Size bytes(int bytes) {
        return DefaultSize.bytes(bytes);
    }

    static Size of(byte[] bytes) {
        return DefaultSize.bytes(Bytes.length(bytes));
    }
}
