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
package scrapi.impl.msg

import org.junit.jupiter.api.Test
import scrapi.alg.Algs
import scrapi.util.Bytes

import java.nio.ByteBuffer
import java.security.MessageDigest

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class StandardHashAlgorithmsTest {

    @Test
    void equality() {
        assertEquals Algs.Hash.get(), new StandardHashAlgorithms()
    }

    @Test
    void count() {
        assertEquals 13, Algs.Hash.get().size()
    }

    @Test
    void ids() {
        assertEquals 'MD2', Algs.Hash.MD2.id()
        assertEquals 'MD5', Algs.Hash.MD5.id()
        assertEquals 'SHA-1', Algs.Hash.SHA_1.id()
        assertEquals 'SHA-224', Algs.Hash.SHA_224.id()
        assertEquals 'SHA-256', Algs.Hash.SHA_256.id()
        assertEquals 'SHA-384', Algs.Hash.SHA_384.id()
        assertEquals 'SHA-512', Algs.Hash.SHA_512.id()
        assertEquals 'SHA-512/224', Algs.Hash.SHA_512_224.id()
        assertEquals 'SHA-512/256', Algs.Hash.SHA_512_256.id()
        assertEquals 'SHA3-224', Algs.Hash.SHA3_224.id()
        assertEquals 'SHA3-256', Algs.Hash.SHA3_256.id()
        assertEquals 'SHA3-384', Algs.Hash.SHA3_384.id()
        assertEquals 'SHA3-512', Algs.Hash.SHA3_512.id()
    }

    @Test
    void sizes() {
        assertEquals 128, Algs.Hash.MD2.size().bits()
        assertEquals 128, Algs.Hash.MD5.size().bits()
        assertEquals 160, Algs.Hash.SHA_1.size().bits()
        assertEquals 224, Algs.Hash.SHA_224.size().bits()
        assertEquals 256, Algs.Hash.SHA_256.size().bits()
        assertEquals 384, Algs.Hash.SHA_384.size().bits()
        assertEquals 512, Algs.Hash.SHA_512.size().bits()
        assertEquals 224, Algs.Hash.SHA_512_224.size().bits()
        assertEquals 256, Algs.Hash.SHA_512_256.size().bits()
        assertEquals 224, Algs.Hash.SHA3_224.size().bits()
        assertEquals 256, Algs.Hash.SHA3_256.size().bits()
        assertEquals 384, Algs.Hash.SHA3_384.size().bits()
        assertEquals 512, Algs.Hash.SHA3_512.size().bits()
    }

    @Test
    void digestNoData() {
        Algs.Hash.get().values().each {
            byte[] digest = it.get().get() // no 'apply' methods called, no data processed
            def jcaDigest = MessageDigest.getInstance(it.id()).digest()
            assertTrue MessageDigest.isEqual(jcaDigest, digest) // assert same result as JCA
            assertEquals it.size().bits(), Bytes.bitLength(digest)
        }
    }

    @Test
    void digestOneByte() {
        Algs.Hash.get().values().each {
            def b = Bytes.random(1)[0]
            byte[] digest = it.get().apply(b).get()
            def jca = MessageDigest.getInstance(it.id())
            jca.update(b)
            def jcaDigest = jca.digest()
            assertTrue MessageDigest.isEqual(jcaDigest, digest) // assert same result as JCA
            assertEquals it.size().bits(), Bytes.bitLength(digest)
        }
    }

    @Test
    void digestByteBuffer() {
        Algs.Hash.get().values().each {
            def buf = ByteBuffer.wrap(Bytes.random(16))
            byte[] digest = it.get().apply(buf).get()

            buf.rewind() // to use in jca digest calculation:
            def jca = MessageDigest.getInstance(it.id())
            jca.update(buf)
            def jcaDigest = jca.digest()

            assertTrue MessageDigest.isEqual(jcaDigest, digest) // assert same result as JCA
            assertEquals it.size().bits(), Bytes.bitLength(digest)
        }
    }


    @Test
    void digestExactLengths() {
        Algs.Hash.get().values().each {
            byte[] data = Bytes.randomBits(it.size().bits())
            byte[] digest = it.get().apply(data).get()
            assertEquals it.size().bits(), Bytes.bitLength(digest)
        }
    }

    @Test
    void applyArrayWithLength() {
        Algs.Hash.get().values().each {
            byte[] data = Bytes.randomBits(it.size().bits())
            byte[] digest = it.get().apply(data, 0, data.length).get()
            assertEquals it.size().bits(), Bytes.bitLength(digest)
        }
    }

    @Test
    void digestSmallerLengths() {
        Algs.Hash.get().values().each { alg ->
            byte[] data = Bytes.randomBits(alg.size().bits() - Byte.SIZE) // 1 byte less than digest length
            byte[] digest = alg.get().apply(data).get()
            assertEquals alg.size().bits(), Bytes.bitLength(digest) // digest is still same as alg digest length
        }
    }

    @Test
    void digestLargerLengths() {
        Algs.Hash.get().values().each { alg ->
            def hasher = alg.get()
            // multiple .apply calls larger than bitlength
            3.times { hasher.apply(Bytes.randomBits(alg.size().bits())) }
            byte[] digest = hasher.get()
            assertEquals alg.size().bits(), Bytes.bitLength(digest) // digest is still same as alg bitlength
        }
    }

    @Test
    void verify() {
        Algs.Hash.get().values().each {
            byte[] data = Bytes.randomBits(it.size().bits())
            byte[] digest = it.get().apply(data).get()
            byte[] jcaDigest = MessageDigest.getInstance(it.id()).digest(data)
            assertTrue MessageDigest.isEqual(digest, jcaDigest)
            assertTrue it.get().apply(data).test(digest)
        }
    }
}
