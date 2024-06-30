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
import scrapi.key.SecretKey;
import scrapi.lang.Builder;
import scrapi.msg.Hasher;
import scrapi.msg.MacAlgorithm;

import java.security.Provider;

abstract class AbstractMacAlgorithm<
        K extends SecretKey<?>,
        HB extends Keyable<K, HB> & Builder<Hasher>,
        G extends KeyGenerator<K, G>
        >
        extends AbstractDigestAlgorithm<Hasher, Hasher, HB, HB> implements MacAlgorithm<K, HB, G> {

    protected AbstractMacAlgorithm(String id, Provider provider, Size digestSize) {
        super(id, provider, digestSize);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        return obj instanceof MacAlgorithm && super.equals(obj);
    }
}
