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
package scrapi.impl.msg;

import scrapi.impl.key.DefaultRsaPrivateKeyGenerator;
import scrapi.impl.lang.IdentifiableRegistry;
import scrapi.msg.HashAlgorithm;
import scrapi.msg.RsaSignatureAlgorithm;
import scrapi.util.Collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class DefaultRsaSignatureAlgorithmRegistry extends IdentifiableRegistry<String, RsaSignatureAlgorithm<?, ?, ?>> {

    private static RsaSignatureAlgorithm<?, ?, ?> rsa(HashAlgorithm hashAlg) {
        String id = hashAlg.id() + "withRSA";
        RsaSignatureAlgorithm<?, ?, ?> alg = new DefaultSignatureAlgorithm<>(id, null, DefaultRsaPrivateKeyGenerator::new);
    }

    // ------------------------------------------------------------------------------------------------
    // https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#signature-algorithms
    // ------------------------------------------------------------------------------------------------
    private static List<RsaSignatureAlgorithm<?, ?, ?>> createAlgs() {
        Collection<HashAlgorithm> hashAlgs = HashAlgorithm.registry().values();
        List<RsaSignatureAlgorithm<?, ?, ?>> algs = new ArrayList<>(hashAlgs.size() - 1); // no MD2, see below
        for (HashAlgorithm hashAlg : hashAlgs) {
            RsaSignatureAlgorithm<?, ?, ?> alg = rsa(hashAlg);
            algs.add(alg);
        }
        return Collections.immutable(algs);
    }

    public DefaultRsaSignatureAlgorithmRegistry() {
        super("RSA Signature Algorithm", createAlgs());
    }


//    public DefaultRsaSignatureAlgorithmRegistry() {
//        super("RSA Signature Algorithm", Collections.<SignatureAlgorithm<?, ?, ?, ?, ?, ?>>of(
//                rsa("SHA1withRSA"),
//                rsa("SHA224withRSA"),
//                rsa("SHA256withRSA"),
//                rsa("SHA384withRSA"),
//                rsa("SHA512withRSA"),
//                rsa("SHA512/224withRSA"),
//                rsa("SHA512/256withRSA"),
//                rsa("SHA3-224withRSA"),
//                rsa("SHA3-256withRSA"),
//                rsa("SHA3-384withRSA"),
//                rsa("SHA3-512withRSA")
//        ));
//    }
}
