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

import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;
import scrapi.lang.Registry;
import scrapi.msg.SignatureAlgorithm;
import scrapi.msg.UnarySignatureAlgorithm;
import scrapi.util.Classes;

public final class Algs {

    /**
     * Private constructor, prevent instantiation.
     */
    private Algs() {
    }

    // do not change this visibility.  Raw type method signature not be publicly exposed:
    @SuppressWarnings("unchecked")
    private static <T> T get(Registry<String, ?> registry, String id) {
        return (T) registry.forKey(id);
    }

    public static final class Sig {

        //prevent instantiation
        private Sig() {
        }

        private static final String IMPL_CLASSNAME = "scrapi.impl.msg.StandardSignatureAlgorithms";
        private static final Registry<String, ? extends SignatureAlgorithm<?, ?, ?, ?, ?, ?>> REGISTRY = Classes.newInstance(IMPL_CLASSNAME);

        /**
         * Returns a registry of all
         * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
         * Standard Hash (aka Message Digest) Algorithms</a>.
         *
         * @return a registry of all
         * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
         * Standard Hash (aka Message Digest) Algorithms</a>.
         */
        public static Registry<String, ? extends SignatureAlgorithm<?, ?, ?, ?, ?, ?>> get() {
            return REGISTRY;
        }

        public static final UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, ?, ?, ?, ?> RS1 = Algs.get(get(), "SHA1withRSA");
        public static final UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, ?, ?, ?, ?> RS224 = Algs.get(get(), "SHA224withRSA");
        public static final UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, ?, ?, ?, ?> RS256 = Algs.get(get(), "SHA256withRSA");
        public static final UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, ?, ?, ?, ?> RS384 = Algs.get(get(), "SHA384withRSA");
        public static final UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, ?, ?, ?, ?> RS512 = Algs.get(get(), "SHA512withRSA");
        public static final UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, ?, ?, ?, ?> RS512_224 = Algs.get(get(), "SHA512/224withRSA");
        public static final UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, ?, ?, ?, ?> RS512_256 = Algs.get(get(), "SHA512/256withRSA");
        public static final UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, ?, ?, ?, ?> RS3_224 = Algs.get(get(), "SHA3-224withRSA");
        public static final UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, ?, ?, ?, ?> RS3_256 = Algs.get(get(), "SHA3-256withRSA");
        public static final UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, ?, ?, ?, ?> RS3_384 = Algs.get(get(), "SHA3-384withRSA");
        public static final UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, ?, ?, ?, ?> RS3_512 = Algs.get(get(), "SHA3-512withRSA");

    }
}
