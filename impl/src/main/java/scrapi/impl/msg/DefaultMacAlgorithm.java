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

import scrapi.alg.Size;
import scrapi.impl.key.DefaultOctetSecretKeyGenerator;
import scrapi.key.OctetSecretKey;

import java.security.Provider;

public class DefaultMacAlgorithm extends AbstractMacAlgorithm<OctetSecretKey, OctetSecretKey.Generator> {

    public DefaultMacAlgorithm(String id, Provider provider, Size digestSize) {
        super(id, provider, digestSize);
    }

    @Override
    public DefaultMacAlgorithm provider(Provider provider) {
        return new DefaultMacAlgorithm(this.ID, provider, this.DIGEST_SIZE);
    }

    @Override
    public OctetSecretKey.Generator keygen() {
        return new DefaultOctetSecretKeyGenerator(this.ID, this.DIGEST_SIZE);
    }
}
