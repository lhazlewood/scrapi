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
import scrapi.impl.key.DefaultPbeKey;
import scrapi.impl.key.DefaultPbeKeyGenerator;
import scrapi.key.PbeKey;
import scrapi.msg.MacAlgorithm;
import scrapi.util.Assert;

import java.security.Provider;

public class DefaultPbeMacAlgorithm
        extends AbstractMacAlgorithm<PbeKey, PbeKey.Generator> {

    protected final int DEFAULT_ITERATIONS;

    protected DefaultPbeMacAlgorithm(String id, Provider provider, Size digestSize, int defaultIterations) {
        super(id, provider, digestSize);
        this.DEFAULT_ITERATIONS = DefaultPbeKey.assertIterationsGte(defaultIterations, DefaultPbeKey.MIN_ITERATIONS);
    }

    @Override
    public MacAlgorithm<PbeKey, PbeKey.Generator> provider(Provider provider) {
        Assert.notNull(provider, "Provider cannot not be null");
        return new DefaultPbeMacAlgorithm(this.ID, provider, digestSize(), this.DEFAULT_ITERATIONS);
    }


    @Override
    public PbeKey.Generator keygen() {
        return new DefaultPbeKeyGenerator(id(), digestSize(), this.DEFAULT_ITERATIONS);
    }
}
