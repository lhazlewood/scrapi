package scrapi.impl.msg

import org.junit.jupiter.api.Test
import scrapi.impl.key.DefaultPassword
import scrapi.key.Password
import scrapi.key.SymmetricKey
import scrapi.msg.Hasher
import scrapi.msg.MacAlgorithm
import scrapi.util.Bytes

import javax.crypto.Mac
import javax.crypto.spec.PBEParameterSpec
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.function.Consumer

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

abstract class AbstractMacAlgorithmTest<T extends MacAlgorithm> {

    protected abstract Collection<T> algs();

    static def roundtrip(MacAlgorithm alg, def data) {

        // wrap in a list if we need to for .apply call iteration:
        if (data && !(data instanceof List)) data = [data]

        def key = alg.keygen().get() as SymmetricKey

        def salt = Bytes.randomBits(alg.size().bits())
        def iterations = DefaultPassword.MIN_ITERATIONS // keep password-based Mac tests fast

        Consumer configurer = { params ->
            params.key(key)
            if (key instanceof Password) {
                params.salt(salt).cost(iterations)
            }
        }
        // Digest data using our API:
        def hasher = alg.with(configurer) as Hasher
        if (data) data.each { hasher.apply(it); if (it instanceof ByteBuffer) it.rewind() }
        def digest = hasher.get().octets()

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
        assertEquals alg.size().bits(), Bytes.bitLength(digest)
        hasher = alg.verifier(configurer)
        if (data) data.each { hasher.apply(it); if (it instanceof ByteBuffer) it.rewind() }
        assertTrue hasher.test(digest)
    }

    @Test
    void digestNoData() {
        algs().each { roundtrip(it, null) }
    }

    @Test
    void digestOneByte() {
        def b = Bytes.random(1)[0]
        algs().each { roundtrip(it, b) }
    }

    @Test
    void digestByteBuffer() {
        def buf = ByteBuffer.wrap(Bytes.random(16))
        algs().each { roundtrip(it, buf) }
    }

    @Test
    void digestExactLengths() {
        algs().each {
            byte[] data = Bytes.randomBits(it.size().bits())
            roundtrip(it, data)
        }
    }

    @Test
    void digestSmallerLengths() {
        algs().each {
            byte[] data = Bytes.randomBits(it.size().bits() - Byte.SIZE) // 1 byte less than digest length
            roundtrip(it, data)
        }
    }

    @Test
    void digestLargerLengths() {
        algs().each {
            def bits = it.size().bits()
            def a = Bytes.randomBits(bits)
            def b = Bytes.randomBits(bits)
            def c = Bytes.randomBits(bits)
            roundtrip(it, [a, b, c]) // more bytes than digest length
        }
    }
}
