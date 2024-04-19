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
package scrapi.impl.jca;

import scrapi.jca.Providable;
import scrapi.jca.Randomizable;
import scrapi.lang.Builder;
import scrapi.util.Assert;

import java.security.Provider;
import java.security.SecureRandom;

public abstract class AbstractSecurityBuilder<T, B extends Providable<B> & Randomizable<B> & Builder<T>>
        implements Providable<B>, Randomizable<B>, Builder<T> {

    protected final String jcaName;

    protected Provider provider;
    protected SecureRandom random;

    protected AbstractSecurityBuilder(String jcaName) {
        this.jcaName = Assert.hasText(jcaName, "jcaName cannot be null or empty.");
    }

    @SuppressWarnings("unchecked")
    protected final B self() {
        return (B) this;
    }

    @Override
    public B provider(Provider provider) {
        this.provider = provider; // may be null
        return self();
    }

    @Override
    public B random(SecureRandom random) {
        this.random = random; // may be null
        return self();
    }
}
