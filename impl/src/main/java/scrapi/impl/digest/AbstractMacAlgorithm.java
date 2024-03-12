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

import scrapi.digest.Hasher;
import scrapi.digest.MacAlgorithm;
import scrapi.key.KeyBuilder;
import scrapi.key.SecretKey;
import scrapi.util.Assert;

import java.security.Provider;

abstract class AbstractMacAlgorithm<K extends SecretKey<?>, B extends KeyBuilder<K, B>>
        extends AbstractDigestAlgorithm<MacAlgorithm<K, B>> implements MacAlgorithm<K, B> {

    protected final String KEYGEN_ALG_NAME;

    protected AbstractMacAlgorithm(String id, Provider provider, int bitLength, String keygenAlgName) {
        super(id, provider, bitLength);
        this.KEYGEN_ALG_NAME = Assert.hasText(keygenAlgName, "keygenAlgName cannot be null or empty.");
    }

    @Override
    public Hasher key(K key) {
        return new JcaMacDigester(this.ID, this.PROVIDER, key);
    }
}
