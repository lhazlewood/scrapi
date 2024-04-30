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
package scrapi.impl.digest;

import scrapi.digest.MacAlgorithm;
import scrapi.impl.key.DefaultPbeKey;
import scrapi.impl.key.DefaultPbeKeyBuilder;
import scrapi.key.PbeKey;
import scrapi.util.Assert;

import java.security.Provider;

public class DefaultPbeMacAlgorithm
        extends AbstractMacAlgorithm<PbeKey, PbeKey.Builder>
        implements MacAlgorithm<PbeKey, PbeKey.Builder> {

    protected final int DEFAULT_ITERATIONS;

    protected DefaultPbeMacAlgorithm(String id, Provider provider, int bitLength, int defaultIterations) {
        super(id, provider, bitLength);
        this.DEFAULT_ITERATIONS = Assert.gte(defaultIterations, DefaultPbeKey.MIN_ITERATIONS,
                DefaultPbeKey.MIN_ITERATIONS_MSG);
    }

    @Override
    public MacAlgorithm<PbeKey, PbeKey.Builder> provider(Provider provider) {
        return new DefaultPbeMacAlgorithm(this.ID, this.PROVIDER, this.BITLEN, this.DEFAULT_ITERATIONS);
    }

    @Override
    public PbeKey.Builder key() {
        return new DefaultPbeKeyBuilder(id(), bitLength(), this.DEFAULT_ITERATIONS);
    }
}
