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

import scrapi.key.OctetSecretKey;
import scrapi.util.Bytes;

import java.util.Optional;

public class DefaultOctetSecretKey extends AbstractKey<javax.crypto.SecretKey> implements OctetSecretKey {

    public DefaultOctetSecretKey(javax.crypto.SecretKey key) {
        super(key);
    }

    @Override
    public Optional<byte[]> octets() {
        return Optional.ofNullable(AbstractKey.findEncoded(this.key));
    }

    @Override
    public Optional<Integer> bitLength() {
        return octets().map(b -> (int) Bytes.bitLength(b));
    }
}
