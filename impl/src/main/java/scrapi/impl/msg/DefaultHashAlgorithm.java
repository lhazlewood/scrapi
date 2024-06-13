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

import scrapi.msg.HashAlgorithm;
import scrapi.msg.Hasher;

import java.security.Provider;

class DefaultHashAlgorithm extends AbstractIntegrityAlgorithm<HashAlgorithm> implements HashAlgorithm {

    DefaultHashAlgorithm(String id, int bitLength) {
        this(id, null, bitLength);
    }

    private DefaultHashAlgorithm(String id, Provider provider, int bitLength) {
        super(id, provider, bitLength);
    }

    @Override
    public HashAlgorithm provider(Provider provider) {
        return new DefaultHashAlgorithm(this.ID, provider, this.BITLEN);
    }

    @Override
    public Hasher get() {
        return new JcaMessageDigester(this.ID, this.PROVIDER);
    }
}
