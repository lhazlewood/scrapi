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
package scrapi.impl.digest;

import scrapi.digest.RsaSignatureAlgorithm;
import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;

public class DefaultRsaSignatureAlgorithm
        extends DefaultSignatureAlgorithm<RsaPublicKey, RsaPrivateKey, RsaPrivateKey.Builder>
        implements RsaSignatureAlgorithm {

    public DefaultRsaSignatureAlgorithm(String id, int bitLength) {
        super(id, null, null, bitLength, () -> RsaPrivateKey.builder().bitLength(4096));
    }
}