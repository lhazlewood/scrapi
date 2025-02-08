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
package scrapi.msg;

import scrapi.alg.Sized;
import scrapi.lang.Registry;

import java.util.function.Supplier;

public interface HashAlgorithm extends IntegrityAlgorithm, Sized, Supplier<Hasher<Digest<HashAlgorithm>>> {

    /**
     * Returns a registry of all
     * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
     * Standard Hash (aka Message Digest) Algorithms</a>.
     *
     * @return a registry of all
     * <a href="https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms">Java
     * Standard Hash (aka Message Digest) Algorithms</a>.
     */
    static Registry<String, HashAlgorithm> registry() {
        return HashAlgs.REGISTRY;
    }

    HashAlgorithm MD2 = registry().forKey("MD2");
    HashAlgorithm MD5 = registry().forKey("MD5");
    HashAlgorithm SHA_1 = registry().forKey("SHA-1");
    HashAlgorithm SHA_224 = registry().forKey("SHA-224");
    HashAlgorithm SHA_256 = registry().forKey("SHA-256");
    HashAlgorithm SHA_384 = registry().forKey("SHA-384");
    HashAlgorithm SHA_512 = registry().forKey("SHA-512");
    HashAlgorithm SHA_512_224 = registry().forKey("SHA-512/224");
    HashAlgorithm SHA_512_256 = registry().forKey("SHA-512/256");
    HashAlgorithm SHA3_224 = registry().forKey("SHA3-224");
    HashAlgorithm SHA3_256 = registry().forKey("SHA3-256");
    HashAlgorithm SHA3_384 = registry().forKey("SHA3-384");
    HashAlgorithm SHA3_512 = registry().forKey("SHA3-512");

}
