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
package scrapi.impl.digest

import org.junit.jupiter.api.Test
import scrapi.Algs
import scrapi.digest.MacAlgorithm
import scrapi.impl.key.DefaultPbeKeyBuilder
import scrapi.key.PbeKey
import scrapi.key.SecretKey
import scrapi.util.Bytes

import static org.junit.jupiter.api.Assertions.assertEquals

class StandardMacAlgorithmsTest {

    static void assertId(String algId, MacAlgorithm alg) {
        assertEquals algId, alg.id()
    }

    static SecretKey<?> newKey(MacAlgorithm alg) {
        def b = alg.key()
        if (b instanceof PbeKey.Builder) {
            return b.iterations(DefaultPbeKeyBuilder.MIN_ITERATIONS) // keep tests fast
                    .build()
        }
        return b.build() as SecretKey
    }

    @Test
    void equality() {
        assertEquals Algs.Mac.get(), new StandardMacAlgorithms()
    }

    @Test
    void count() {
        assertEquals 26, Algs.Mac.get().size()
    }

    @Test
    void instances() {
        assertId 'HmacMD5', Algs.Mac.HMD5
        assertId 'HmacSHA1', Algs.Mac.HS1
        assertId 'HmacSHA224', Algs.Mac.HS224
        assertId 'HmacSHA256', Algs.Mac.HS256
        assertId 'HmacSHA384', Algs.Mac.HS384
        assertId 'HmacSHA512', Algs.Mac.HS512
        assertId 'HmacSHA512/224', Algs.Mac.HS512_224
        assertId 'HmacSHA512/256', Algs.Mac.HS512_256
        assertId 'HmacSHA3-224', Algs.Mac.HS3_224
        assertId 'HmacSHA3-256', Algs.Mac.HS3_256
        assertId 'HmacSHA3-384', Algs.Mac.HS3_384
        assertId 'HmacSHA3-512', Algs.Mac.HS3_512
    }

    @Test
    void digestZeroLength() {
        for (MacAlgorithm alg : Algs.Mac.get().values()) {
            def key = newKey(alg)
            byte[] digest = alg.key(key).get() // no 'apply' methods called, no data processed
            assertEquals alg.bitLength(), Bytes.bitLength(digest)
        }
    }

    @Test
    void digestExactLengths() {
        for (MacAlgorithm alg : Algs.Mac.get().values()) {
            def key = newKey(alg)
            byte[] data = Bytes.randomBits(alg.bitLength())
            byte[] digest = alg.key(key).apply(data).get()
            assertEquals alg.bitLength(), Bytes.bitLength(digest)
        }
    }

    @Test
    void digestSmallerLengths() {
        for (MacAlgorithm alg : Algs.Mac.get().values()) {
            def key = newKey(alg)
            byte[] data = Bytes.randomBits(alg.bitLength() - Byte.SIZE) // 1 byte less than digest length
            byte[] digest = alg.key(key).apply(data).get()
            assertEquals alg.bitLength(), Bytes.bitLength(digest) // digest is still same as alg bitLength
        }
    }

    @Test
    void digestLargerLengths() {
        for (MacAlgorithm alg : Algs.Mac.get().values()) {
            def key = newKey(alg)
            def hasher = alg.key(key)
            // multiple .apply calls, total bytes applied are larger than bitLength:
            3.times { hasher.apply(Bytes.randomBits(alg.bitLength())) }
            byte[] digest = hasher.get()
            assertEquals alg.bitLength(), Bytes.bitLength(digest) // digest is still same as alg bitLength
        }
    }
}