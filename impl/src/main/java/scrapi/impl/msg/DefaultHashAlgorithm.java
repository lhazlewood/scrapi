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
import scrapi.impl.alg.AlgorithmSupport;
import scrapi.msg.HashAlgorithm;
import scrapi.msg.Hasher;

import java.security.Provider;
import java.util.function.Supplier;

class DefaultHashAlgorithm
        extends AbstractDigestAlgorithm<Hasher, Hasher, Supplier<Hasher>, Supplier<Hasher>>
        implements HashAlgorithm {

    DefaultHashAlgorithm(String id, Size digestSize) {
        this(id, null, digestSize);
    }

    private DefaultHashAlgorithm(String id, Provider provider, Size digestSize) {
        super(id, provider, digestSize);
    }

    @Override
    public Supplier<Hasher> digester() {
        return new HasherBuilder(this.ID);
    }

    @Override
    public Supplier<Hasher> verifier() {
        return digester();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        return obj instanceof HashAlgorithm && super.equals(obj);
    }

    private static class HasherBuilder extends AlgorithmSupport<HasherBuilder> implements Supplier<Hasher> {

        public HasherBuilder(String jcaName) {
            super(jcaName);
        }

        @Override
        public Hasher get() {
            return new JcaMessageDigester(this.jcaName, this.provider);
        }
    }
}
