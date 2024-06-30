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
import scrapi.alg.Size
import scrapi.key.RsaPrivateKey
import scrapi.msg.SignatureAlgorithm
import scrapi.util.Bytes

import java.security.MessageDigest
import java.security.Signature

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class StandardSignatureAlgorithmsTest {

    static void assertId(String algId, SignatureAlgorithm alg) {
        assertEquals algId, alg.id()
    }

    @Test
    void equality() {
        assertEquals Algs.Sig.get(), new StandardSignatureAlgorithms()
    }

    @Test
    void count() {
        assertEquals 11, Algs.Sig.get().size()
    }

    @SuppressWarnings('GrDeprecatedAPIUsage')
    @Test
    void instances() {
        assertId 'SHA1withRSA', Algs.Sig.RS1
        assertId 'SHA224withRSA', Algs.Sig.RS224
        assertId 'SHA256withRSA', Algs.Sig.RS256
        assertId 'SHA384withRSA', Algs.Sig.RS384
        assertId 'SHA512withRSA', Algs.Sig.RS512
        assertId 'SHA512/224withRSA', Algs.Sig.RS512_224
        assertId 'SHA512/256withRSA', Algs.Sig.RS512_256
        assertId 'SHA3-224withRSA', Algs.Sig.RS3_224
        assertId 'SHA3-256withRSA', Algs.Sig.RS3_256
        assertId 'SHA3-384withRSA', Algs.Sig.RS3_384
        assertId 'SHA3-512withRSA', Algs.Sig.RS3_512
    }

    @Test
    void digestNoData() {
        Algs.Sig.get().values().each { SignatureAlgorithm alg ->
            def size = Size.bits(2048) // keep build times short
            def priv = alg.keygen().size(size).get() as RsaPrivateKey
            byte[] sig = alg.digester().key(priv).get().get()
            def jca = Signature.getInstance(alg.id() as String)
            jca.initSign(priv.toJcaKey())
            byte[] jcaSig = jca.sign()
            assertTrue MessageDigest.isEqual(jcaSig, sig)
            assertEquals Bytes.bitLength(sig), priv.size().get().bits() // RSA signature length is equal to the modulus length
            jca = Signature.getInstance(alg.id() as String)
            jca.initVerify(priv.publicKey().toJcaKey())
            assertTrue jca.verify(jcaSig)
            assertTrue alg.verifier().key(priv.publicKey()).get().test(sig)
        }
    }

    @Test
    void digestOneByte() {
        Algs.Sig.get().values().each { SignatureAlgorithm alg ->
            def size = Size.bits(2048) // keep build times short
            def priv = alg.keygen().size(size).get() as RsaPrivateKey

            byte b = Bytes.random(1)[0]

            byte[] sig = alg.digester().key(priv).get().apply(b).get()
            def jca = Signature.getInstance(alg.id() as String)
            jca.initSign(priv.toJcaKey())
            jca.update(b)
            byte[] jcaSig = jca.sign()
            assertTrue MessageDigest.isEqual(jcaSig, sig)

            assertEquals Bytes.bitLength(sig), priv.size().get().bits() // RSA signature length is equal to the modulus length

            jca = Signature.getInstance(alg.id() as String)
            jca.initVerify(priv.publicKey().toJcaKey())
            jca.update(b)
            assertTrue jca.verify(jcaSig)
            assertTrue alg.verifier().key(priv.publicKey()).get().apply(b).test(sig)
        }
    }
//
//    @Test
//    void digestByteBuffer() {
//        Algs.Mac.get().values().each {
//            def key = newKey(it)
//            def buf = ByteBuffer.wrap(Bytes.random(16))
//            byte[] digest = it.key(key).apply(buf).get()
//
//            buf.rewind() // to use in jca Mac calculation:
//            def jca = Mac.getInstance(it.id())
//            jca.init(key.toJcaKey())
//            jca.update(buf)
//            def jcaDigest = jca.doFinal()
//
//            assertTrue MessageDigest.isEqual(jcaDigest, digest) // assert same result as JCA
//            assertEquals it.bitLength(), Bytes.bitLength(digest)
//        }
//    }
//
//
//    @Test
//    void digestExactLengths() {
//        for (MacAlgorithm alg : Algs.Mac.get().values()) {
//            def key = newKey(alg)
//            byte[] data = Bytes.randomBits(alg.bitLength())
//            byte[] digest = alg.key(key).apply(data).get()
//            assertEquals alg.bitLength(), Bytes.bitLength(digest)
//        }
//    }
//
//    @Test
//    void digestSmallerLengths() {
//        for (MacAlgorithm alg : Algs.Mac.get().values()) {
//            def key = newKey(alg)
//            byte[] data = Bytes.randomBits(alg.bitLength() - Byte.SIZE) // 1 byte less than digest length
//            byte[] digest = alg.key(key).apply(data).get()
//            assertEquals alg.bitLength(), Bytes.bitLength(digest) // digest is still same as alg bitLength
//        }
//    }
//
//    @Test
//    void digestLargerLengths() {
//        for (MacAlgorithm alg : Algs.Mac.get().values()) {
//            def key = newKey(alg)
//            def hasher = alg.key(key)
//            // multiple .apply calls, total bytes applied are larger than bitLength:
//            3.times { hasher.apply(Bytes.randomBits(alg.bitLength())) }
//            byte[] digest = hasher.get()
//            assertEquals alg.bitLength(), Bytes.bitLength(digest) // digest is still same as alg bitLength
//        }
//    }
}
