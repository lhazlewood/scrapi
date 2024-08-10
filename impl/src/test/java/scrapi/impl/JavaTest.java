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
package scrapi.impl;

import scrapi.alg.Algs;
import scrapi.key.RsaPublicKey;

// various tests using the direct Java (not Groovy) APIs.
public class JavaTest {

    void test() {
        byte[] input = null;
        Algs.Mac.HMD5.key(null).apply(input).get();
        Algs.Mac.HMD5.creator(c -> c.key(null)).apply(new byte[21]).get();
        Algs.Mac.PBEHS1.creator(c -> c.key(null).salt(null)).apply(new byte[21]).get();
        Algs.Mac.PBEHS1.creator().key(null).salt(null).get().apply(new byte[21]).get();
        Algs.Sig.RS256.verifier(c -> c.key(null)).apply((byte) 'f').test(null);
        Algs.Sig.RS256.key((RsaPublicKey) null).apply((byte) 'f').test(null);
    }

//    void testWhatever() {
//        byte[] digestA = Algs.PBEMAC
//                .with().password(pswd).salt(saltBytes).iterations(iterations).build()
//                .apply(chunk1)/* ... */.apply(chunkN).get();
//
//        byte[] digestB = Algs.PBEMAC
//                .with(p -> p.password(pswd).salt(saltBytes).iterations(iterations))
//                .apply(chunk1)/* ... */.apply(chunkN).get();
//    }

//    @Test
//    void testing() throws Exception {
//        byte[] salt = Bytes.randomBits(64);
//        char[] password = "correct horse battery staple".toCharArray();
//        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 20);
//        PBEKeySpec keySpec = new PBEKeySpec(password, salt, 20, 256);
//        SecretKeyFactory f = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
//        SecretKey with = f.generateSecret(keySpec);
//        Mac mac = Mac.getInstance("HmacPBESHA256");
//        mac.getMacLength(); // force SPI lookup
//        mac.init(with);
//        mac.update(salt);
//        byte[] digest = mac.doFinal();
//        System.out.println(with);
//        System.out.println("Digest: " + Strings.toHex(digest));
//    }

//    @Test
//    void sigNoData() throws Exception {
//        Algs.Sig.get().values().stream().filter(s -> s instanceof RsaSignatureAlgorithm).map(s -> (RsaSignatureAlgorithm) s).forEach(rsa -> {
//            RsaPrivateKey priv = rsa.with().size(1024).build(); // keep build times short
//            byte[] sig = rsa.with(priv).get();
//            byte[] jcaSig;
//            Signature jca;
//            try {
//                jca = Signature.getInstance(rsa.id());
//                jca.initSign(priv.toJcaKey());
//                jcaSig = jca.sign();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//            assertTrue(MessageDigest.isEqual(jcaSig, sig));
//            assertEquals(Bytes.bitLength(sig), (long) priv.bitLength().get()); // RSA signature length is equal to the modulus length
//            try {
//                jca.initVerify(priv.publicKey().toJcaKey());
//                assertTrue(jca.verify(jcaSig));
//                assertTrue(rsa.with(priv.publicKey()).test(sig));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//    }
//
//    @Test
//    void sigNoData2() {
//        Algs.Sig.get().values().stream().filter(s -> s instanceof RsaSignatureAlgorithm)
//                .map(s -> (RsaSignatureAlgorithm) s).forEach(rsa -> {
//                    RsaPrivateKey priv = rsa.with().size(1024).build(); // keep build times short
//                    byte[] sig = rsa.with(priv).get();
//                    assertEquals(Bytes.bitLength(sig), (long) priv.bitLength().get()); // RSA signature length is equal to the modulus length
//                    assert rsa.with(priv.publicKey()).test(sig);
//                });
//    }
}
