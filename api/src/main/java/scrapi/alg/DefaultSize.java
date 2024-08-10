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

import scrapi.util.Assert;
import scrapi.util.Bytes;

final class DefaultSize implements Size {

    private static final int MIN_BITS = 0;
    private static final int MAX_BITS = Integer.MAX_VALUE - 7;
    private static final int MIN_BYTES = 0;
    private static final int MAX_BYTES = MAX_BITS / Byte.SIZE;
    private static final String MIN_BITS_MSG = "bits must be >= " + MIN_BITS;
    private static final String MAX_BITS_MSG = "bits must be <= " + MAX_BITS;
    private static final String MIN_BYTES_MSG = "bytes must be >= " + MIN_BYTES;
    private static final String MAX_BYTES_MSG = "bytes must be <= " + MAX_BYTES;

    static final Size ZERO = new DefaultSize(0);
    static final Size MAX = new DefaultSize(MAX_BITS);

    private final int bits;
    private final int bytes;

    static Size bits(int bits) {
        return switch (bits) {
            case 0 -> ZERO;
            case MAX_BITS -> MAX;
            default -> new DefaultSize(bits);
        };
    }

    static Size bytes(int bytes) {
        int val = Assert.lte(Assert.gte(bytes, MIN_BYTES, MIN_BYTES_MSG), MAX_BYTES, MAX_BYTES_MSG);
        return bits(val * Byte.SIZE);
    }

    private DefaultSize(int bits) {
        this.bits = Assert.lte(Assert.gte(bits, MIN_BITS, MIN_BITS_MSG), MAX_BITS, MAX_BITS_MSG);
        this.bytes = Bytes.length(bits);
    }

    @Override
    public int bits() {
        return this.bits;
    }

    @Override
    public int bytes() {
        return this.bytes;
    }

    @Override
    public int hashCode() {
        return bits;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        return o instanceof Size s && bits == s.bits();
    }

    @Override
    public String toString() {
        return Bytes.bitsMsg(this.bits);
    }

    @Override
    public int compareTo(Size o) {
        Assert.notNull(o, "compared size must not be null.");
        return Integer.compare(this.bits, o.bits());
    }
}
