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

import scrapi.alg.Size;
import scrapi.key.KeyGenerator;
import scrapi.key.Keyable;
import scrapi.key.SymmetricKey;
import scrapi.msg.Digest;
import scrapi.msg.MacAlgorithm;
import scrapi.util.Assert;

import java.security.Provider;
import java.util.function.Supplier;

abstract class AbstractMacAlgorithm<
        K extends SymmetricKey,
        DB extends Keyable<K, DB>,
        G extends KeyGenerator<K, G>,
        D extends Digest<T>,
        T extends MacAlgorithm<K, DB, G, D, T>
        >
        extends AbstractDigestAlgorithm implements MacAlgorithm<K, DB, G, D, T> {

    private final Supplier<G> keygen;

    protected AbstractMacAlgorithm(String id, Provider provider, Size digestSize, Supplier<G> keygen) {
        super(id, provider, digestSize);
        this.keygen = Assert.notNull(keygen, "keygen cannot be null");
    }

    @Override
    public G keygen() {
        return this.keygen.get();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        return obj instanceof MacAlgorithm && super.equals(obj);
    }
}
