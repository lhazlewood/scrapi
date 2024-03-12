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

import scrapi.key.KeyException;
import scrapi.key.OctetKey;

import javax.security.auth.DestroyFailedException;

public class DefaultOctetKey extends AbstractKey<javax.crypto.SecretKey> implements OctetKey {

    public DefaultOctetKey(javax.crypto.SecretKey key) {
        super(key);
    }

    @Override
    public byte[] octets() {
        return this.key.getEncoded();
    }

    @Override
    public void destroy() {
        try {
            this.key.destroy();
        } catch (DestroyFailedException e) {
            String msg = javax.crypto.SecretKey.class.getName() + " destroy() failed: " + e.getMessage();
            throw new KeyException(msg, e);
        }
    }

    @Override
    public boolean isDestroyed() {
        return this.key.isDestroyed();
    }
}