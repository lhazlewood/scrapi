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
import scrapi.impl.key.KeyableSupport;
import scrapi.key.PbeKey;
import scrapi.msg.Hasher;
import scrapi.msg.PbeMacAlgorithm;
import scrapi.util.Assert;
import scrapi.util.Bytes;

import javax.crypto.Mac;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Provider;

public class DefaultPbeMacAlgorithm
        extends AbstractMacAlgorithm<PbeKey, PbeMacAlgorithm.HasherBuilder, PbeKey.Generator>
        implements PbeMacAlgorithm {

    protected final int DEFAULT_ITERATIONS;

    protected DefaultPbeMacAlgorithm(String id, Provider provider, Size digestSize, int defaultIterations) {
        super(id, provider, digestSize);
        this.DEFAULT_ITERATIONS = DefaultPbeKey.assertIterationsGte(defaultIterations, DefaultPbeKey.MIN_ITERATIONS);
    }

    @Override
    public HasherBuilder digester() {
        return new DefaultHasherBuilder(this.ID).provider(this.PROVIDER).iterations(this.DEFAULT_ITERATIONS);
    }

    @Override
    public HasherBuilder verifier() {
        return digester();
    }

    @Override
    public PbeKey.Generator keygen() {
        return new DefaultPbeKeyGenerator(id(), digestSize(), this.DEFAULT_ITERATIONS);
    }

    static class DefaultHasherBuilder extends KeyableSupport<PbeKey, HasherBuilder> implements HasherBuilder {

        private byte[] salt;
        private int iterations;

        public DefaultHasherBuilder(String jcaName) {
            super(jcaName);
        }

        @Override
        public HasherBuilder salt(byte[] salt) {
            this.salt = Bytes.isEmpty(salt) ? null : salt.clone();
            return self();
        }

        @Override
        public HasherBuilder iterations(int iterations) {
            this.iterations = iterations;
            return self();
        }

        @Override
        public Hasher build() {
            Assert.notEmpty(this.salt, "salt cannot be null or empty.");
            DefaultPbeKey.assertIterationsGte(this.iterations, DefaultPbeKey.MIN_ITERATIONS);
            Mac m = jca().withMac(mac -> {
                PBEParameterSpec spec = new PBEParameterSpec(this.salt, this.iterations);
                mac.init(this.key.toJcaKey(), spec);
                return mac;
            });
            return new JcaMacDigester(m);
        }
    }
}
