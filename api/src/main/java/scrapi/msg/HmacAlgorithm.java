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

import scrapi.key.KeyGenerator;
import scrapi.key.Keyable;
import scrapi.key.OctetSecretKey;
import scrapi.lang.Registry;

public interface HmacAlgorithm extends UnaryMacAlgorithm<
        OctetSecretKey,
        Keyable.Param<OctetSecretKey>,
        KeyGenerator.Basic<OctetSecretKey>,
        Digest<HmacAlgorithm>,
        HmacAlgorithm> {

    /**
     * Returns a registry of all
     * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
     * Standard HMAC Message Digest Algorithms</a>.
     *
     * @return a registry of all
     * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
     * Standard HMAC Message Digest Algorithms</a>.
     */
    static Registry<String, HmacAlgorithm> registry() {
        return HmacAlgs.REGISTRY;
    }

    HmacAlgorithm HMD5 = registry().forKey("HmacMD5");
    HmacAlgorithm HS1 = registry().forKey("HmacSHA1");
    HmacAlgorithm HS224 = registry().forKey("HmacSHA224");
    HmacAlgorithm HS256 = registry().forKey("HmacSHA256");
    HmacAlgorithm HS384 = registry().forKey("HmacSHA384");
    HmacAlgorithm HS512 = registry().forKey("HmacSHA512");
    HmacAlgorithm HS512_224 = registry().forKey("HmacSHA512/224");
    HmacAlgorithm HS512_256 = registry().forKey("HmacSHA512/256");
    HmacAlgorithm HS3_224 = registry().forKey("HmacSHA3-224");
    HmacAlgorithm HS3_256 = registry().forKey("HmacSHA3-256");
    HmacAlgorithm HS3_384 = registry().forKey("HmacSHA3-384");
    HmacAlgorithm HS3_512 = registry().forKey("HmacSHA3-512");
}
