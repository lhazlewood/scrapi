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

import scrapi.impl.key.DefaultPassword;
import scrapi.msg.PasswordDigest;
import scrapi.msg.PasswordMacAlgorithm;
import scrapi.util.Assert;
import scrapi.util.Objects;

import java.security.MessageDigest;

class DefaultPasswordDigest<A extends PasswordMacAlgorithm<?, ?, ?>> extends DefaultDigest<A> implements PasswordDigest<A> {

    private final byte[] salt;
    private final int cost;

    DefaultPasswordDigest(A algorithm, byte[] octets, byte[] salt, int cost) {
        super(algorithm, octets);
        this.salt = Assert.notNull(salt, "salt must not be null");
        this.cost = DefaultPassword.assertIterationsGte(cost);
    }

    @Override
    public byte[] salt() {
        return this.salt.clone();
    }

    @Override
    public int cost() {
        return this.cost;
    }

    @Override
    public int hashCode() {
        return Objects.nullSafeHashCode(this.algorithm, this.octets, this.salt, this.cost);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        return obj instanceof PasswordDigest<?> pd && super.equals(pd) &&
                this.cost == pd.cost() && MessageDigest.isEqual(this.salt, pd.salt());
    }
}
