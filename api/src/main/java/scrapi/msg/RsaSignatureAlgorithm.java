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

import scrapi.alg.Randomizable;
import scrapi.key.KeyGenerator;
import scrapi.key.Keyable;
import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;
import scrapi.lang.Registry;

public interface RsaSignatureAlgorithm<
        SP extends Keyable<RsaPrivateKey, SP> & Randomizable<SP>,
        VP extends Keyable<RsaPublicKey, VP>,
        G extends KeyGenerator<RsaPrivateKey, G>>
        extends UnarySignatureAlgorithm<RsaPrivateKey, RsaPublicKey, SP, VP, G, RsaSignatureAlgorithm<SP, VP, G>> {

    /**
     * Returns a registry of all
     * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#signature-algorithms">Java
     * Standard RSA Signature Algorithms</a>.
     *
     * @return a registry of all
     * * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#signature-algorithms">Java
     * * Standard RSA Signature Algorithms</a>.
     */
    static Registry<String, RsaSignatureAlgorithm<?, ?, ?>> registry() {
        return RsaSigAlgs.REGISTRY;
    }

    RsaSignatureAlgorithm<?, ?, ?> RS1 = registry().forKey("SHA1withRSA");
    RsaSignatureAlgorithm<?, ?, ?> RS224 = registry().forKey("SHA224withRSA");
    RsaSignatureAlgorithm<?, ?, ?> RS256 = registry().forKey("SHA256withRSA");
    RsaSignatureAlgorithm<?, ?, ?> RS384 = registry().forKey("SHA384withRSA");
    RsaSignatureAlgorithm<?, ?, ?> RS512 = registry().forKey("SHA512withRSA");
    RsaSignatureAlgorithm<?, ?, ?> RS512_224 = registry().forKey("SHA512/224withRSA");
    RsaSignatureAlgorithm<?, ?, ?> RS512_256 = registry().forKey("SHA512/256withRSA");
    RsaSignatureAlgorithm<?, ?, ?> RS3_224 = registry().forKey("SHA3-224withRSA");
    RsaSignatureAlgorithm<?, ?, ?> RS3_256 = registry().forKey("SHA3-256withRSA");
    RsaSignatureAlgorithm<?, ?, ?> RS3_384 = registry().forKey("SHA3-384withRSA");
    RsaSignatureAlgorithm<?, ?, ?> RS3_512 = registry().forKey("SHA3-512withRSA");
}
