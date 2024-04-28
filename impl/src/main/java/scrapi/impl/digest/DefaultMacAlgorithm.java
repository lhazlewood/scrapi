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

import scrapi.digest.MacAlgorithm;
import scrapi.impl.key.DefaultOctetKeyBuilder;
import scrapi.key.OctetSecretKey;

import java.security.Provider;

public class DefaultMacAlgorithm
        extends AbstractMacAlgorithm<OctetSecretKey, OctetSecretKey.Builder>
        implements MacAlgorithm<OctetSecretKey, OctetSecretKey.Builder> {

    public DefaultMacAlgorithm(String id, Provider provider, int bitLength, String keygenAlgName) {
        super(id, provider, bitLength, keygenAlgName);
    }

    @Override
    public DefaultMacAlgorithm provider(Provider provider) {
        return new DefaultMacAlgorithm(this.ID, this.PROVIDER, this.BITLEN, this.KEYGEN_ALG_NAME);
    }

    @Override
    public OctetSecretKey.Builder key() {
        return new DefaultOctetKeyBuilder(this.KEYGEN_ALG_NAME, this.BITLEN);
    }
}
