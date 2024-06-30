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
import scrapi.impl.key.DefaultPbeKey
import scrapi.key.PbeKey
import scrapi.key.SecretKey
import scrapi.msg.MacAlgorithm
import scrapi.util.Bytes

import javax.crypto.Mac
import java.nio.ByteBuffer
import java.security.MessageDigest

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class StandardMacAlgorithmsTest {

    static void assertId(String algId, MacAlgorithm alg) {
        assertEquals algId, alg.id()
    }

    static SecretKey<? extends javax.crypto.SecretKey> newKey(MacAlgorithm alg) {
        def b = alg.keygen()
        if (b instanceof PbeKey.Builder) b.iterations(DefaultPbeKey.MIN_ITERATIONS) // keep tests fast
        return b.get() as SecretKey
    }

    static def hasher(def key, def builder) {
        if (key instanceof PbeKey) {
            builder.salt(key.salt())
            builder.iterations(key.iterations())
        }
        return builder.key(key).get()
    }

    @Test
    void equality() {
        assertEquals Algs.Mac.get(), new StandardMacAlgorithms()
    }

    @Test
    void count() {
        assertEquals 26, Algs.Mac.get().size()
    }

    @SuppressWarnings('GrDeprecatedAPIUsage')
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
        assertId 'PBEWithHmacSHA1', Algs.Mac.PBEHS1
        assertId 'PBEWithHmacSHA224', Algs.Mac.PBEHS224
        assertId 'PBEWithHmacSHA256', Algs.Mac.PBEHS256
        assertId 'PBEWithHmacSHA384', Algs.Mac.PBEHS384
        assertId 'PBEWithHmacSHA512', Algs.Mac.PBEHS512
        assertId 'PBEWithHmacSHA512/224', Algs.Mac.PBEHS512_224
        assertId 'PBEWithHmacSHA512/256', Algs.Mac.PBEHS512_256
        assertId 'HmacPBESHA1', Algs.Mac.PKCS12HS1
        assertId 'HmacPBESHA224', Algs.Mac.PKCS12HS224
        assertId 'HmacPBESHA256', Algs.Mac.PKCS12HS256
        assertId 'HmacPBESHA384', Algs.Mac.PKCS12HS384
        assertId 'HmacPBESHA512', Algs.Mac.PKCS12HS512
        assertId 'HmacPBESHA512/224', Algs.Mac.PKCS12HS512_224
        assertId 'HmacPBESHA512/256', Algs.Mac.PKCS12HS512_256
    }

    @Test
    void digestNoData() {
        for (MacAlgorithm alg : Algs.Mac.get().values()) {
            def key = newKey(alg)

            byte[] digest = hasher(key, alg.digester()).get() // no 'apply' methods called, no data processed
            def jca = Mac.getInstance(alg.id() as String)
            jca.getMacLength()
            jca.init(key.toJcaKey())
            byte[] jcaDigest = jca.doFinal()
            assertTrue MessageDigest.isEqual(jcaDigest, digest)
            assertEquals alg.digestSize().bits(), Bytes.bitLength(digest)
            assertTrue hasher(key, alg.verifier()).test(digest)
        }
    }

    @Test
    void digestOneByte() {
        Algs.Mac.get().values().each {
            def key = newKey(it)
            def b = Bytes.random(1)[0]
            byte[] digest = hasher(key, it.digester()).apply(b).get()
            def jca = Mac.getInstance(it.id())
            jca.init(key.toJcaKey())
            jca.update(b)
            def jcaDigest = jca.doFinal()
            assertTrue MessageDigest.isEqual(jcaDigest, digest) // assert same result as JCA
            assertEquals it.digestSize().bits(), Bytes.bitLength(digest)
            assertTrue hasher(key, it.verifier()).apply(b).test(digest)
        }
    }

    @Test
    void digestByteBuffer() {
        Algs.Mac.get().values().each {
            def key = newKey(it)
            def buf = ByteBuffer.wrap(Bytes.random(16))
            byte[] digest = hasher(key, it.digester()).apply(buf).get()

            buf.rewind() // to use in jca Mac calculation:
            def jca = Mac.getInstance(it.id())
            jca.init(key.toJcaKey())
            jca.update(buf)
            def jcaDigest = jca.doFinal()
            buf.rewind()

            assertTrue MessageDigest.isEqual(jcaDigest, digest) // assert same result as JCA
            assertEquals it.digestSize().bits(), Bytes.bitLength(digest)
            assertTrue hasher(key, it.verifier()).apply(buf).test(digest)
        }
    }

    @Test
    void digestExactLengths() {
        for (MacAlgorithm alg : Algs.Mac.get().values()) {
            def key = newKey(alg)
            byte[] data = Bytes.randomBits(alg.digestSize().bits())
            byte[] digest = hasher(key, alg.digester()).apply(data).get()
            assertEquals alg.digestSize().bits(), Bytes.bitLength(digest)
            assertTrue hasher(key, alg.verifier()).apply(data).test(digest)
        }
    }

    @Test
    void digestSmallerLengths() {
        for (MacAlgorithm alg : Algs.Mac.get().values()) {
            def key = newKey(alg)
            byte[] data = Bytes.randomBits(alg.digestSize().bits() - Byte.SIZE) // 1 byte less than digest length
            def h = hasher(key, alg.digester())
            byte[] digest = h.apply(data).get()
            assertEquals alg.digestSize().bits(), Bytes.bitLength(digest) // digest is still same as alg bitLength
            h = hasher(key, alg.verifier())
            assertTrue h.apply(data).test(digest)
        }
    }

    @Test
    void digestLargerLengths() {
        for (MacAlgorithm alg : Algs.Mac.get().values()) {
            def key = newKey(alg)
            def h = hasher(key, alg.digester())
            def bits = alg.digestSize().bits()
            def a = Bytes.randomBits(bits)
            def b = Bytes.randomBits(bits)
            def c = Bytes.randomBits(bits)
            // multiple .apply calls, total bytes applied are larger than bitLength:
            byte[] digest = h.apply(a).apply(b).apply(c).get()
            assertEquals alg.digestSize().bits(), Bytes.bitLength(digest) // digest is still same as alg bitLength

            h = hasher(key, alg.verifier())
            assertTrue h.apply(a).apply(b).apply(c).test(digest)
        }
    }
}
