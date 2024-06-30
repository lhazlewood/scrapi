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
package scrapi.impl.alg;

import scrapi.alg.Providable;
import scrapi.alg.Randomizable;
import scrapi.impl.jca.JcaTemplate;
import scrapi.util.Assert;

import java.security.Provider;
import java.security.SecureRandom;

public abstract class AlgorithmSupport<T> implements Providable<T>, Randomizable<T> {

    protected final String jcaName;
    protected Provider provider;
    protected SecureRandom random;

    public AlgorithmSupport(String jcaName) {
        this.jcaName = Assert.hasText(jcaName, "jcaName cannot be null or empty.");
    }

    @SuppressWarnings("unchecked")
    protected final T self() {
        return (T) this;
    }

    @Override
    public T provider(Provider provider) {
        this.provider = provider;
        return self();
    }

    public Provider provider() {
        return this.provider;
    }

    @Override
    public T random(SecureRandom random) {
        this.random = random;
        return self();
    }

    public SecureRandom random() {
        return this.random;
    }

    protected JcaTemplate jca() {
        return new JcaTemplate(this.jcaName, this.provider, this.random);
    }
}
