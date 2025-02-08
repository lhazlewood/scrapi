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

import scrapi.key.SymmetricKey;
import scrapi.msg.Digest;
import scrapi.msg.MacAlgorithm;

import javax.crypto.Mac;
import java.security.Provider;

class DefaultMacHasher<A extends MacAlgorithm<?, ?, ?, Digest<A>, ?>> extends AbstractMacHasher<Digest<A>, A> {

    DefaultMacHasher(A alg, Mac mac) {
        super(alg, mac);
    }

    DefaultMacHasher(A alg, Provider provider, SymmetricKey key) {
        super(alg, provider, key);
    }

    @Override
    public Digest<A> get() {
        return new DefaultDigest<>(this.alg, this.MAC.doFinal());
    }
}
