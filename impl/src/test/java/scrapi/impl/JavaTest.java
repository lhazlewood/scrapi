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

// various tests using the direct Java (not Groovy) APIs.
public class JavaTest {

//    @Test
//    void testing() throws Exception {
//        byte[] salt = Bytes.randomBits(64);
//        char[] password = "correct horse battery staple".toCharArray();
//        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, 20);
//        PBEKeySpec keySpec = new PBEKeySpec(password, salt, 20, 256);
//        SecretKeyFactory f = SecretKeyFactory.getInstance("PBEWithHmacSHA256AndAES_128");
//        SecretKey key = f.generateSecret(keySpec);
//        Mac mac = Mac.getInstance("HmacPBESHA256");
//        mac.getMacLength(); // force SPI lookup
//        mac.init(key);
//        mac.update(salt);
//        byte[] digest = mac.doFinal();
//        System.out.println(key);
//        System.out.println("Digest: " + Strings.toHex(digest));
//    }

//    @Test
//    void sigNoData() throws Exception {
//        Algs.Sig.get().values().stream().filter(s -> s instanceof RsaSignatureAlgorithm).map(s -> (RsaSignatureAlgorithm) s).forEach(rsa -> {
//            RsaPrivateKey priv = rsa.key().size(1024).build(); // keep build times short
//            byte[] sig = rsa.key(priv).get();
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
//                assertTrue(rsa.key(priv.publicKey()).test(sig));
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
//                    RsaPrivateKey priv = rsa.key().size(1024).build(); // keep build times short
//                    byte[] sig = rsa.key(priv).get();
//                    assertEquals(Bytes.bitLength(sig), (long) priv.bitLength().get()); // RSA signature length is equal to the modulus length
//                    assert rsa.key(priv.publicKey()).test(sig);
//                });
//    }
}
