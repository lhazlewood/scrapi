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
import scrapi.digest.HashAlgorithm
import scrapi.util.Bytes

import static org.junit.jupiter.api.Assertions.assertEquals

class StandardHashAlgorithmsTest {

    static void assertId(String algId, HashAlgorithm alg) {
        assertEquals algId, alg.id()
    }

    @Test
    void equality() {
        assertEquals Algs.Hash.get(), new StandardHashAlgorithms()
    }

    @Test
    void count() {
        assertEquals 13, Algs.Hash.get().size()
    }

    @Test
    void instances() {
        assertId 'MD2', Algs.Hash.MD2
        assertId 'MD5', Algs.Hash.MD5
        assertId 'SHA-1', Algs.Hash.SHA_1
        assertId 'SHA-224', Algs.Hash.SHA_224
        assertId 'SHA-256', Algs.Hash.SHA_256
        assertId 'SHA-384', Algs.Hash.SHA_384
        assertId 'SHA-512', Algs.Hash.SHA_512
        assertId 'SHA-512/224', Algs.Hash.SHA_512_224
        assertId 'SHA-512/256', Algs.Hash.SHA_512_256
        assertId 'SHA3-224', Algs.Hash.SHA3_224
        assertId 'SHA3-256', Algs.Hash.SHA3_256
        assertId 'SHA3-384', Algs.Hash.SHA3_384
        assertId 'SHA3-512', Algs.Hash.SHA3_512
    }

    @Test
    void digestNoData() {
        Algs.Hash.get().values().each {
            byte[] digest = it.get().get() // no 'apply' methods called, no data processed
            assertEquals it.bitLength(), Bytes.bitLength(digest)
        }
    }

    @Test
    void digestExactLengths() {
        Algs.Hash.get().values().each {
            byte[] data = Bytes.randomBits(it.bitLength())
            byte[] digest = it.get().apply(data).get()
            assertEquals it.bitLength(), Bytes.bitLength(digest)
        }
    }

    @Test
    void digestSmallerLengths() {
        Algs.Hash.get().values().each {
            byte[] data = Bytes.randomBits(it.bitLength() - Byte.SIZE) // 1 byte less than digest length
            byte[] digest = it.get().apply(data).get()
            assertEquals it.bitLength(), Bytes.bitLength(digest) // digest is still same as alg bitlength
        }
    }

    @Test
    void digestLargerLengths() {
        Algs.Hash.get().values().each { alg ->
            def hasher = alg.get()
            // multiple .apply calls larger than bitlength
            3.times { hasher.apply(Bytes.randomBits(alg.bitLength())) }
            byte[] digest = hasher.get()
            assertEquals alg.bitLength(), Bytes.bitLength(digest) // digest is still same as alg bitlength
        }
    }
}