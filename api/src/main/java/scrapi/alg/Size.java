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

/**
 * A {@code Size} represents cryptographic algorithm size/strength values, such as block sizes or key lengths,
 * eliminating the ambiguity that would arise otherwise when using only an {@code int} or {@code long} value.  Because
 * a single {@code int} or {@code long} value can only indicate magnitude, and not units such as
 * <em>bits</em> or <em>bytes</em>, using a {@code Size} instance ensures any value can accurately be represented
 * as bits or bytes.
 *
 * <p>For example, a potential API that only uses integer values can be confusing:</p>
 *
 * <blockquote><pre>
 * void keyLength(int length);</pre></blockquote>
 *
 * <p>It is difficult to tell if the above {@code length} value should reflect bits or bytes, and the
 * only way to determine is to read the (hopefully) well-written JavaDoc.</p>
 *
 * <p>Conversely, using a {@code Size} eliminates this confusion and allows either the API caller or
 * implementor to represent values as desired. For example:</p>
 *
 * <blockquote><pre>
 * void keyLength(Size size);</pre></blockquote>
 *
 * <p>If the caller wanted to represent a length of 32 bytes, they could then invoke this method as follows:</p>
 *
 * <blockquote><pre>
 * alg.keyLength(Size.{@link #bytes() bytes}(32));</pre></blockquote>
 *
 * <p>But the API implementor might prefer the length in bits, since bits are regularly encountered in algorithm
 * design. The implementation can easily obtain the bit value as desired, for example:</p>
 *
 * <blockquote><pre>
 * this.size = size;  // then sometime later:
 * int bits = this.size.{@link #bits()};</pre></blockquote>
 *
 * @since SCRAPI_RELEASE_VERSION
 */
public sealed interface Size extends Comparable<Size> permits DefaultSize {

    /**
     * Constant for zero bits or bytes.
     */
    Size ZERO = DefaultSize.ZERO;

    /**
     * Returns the number of bits, which may or may not be perfectly divisible by 8. The minimum number
     * of bytes (octets) required to contain this number of bits is available via the {@link #bytes()} methods.
     *
     * @return the number of bits, which may or may not be perfectly divisible by 8.
     * @see #bytes()
     */
    int bits();

    /**
     * Returns the minimum number of bytes (octets) required to contain the number of {@link #bits() bits}, equal to
     * <code>({@link #bits()} + 7) / 8</code> using integer (zero remainder) division.
     *
     * @return the minimum number of bytes (octets) required to contain the number of {@link #bits()}.
     * @see #bits()
     */
    int bytes();

    /**
     * Returns a {@code Size} that reflects an exact number of bits, which may or may not be perfectly
     * divisible by 8.
     *
     * <p>It is possible for some algorithms to utilize odd bit sizes (such as
     * the {@code 255} bits value used in X25519 Edwards Curve
     * <a href="https://www.rfc-editor.org/rfc/rfc7748.html#section-5">u-coordinate encoding</a>).  In these cases,
     * the returned instance's {@link #bytes()} value is the minimum number of bytes
     * (octets) needed to contain that number of bits, equal to <code>(bits + 7) / 8</code> using integer
     * (zero remainder) division.
     *
     * @param bits the number of bits.
     * @return a {@code Size} instance that reflects the exact number of bits.
     * @throws IllegalArgumentException if {@code bits} is less than zero.
     */
    static Size bits(int bits) throws IllegalArgumentException {
        return DefaultSize.bits(bits);
    }

    /**
     * Returns a {@code Size} that reflects the specified number of bytes (octets), equal to {@code 8 * bytes} bits.
     *
     * @param bytes the number of bytes (octets)
     * @return a {@code Size} that reflects the specified number of bytes (octets), with {@code 8 * bytes} bits.
     * @throws IllegalArgumentException if {@code bytes} is less than zero, or more than
     *                                  {@code floor(Integer.MAX_VALUE / 8) + 1}, which is the largest number of bytes
     *                                  that can be represented as an integer bit value.
     */
    static Size bytes(int bytes) throws IllegalArgumentException {
        return DefaultSize.bytes(bytes);
    }

    /**
     * Returns the Size of the specified {@code bytes} array.
     *
     * @param bytes the byte array to inspect
     * @return the Size of the specified {@code bytes} array.
     */
    static Size of(byte[] bytes) {
        return DefaultSize.bytes(Bytes.length(bytes));
    }
}
