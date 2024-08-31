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

import javax.crypto.Mac;

public class DefaultPasswordMacHasher<A extends PasswordMacAlgorithm<?, PasswordDigest<A>, ?>>
        extends AbstractMacHasher<PasswordDigest<A>, A> {

    private final byte[] salt;
    private final int cost;

    DefaultPasswordMacHasher(A alg, Mac mac, byte[] salt, int cost) {
        super(alg, mac);
        this.salt = Assert.notNull(salt, "Salt must not be null");
        this.cost = DefaultPassword.assertIterationsGte(cost);
    }

    @Override
    public PasswordDigest<A> get() {
        return new DefaultPasswordDigest<>(this.alg, this.MAC.doFinal(), this.salt, this.cost);
    }
}
