/*
 * Copyright © 2025 Les Hazlewood
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
package scrapi.msg;

import scrapi.key.PasswordStretcher;
import scrapi.lang.Registry;

public interface PbeMacAlgorithm extends PasswordMacAlgorithm<PbeMacAlgorithm.Params, PasswordDigest<PbeMacAlgorithm>, PbeMacAlgorithm> {

    interface Params extends PasswordStretcher<Params> {
    }

    /**
     * Returns a registry of all
     * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
     * Standard HMAC Message Digest Algorithms</a>.
     *
     * @return a registry of all
     * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
     * Standard HMAC Message Digest Algorithms</a>.
     */
    static Registry<String, PbeMacAlgorithm> registry() {
        return PbeMacAlgs.REGISTRY;
    }

    PbeMacAlgorithm PBEHS1 = registry().forKey("PBEWithHmacSHA1");
    PbeMacAlgorithm PBEHS224 = registry().forKey("PBEWithHmacSHA224");
    PbeMacAlgorithm PBEHS256 = registry().forKey("PBEWithHmacSHA256");
    PbeMacAlgorithm PBEHS384 = registry().forKey("PBEWithHmacSHA384");
    PbeMacAlgorithm PBEHS512 = registry().forKey("PBEWithHmacSHA512");
    PbeMacAlgorithm PBEHS512_224 = registry().forKey("PBEWithHmacSHA512/224");
    PbeMacAlgorithm PBEHS512_256 = registry().forKey("PBEWithHmacSHA512/256");

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

//        @SuppressWarnings("DeprecatedIsStillUsed")
//        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
//        public static final PasswordMacAlgorithm<?, ?, ?> PKCS12HS1 = Algs.get(get(), "HmacPBESHA1");
//
//        @SuppressWarnings("DeprecatedIsStillUsed")
//        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
//        public static final PasswordMacAlgorithm<?, ?, ?> PKCS12HS224 = Algs.get(get(), "HmacPBESHA224");
//
//        @SuppressWarnings("DeprecatedIsStillUsed")
//        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
//        public static final PasswordMacAlgorithm<?, ?, ?> PKCS12HS256 = Algs.get(get(), "HmacPBESHA256");
//
//        @SuppressWarnings("DeprecatedIsStillUsed")
//        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
//        public static final PasswordMacAlgorithm<?, ?, ?> PKCS12HS384 = Algs.get(get(), "HmacPBESHA384");
//
//        @SuppressWarnings("DeprecatedIsStillUsed")
//        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
//        public static final PasswordMacAlgorithm<?, ?, ?> PKCS12HS512 = Algs.get(get(), "HmacPBESHA512");
//
//        @SuppressWarnings("DeprecatedIsStillUsed")
//        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
//        public static final PasswordMacAlgorithm<?, ?, ?> PKCS12HS512_224 = Algs.get(get(), "HmacPBESHA512/224");
//
//        @SuppressWarnings("DeprecatedIsStillUsed")
//        @Deprecated // per https://datatracker.ietf.org/doc/html/rfc7292#appendix-B
//        public static final PasswordMacAlgorithm<?, ?, ?> PKCS12HS512_256 = Algs.get(get(), "HmacPBESHA512/256");
}
