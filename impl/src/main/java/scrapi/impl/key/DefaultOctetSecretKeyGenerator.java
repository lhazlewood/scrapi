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
package scrapi.impl.key;

import scrapi.alg.Size;
import scrapi.key.KeyGenerator;
import scrapi.key.OctetSecretKey;

import javax.crypto.SecretKey;

public class DefaultOctetSecretKeyGenerator extends AbstractKeyGenerator<OctetSecretKey, KeyGenerator.Basic<OctetSecretKey>>
        implements KeyGenerator<OctetSecretKey, KeyGenerator.Basic<OctetSecretKey>>, KeyGenerator.Basic<OctetSecretKey> {

    public DefaultOctetSecretKeyGenerator(String jcaName, Size minSize) {
        super(jcaName, minSize, minSize);
    }

    @Override
    public OctetSecretKey get() {
        Size size = resolveSize();
        SecretKey jcaKey = jca().generateSecretKey(size.bits());
        return new DefaultOctetSecretKey(jcaKey);
    }
}
