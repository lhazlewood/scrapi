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
import scrapi.impl.key.DefaultPassword;
import scrapi.impl.key.DefaultPasswordGenerator;
import scrapi.impl.key.KeyableSupport;
import scrapi.key.Password;
import scrapi.msg.Hasher;
import scrapi.msg.PasswordMacAlgorithm;
import scrapi.util.Assert;
import scrapi.util.Bytes;

import javax.crypto.Mac;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Provider;

public class DefaultPasswordMacAlgorithm
        extends AbstractMacAlgorithm<Password, PasswordMacAlgorithm.HasherBuilder, Password.Generator>
        implements PasswordMacAlgorithm {

    protected final int DEFAULT_ITERATIONS;

    protected DefaultPasswordMacAlgorithm(String id, Provider provider, Size digestSize, int defaultIterations) {
        super(id, provider, digestSize);
        this.DEFAULT_ITERATIONS = DefaultPassword.assertIterationsGte(defaultIterations);
    }

    @Override
    public HasherBuilder digester() {
        return new DefaultHasherBuilder(this.ID).provider(this.PROVIDER).iterations(this.DEFAULT_ITERATIONS);
    }

    @Override
    public Password.Generator keygen() {
        return new DefaultPasswordGenerator();
    }

    static class DefaultHasherBuilder extends KeyableSupport<Password, HasherBuilder> implements HasherBuilder {

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
            this.iterations = DefaultPassword.assertIterationsGte(iterations);
            return self();
        }

        @Override
        public Hasher get() {
            Assert.notNull(this.key, "Password cannot be null or empty.");
            Assert.notEmpty(this.salt, "salt cannot be null or empty.");
            DefaultPassword.assertIterationsGte(this.iterations);
            Mac m = jca().withMac(mac -> {
                SecretKeySpec keySpec = DefaultPassword.toJcaKey(this.key.chars());
                PBEParameterSpec spec = new PBEParameterSpec(this.salt, this.iterations);
                mac.init(keySpec, spec);
                return mac;
            });
            return new JcaMacDigester(m);
        }
    }
}
