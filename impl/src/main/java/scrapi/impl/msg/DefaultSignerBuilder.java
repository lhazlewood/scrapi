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

import scrapi.impl.key.KeyableSupport;
import scrapi.key.PrivateKey;
import scrapi.msg.SignatureAlgorithm;
import scrapi.msg.Signer;
import scrapi.util.Assert;

class DefaultSignerBuilder<K extends PrivateKey<?, ?>, A extends SignatureAlgorithm<K, ?, ?, ?, ?, A>>
        extends KeyableSupport<K, DefaultSignerBuilder<K, A>> {

    private final A alg;

    DefaultSignerBuilder(A alg) {
        super(Assert.notNull(alg, "alg must not be null.").id());
        this.alg = alg;
    }

    Signer<A> get() {
        return new DefaultSigner<>(this.alg, this.provider, this.random, this.key);
    }
}
