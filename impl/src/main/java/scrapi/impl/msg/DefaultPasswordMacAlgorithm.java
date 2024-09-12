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
import scrapi.msg.PasswordDigest;
import scrapi.msg.PasswordMacAlgorithm;
import scrapi.util.Assert;
import scrapi.util.Bytes;

import javax.crypto.Mac;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Provider;
import java.util.function.Consumer;

class DefaultPasswordMacAlgorithm extends AbstractMacAlgorithm<
        Password,
        DefaultPasswordMacAlgorithm.Builder,
        PasswordDigest<DefaultPasswordMacAlgorithm>,
        PasswordGenerator,
        DefaultPasswordMacAlgorithm
        >
        implements PasswordMacAlgorithm<
        DefaultPasswordMacAlgorithm.Builder,
        PasswordDigest<DefaultPasswordMacAlgorithm>,
        DefaultPasswordMacAlgorithm
        > {

    protected final int DEFAULT_ITERATIONS;

    DefaultPasswordMacAlgorithm(String id, Provider provider, Size digestSize, int defaultIterations) {
        super(id, provider, digestSize, DefaultPasswordGenerator::new);
        this.DEFAULT_ITERATIONS = DefaultPassword.assertIterationsGte(defaultIterations);
    }

    @Override
    public Hasher<PasswordDigest<DefaultPasswordMacAlgorithm>> with(Consumer<Builder> p) {
        Builder builder = new Builder(this);
        builder.provider(this.PROVIDER).cost(this.DEFAULT_ITERATIONS);
        p.accept(builder);
        return builder.get();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        return obj instanceof PasswordMacAlgorithm && super.equals(obj);
    }

    static class Builder extends KeyableSupport<Password, Builder> implements PasswordStretcher<Builder> {

        private final DefaultPasswordMacAlgorithm alg;
        private byte[] salt;
        private int iterations;

        private Builder(DefaultPasswordMacAlgorithm alg) {
            super(Assert.notNull(alg, "alg must not be null.").id());
            this.alg = alg;
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

        private Hasher<PasswordDigest<DefaultPasswordMacAlgorithm>> get() {
            Assert.notNull(this.key, "Password cannot be null or empty.");
            DefaultPassword.assertIterationsGte(this.iterations);
            final byte[] salt = !Bytes.isEmpty(this.salt) ? this.salt : Bytes.random(this.alg.size().bytes());
            Mac m = jca().withMac(mac -> {
                SecretKeySpec keySpec = DefaultPassword.toJcaKey(this.key.chars());
                PBEParameterSpec spec = new PBEParameterSpec(salt, this.iterations);
                mac.init(keySpec, spec);
                return mac;
            });
            return new DefaultPasswordMacHasher<>(this.alg, m, salt, this.iterations);
        }
    }
}
