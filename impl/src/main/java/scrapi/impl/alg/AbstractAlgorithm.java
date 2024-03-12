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

import scrapi.Algorithm;
import scrapi.util.Assert;

import java.security.Provider;

public abstract class AbstractAlgorithm<A extends Algorithm<A>> implements Algorithm<A> {

    protected final String ID;

    protected final Provider PROVIDER;

    @SuppressWarnings("unchecked")
    protected final A self() {
        return (A) this;
    }

    protected AbstractAlgorithm(String id, Provider provider) {
        this.ID = Assert.hasText(id, "Algorithm id cannot be null or empty.");
        this.PROVIDER = provider; // can be null
    }

    @Override
    public String id() {
        return this.ID;
    }

    @Override
    public int hashCode() {
        return this.ID.hashCode();
    }

    @Override
    public String toString() {
        return this.ID;
    }
}
