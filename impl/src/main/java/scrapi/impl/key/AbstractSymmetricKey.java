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

import javax.security.auth.DestroyFailedException;

abstract class AbstractSymmetricKey<K extends javax.crypto.SecretKey> extends AbstractKey<K> implements scrapi.key.Key<K>, scrapi.Destroyable {

    public AbstractSymmetricKey(K key) {
        super(key);
    }

    public AbstractSymmetricKey(K key, int bitLength) {
        super(key, bitLength);
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
