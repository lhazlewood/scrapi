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

import scrapi.impl.lang.IdentifiableRegistry;
import scrapi.msg.SignatureAlgorithm;
import scrapi.util.Collections;

public final class StandardSignatureAlgorithms extends IdentifiableRegistry<String, SignatureAlgorithm<?, ?, ?, ?, ?, ?, ?>> {
    public StandardSignatureAlgorithms() {
        super("Signature Algorithm", Collections.<SignatureAlgorithm<?, ?, ?, ?, ?, ?, ?>>of(
                new DefaultRsaSignatureAlgorithm("SHA1withRSA"),
                new DefaultRsaSignatureAlgorithm("SHA224withRSA"),
                new DefaultRsaSignatureAlgorithm("SHA256withRSA"),
                new DefaultRsaSignatureAlgorithm("SHA384withRSA"),
                new DefaultRsaSignatureAlgorithm("SHA512withRSA"),
                new DefaultRsaSignatureAlgorithm("SHA512/224withRSA"),
                new DefaultRsaSignatureAlgorithm("SHA512/256withRSA"),
                new DefaultRsaSignatureAlgorithm("SHA3-224withRSA"),
                new DefaultRsaSignatureAlgorithm("SHA3-256withRSA"),
                new DefaultRsaSignatureAlgorithm("SHA3-384withRSA"),
                new DefaultRsaSignatureAlgorithm("SHA3-512withRSA")
        ));
    }
}
