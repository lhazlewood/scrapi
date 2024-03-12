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
package scrapi;

import scrapi.digest.HashAlgorithm;
import scrapi.digest.MacAlgorithm;
import scrapi.key.OctetKey;
import scrapi.key.PbeKey;
import scrapi.lang.Registry;
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

    public static final class Hash {

        //prevent instantiation
        private Hash() {
        }

        private static final String IMPL_CLASSNAME = "scrapi.impl.digest.StandardHashAlgorithms";
        private static final Registry<String, HashAlgorithm> REGISTRY = Classes.newInstance(IMPL_CLASSNAME);

        /**
         * Returns a registry of all
         * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
         * Standard Hash (aka Message Digest) Algorithms</a>.
         *
         * @return a registry of all
         * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
         * Standard Hash (aka Message Digest) Algorithms</a>.
         */
        public static Registry<String, HashAlgorithm> get() {
            return REGISTRY;
        }

        public static final HashAlgorithm MD2 = get().forKey("MD2");
        public static final HashAlgorithm MD5 = get().forKey("MD5");
        public static final HashAlgorithm SHA_1 = get().forKey("SHA-1");
        public static final HashAlgorithm SHA_224 = get().forKey("SHA-224");
        public static final HashAlgorithm SHA_256 = get().forKey("SHA-256");
        public static final HashAlgorithm SHA_384 = get().forKey("SHA-384");
        public static final HashAlgorithm SHA_512 = get().forKey("SHA-512");
        public static final HashAlgorithm SHA_512_224 = get().forKey("SHA-512/224");
        public static final HashAlgorithm SHA_512_256 = get().forKey("SHA-512/256");
        public static final HashAlgorithm SHA3_224 = get().forKey("SHA3-224");
        public static final HashAlgorithm SHA3_256 = get().forKey("SHA3-256");
        public static final HashAlgorithm SHA3_384 = get().forKey("SHA3-384");
        public static final HashAlgorithm SHA3_512 = get().forKey("SHA3-512");
    }

    public static final class Mac {

        //prevent instantiation
        private Mac() {
        }

        private static final String IMPL_CLASSNAME = "scrapi.impl.digest.StandardMacAlgorithms";
        private static final Registry<String, MacAlgorithm<?, ?>> REGISTRY = Classes.newInstance(IMPL_CLASSNAME);

        /**
         * Returns a registry of all
         * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#mac-algorithms">Java
         * Standard Mac Algorithms</a>.
         *
         * @return a registry of all
         * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#mac-algorithms">Java
         * Standard Mac Algorithms</a>.
         */
        public static Registry<String, MacAlgorithm<?, ?>> get() {
            return REGISTRY;
        }

        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HMD5 = Algs.get(get(), "HmacMD5");
        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HS1 = Algs.get(get(), "HmacSHA1");
        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HS224 = Algs.get(get(), "HmacSHA224");
        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HS256 = Algs.get(get(), "HmacSHA256");
        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HS384 = Algs.get(get(), "HmacSHA384");
        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HS512 = Algs.get(get(), "HmacSHA512");
        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HS512_224 = Algs.get(get(), "HmacSHA512/224");
        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HS512_256 = Algs.get(get(), "HmacSHA512/256");
        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HS3_224 = Algs.get(get(), "HmacSHA3-224");
        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HS3_256 = Algs.get(get(), "HmacSHA3-256");
        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HS3_384 = Algs.get(get(), "HmacSHA3-384");
        public static final MacAlgorithm<OctetKey, OctetKey.Builder> HS3_512 = Algs.get(get(), "HmacSHA3-512");

        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PBEHS1 = Algs.get(get(), "PBEWithHmacSHA1");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PBEHS224 = Algs.get(get(), "PBEWithHmacSHA224");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PBEHS256 = Algs.get(get(), "PBEWithHmacSHA256");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PBEHS384 = Algs.get(get(), "PBEWithHmacSHA384");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PBEHS512 = Algs.get(get(), "PBEWithHmacSHA512");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PBEHS512_224 = Algs.get(get(), "PBEWithHmacSHA512/224");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PBEHS512_256 = Algs.get(get(), "PBEWithHmacSHA512/256");

        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PKCS12HS1 = Algs.get(get(), "HmacPBESHA1");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PKCS12HS224 = Algs.get(get(), "HmacPBESHA224");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PKCS12HS256 = Algs.get(get(), "HmacPBESHA256");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PKCS12HS384 = Algs.get(get(), "HmacPBESHA384");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PKCS12HS512 = Algs.get(get(), "HmacPBESHA512");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PKCS12HS512_224 = Algs.get(get(), "HmacPBESHA512/224");
        private static final MacAlgorithm<PbeKey, PbeKey.Builder> PKCS12HS512_256 = Algs.get(get(), "HmacPBESHA512/256");

    }
}
