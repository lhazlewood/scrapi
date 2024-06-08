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
import scrapi.digest.RsaSignatureAlgorithm;
import scrapi.digest.SignatureAlgorithm;
import scrapi.key.OctetSecretKey;
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

        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HMD5 = Algs.get(get(), "HmacMD5");
        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HS1 = Algs.get(get(), "HmacSHA1");
        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HS224 = Algs.get(get(), "HmacSHA224");
        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HS256 = Algs.get(get(), "HmacSHA256");
        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HS384 = Algs.get(get(), "HmacSHA384");
        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HS512 = Algs.get(get(), "HmacSHA512");
        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HS512_224 = Algs.get(get(), "HmacSHA512/224");
        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HS512_256 = Algs.get(get(), "HmacSHA512/256");
        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HS3_224 = Algs.get(get(), "HmacSHA3-224");
        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HS3_256 = Algs.get(get(), "HmacSHA3-256");
        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HS3_384 = Algs.get(get(), "HmacSHA3-384");
        public static final MacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> HS3_512 = Algs.get(get(), "HmacSHA3-512");

        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PBEHS1 = Algs.get(get(), "PBEWithHmacSHA1");
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PBEHS224 = Algs.get(get(), "PBEWithHmacSHA224");
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PBEHS256 = Algs.get(get(), "PBEWithHmacSHA256");
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PBEHS384 = Algs.get(get(), "PBEWithHmacSHA384");
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PBEHS512 = Algs.get(get(), "PBEWithHmacSHA512");
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PBEHS512_224 = Algs.get(get(), "PBEWithHmacSHA512/224");
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PBEHS512_256 = Algs.get(get(), "PBEWithHmacSHA512/256");

        /*
         * Per <a href="https://datatracker.ietf.org/doc/html/rfc7292#appendix-B">RFC 7292, Appendix B</a>, the
         * following HmacPBE* algorithms are deprecated and should not be used in new code:
         * <blockquote><pre>
         *    Note that this method for password privacy mode is not recommended
         *    and is deprecated for new usage.  The procedures and algorithms
         *    defined in PKCS #5 v2.1 should be used instead.
         *    Specifically, PBES2 should be used as encryption scheme, with PBKDF2
         *    as the key derivation function.
         *
         *    The method presented here is still used to generate the key in
         *    password integrity mode.
         * </pre></blockquote>
         */

        @SuppressWarnings("DeprecatedIsStillUsed")
        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PKCS12HS1 = Algs.get(get(), "HmacPBESHA1");

        @SuppressWarnings("DeprecatedIsStillUsed")
        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PKCS12HS224 = Algs.get(get(), "HmacPBESHA224");

        @SuppressWarnings("DeprecatedIsStillUsed")
        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PKCS12HS256 = Algs.get(get(), "HmacPBESHA256");

        @SuppressWarnings("DeprecatedIsStillUsed")
        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PKCS12HS384 = Algs.get(get(), "HmacPBESHA384");

        @SuppressWarnings("DeprecatedIsStillUsed")
        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PKCS12HS512 = Algs.get(get(), "HmacPBESHA512");

        @SuppressWarnings("DeprecatedIsStillUsed")
        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PKCS12HS512_224 = Algs.get(get(), "HmacPBESHA512/224");

        @SuppressWarnings("DeprecatedIsStillUsed")
        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
        public static final MacAlgorithm<PbeKey, PbeKey.Generator> PKCS12HS512_256 = Algs.get(get(), "HmacPBESHA512/256");
    }

    public static final class Sig {

        //prevent instantiation
        private Sig() {
        }

        private static final String IMPL_CLASSNAME = "scrapi.impl.digest.StandardSignatureAlgorithms";
        private static final Registry<String, SignatureAlgorithm<?, ?, ?>> REGISTRY = Classes.newInstance(IMPL_CLASSNAME);

        /**
         * Returns a registry of all
         * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
         * Standard Hash (aka Message Digest) Algorithms</a>.
         *
         * @return a registry of all
         * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
         * Standard Hash (aka Message Digest) Algorithms</a>.
         */
        public static Registry<String, SignatureAlgorithm<?, ?, ?>> get() {
            return REGISTRY;
        }

        public static final RsaSignatureAlgorithm RS1 = Algs.get(get(), "SHA1withRSA");
        public static final RsaSignatureAlgorithm RS224 = Algs.get(get(), "SHA224withRSA");
        public static final RsaSignatureAlgorithm RS256 = Algs.get(get(), "SHA256withRSA");
        public static final RsaSignatureAlgorithm RS384 = Algs.get(get(), "SHA384withRSA");
        public static final RsaSignatureAlgorithm RS512 = Algs.get(get(), "SHA512withRSA");
        public static final RsaSignatureAlgorithm RS512_224 = Algs.get(get(), "SHA512/224withRSA");
        public static final RsaSignatureAlgorithm RS512_256 = Algs.get(get(), "SHA512/256withRSA");
        public static final RsaSignatureAlgorithm RS3_224 = Algs.get(get(), "SHA3-224withRSA");
        public static final RsaSignatureAlgorithm RS3_256 = Algs.get(get(), "SHA3-256withRSA");
        public static final RsaSignatureAlgorithm RS3_384 = Algs.get(get(), "SHA3-384withRSA");
        public static final RsaSignatureAlgorithm RS3_512 = Algs.get(get(), "SHA3-512withRSA");

    }
}
