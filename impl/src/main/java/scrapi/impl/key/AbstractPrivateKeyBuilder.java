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

import scrapi.key.PrivateKey;
import scrapi.key.PublicKey;
import scrapi.util.Assert;

abstract class AbstractPrivateKeyBuilder<U extends PublicKey<?>, R extends PrivateKey<?, U>, T extends PrivateKey.Builder<U, R, T>>
        extends AbstractKeyBuilder<R, T> implements PrivateKey.Builder<U, R, T> {

    protected U publicKey;

    public AbstractPrivateKeyBuilder(String jcaName, int minSize) {
        super(jcaName, minSize);
    }

    @Override
    public T publicKey(U publicKey) {
        this.publicKey = Assert.notNull(publicKey, "PublicKey cannot be null");
        // if a public key is specified, key generation doesn't make sense:
        this.size = 0;
        publicKeyChanged();
        return self();
    }

    protected void publicKeyChanged() {
        // no op, can be overridden by subclasses
    }
}
