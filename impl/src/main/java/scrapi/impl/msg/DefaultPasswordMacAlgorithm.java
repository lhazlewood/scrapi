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
import scrapi.msg.PasswordMacAlgorithm;
import scrapi.util.Assert;

import javax.crypto.Mac;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Provider;
import java.util.function.Consumer;

class DefaultPasswordMacAlgorithm extends AbstractMacAlgorithm<Password, DefaultPasswordMacAlgorithm.Builder, PasswordGenerator>
        implements PasswordMacAlgorithm<DefaultPasswordMacAlgorithm.Builder> {

    protected final int DEFAULT_ITERATIONS;

    DefaultPasswordMacAlgorithm(String id, Provider provider, Size digestSize, int defaultIterations) {
        super(id, provider, digestSize, DefaultPasswordGenerator::new);
        this.DEFAULT_ITERATIONS = DefaultPassword.assertIterationsGte(defaultIterations);
    }

    @Override
    public Hasher with(Consumer<Builder> c) {
        Builder builder = new Builder(this.ID);
        builder.provider(this.PROVIDER).cost(this.DEFAULT_ITERATIONS);
        c.accept(builder);
        return builder.get();
    }

    static class Builder extends KeyableSupport<Password, Builder> implements PasswordStretcher<Builder> {

        private byte[] salt;
        private int iterations;

        private Builder(String jcaName) {
            super(jcaName);
        }

        @Override
        public Builder salt(byte[] salt) {
            this.salt = Assert.notEmpty(salt, "salt cannot be null or empty").clone();
            return self();
        }

        @Override
        public Builder cost(int i) {
            this.iterations = DefaultPassword.assertIterationsGte(i);
            return self();
        }

        private Hasher get() {
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
