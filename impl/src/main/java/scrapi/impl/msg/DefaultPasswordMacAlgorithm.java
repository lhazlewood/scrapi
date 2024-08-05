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
import scrapi.key.PasswordGenerator;
import scrapi.key.PasswordStretcher;
import scrapi.msg.Hasher;
import scrapi.util.Assert;

import javax.crypto.Mac;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Provider;
import java.util.function.Consumer;

public class DefaultPasswordMacAlgorithm
        extends AbstractMacAlgorithm<Password, DefaultPasswordMacAlgorithm.PbeParams, PasswordGenerator> {

    protected final int DEFAULT_ITERATIONS;

    protected DefaultPasswordMacAlgorithm(String id, Provider provider, Size digestSize, int defaultIterations) {
        super(id, provider, digestSize, DefaultPasswordGenerator::new);
        this.DEFAULT_ITERATIONS = DefaultPassword.assertIterationsGte(defaultIterations);
    }

    @Override
    public Hasher producer(Consumer<PbeParams> c) {
        PbeParams params = new PbeParams(this.ID).provider(this.PROVIDER).iterations(this.DEFAULT_ITERATIONS);
        c.accept(params);
        return params.get();
    }

    public static class PbeParams extends KeyableSupport<Password, PbeParams> implements PasswordStretcher<PbeParams> {

        private byte[] salt;
        private int iterations;

        public PbeParams(String jcaName) {
            super(jcaName);
        }

        @Override
        public PbeParams salt(byte[] salt) {
            this.salt = Assert.notEmpty(salt, "salt cannot be null or empty").clone();
            return self();
        }

        @Override
        public PbeParams iterations(int iterations) {
            this.iterations = DefaultPassword.assertIterationsGte(iterations);
            return self();
        }

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
            return new DefaultMacHasher(m);
        }
    }
}
