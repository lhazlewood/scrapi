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
import scrapi.impl.key.DefaultPassword
import scrapi.key.Password
import scrapi.key.SecretKey
import scrapi.msg.Hasher
import scrapi.msg.MacAlgorithm
import scrapi.msg.PasswordMacAlgorithm
import scrapi.util.Bytes

import javax.crypto.Mac
import javax.crypto.spec.PBEParameterSpec
import java.nio.ByteBuffer
import java.security.MessageDigest

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class StandardMacAlgorithmsTest {

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
        assertEquals 'HmacMD5', Algs.Mac.HMD5.id()
        assertEquals 'HmacSHA1', Algs.Mac.HS1.id()
        assertEquals 'HmacSHA224', Algs.Mac.HS224.id()
        assertEquals 'HmacSHA256', Algs.Mac.HS256.id()
        assertEquals 'HmacSHA384', Algs.Mac.HS384.id()
        assertEquals 'HmacSHA512', Algs.Mac.HS512.id()
        assertEquals 'HmacSHA512/224', Algs.Mac.HS512_224.id()
        assertEquals 'HmacSHA512/256', Algs.Mac.HS512_256.id()
        assertEquals 'HmacSHA3-224', Algs.Mac.HS3_224.id()
        assertEquals 'HmacSHA3-256', Algs.Mac.HS3_256.id()
        assertEquals 'HmacSHA3-384', Algs.Mac.HS3_384.id()
        assertEquals 'HmacSHA3-512', Algs.Mac.HS3_512.id()
        assertEquals 'PBEWithHmacSHA1', Algs.Mac.PBEHS1.id()
        assertEquals 'PBEWithHmacSHA224', Algs.Mac.PBEHS224.id()
        assertEquals 'PBEWithHmacSHA256', Algs.Mac.PBEHS256.id()
        assertEquals 'PBEWithHmacSHA384', Algs.Mac.PBEHS384.id()
        assertEquals 'PBEWithHmacSHA512', Algs.Mac.PBEHS512.id()
        assertEquals 'PBEWithHmacSHA512/224', Algs.Mac.PBEHS512_224.id()
        assertEquals 'PBEWithHmacSHA512/256', Algs.Mac.PBEHS512_256.id()
        assertEquals 'HmacPBESHA1', Algs.Mac.PKCS12HS1.id()
        assertEquals 'HmacPBESHA224', Algs.Mac.PKCS12HS224.id()
        assertEquals 'HmacPBESHA256', Algs.Mac.PKCS12HS256.id()
        assertEquals 'HmacPBESHA384', Algs.Mac.PKCS12HS384.id()
        assertEquals 'HmacPBESHA512', Algs.Mac.PKCS12HS512.id()
        assertEquals 'HmacPBESHA512/224', Algs.Mac.PKCS12HS512_224.id()
        assertEquals 'HmacPBESHA512/256', Algs.Mac.PKCS12HS512_256.id()
    }

    static def roundtrip(MacAlgorithm alg, def data) {

        // wrap in a list if we need to for .apply call iteration:
        if (data && !(data instanceof List)) data = [data]

        def key = alg.keygen().get() as SecretKey

        def salt = Bytes.randomBits(alg.digestSize().bits())
        def iterations = DefaultPassword.MIN_ITERATIONS // keep password-based Mac tests fast

        // Digest data using our API:
        def hb = alg.digester().key(key)
        if (alg instanceof PasswordMacAlgorithm) hb.salt(salt).iterations(iterations)
        Hasher hasher = hb.get()
        if (data) data.each { hasher.apply(it); if (it instanceof ByteBuffer) it.rewind() }
        def digest = hasher.get()

        // Digest the same data using the raw JCA API directly:
        def jca = Mac.getInstance(alg.id() as String)
        jca.getMacLength() // force JCA provider loaded
        if (key instanceof Password) {
            def sk = DefaultPassword.toJcaKey(key.chars())
            jca.init(sk, new PBEParameterSpec(salt, iterations))
        } else {
            jca.init(key.toJcaKey())
        }
        if (data) data.each { jca.update(it); if (it instanceof ByteBuffer) it.rewind() }
        byte[] jcaDigest = jca.doFinal()

        // Assert that our Digest result is identical to the JCA output, and our verify implementation does the same:
        assertTrue MessageDigest.isEqual(jcaDigest, digest)
        assertEquals alg.digestSize().bits(), Bytes.bitLength(digest)
        hb = alg.verifier().key(key)
        if (alg instanceof PasswordMacAlgorithm) hb.salt(salt).iterations(iterations)
        hasher = hb.get()
        if (data) data.each { hasher.apply(it); if (it instanceof ByteBuffer) it.rewind() }
        assertTrue hasher.test(digest)
    }

    @Test
    void digestNoData() {
        Algs.Mac.get().values().each { roundtrip(it, null) }
    }

    @Test
    void digestOneByte() {
        def b = Bytes.random(1)[0]
        Algs.Mac.get().values().each { roundtrip(it, b) }
    }

    @Test
    void digestByteBuffer() {
        def buf = ByteBuffer.wrap(Bytes.random(16))
        Algs.Mac.get().values().each { roundtrip(it, buf) }
    }

    @Test
    void digestExactLengths() {
        Algs.Mac.get().values().each {
            byte[] data = Bytes.randomBits(it.digestSize().bits())
            roundtrip(it, data)
        }
    }

    @Test
    void digestSmallerLengths() {
        Algs.Mac.get().values().each {
            byte[] data = Bytes.randomBits(it.digestSize().bits() - Byte.SIZE) // 1 byte less than digest length
            roundtrip(it, data)
        }
    }

    @Test
    void digestLargerLengths() {
        Algs.Mac.get().values().each {
            def bits = it.digestSize().bits()
            def a = Bytes.randomBits(bits)
            def b = Bytes.randomBits(bits)
            def c = Bytes.randomBits(bits)
            roundtrip(it, [a, b, c]) // more bytes than digest length
        }
    }
}
