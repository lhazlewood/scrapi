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
import scrapi.alg.Sized;
import scrapi.impl.key.DefaultPassword;
import scrapi.impl.key.DefaultPasswordGenerator;
import scrapi.impl.key.KeyableSupport;
import scrapi.key.Password;
import scrapi.key.PasswordGenerator;
import scrapi.msg.Hasher;
import scrapi.msg.HmacAlgorithm;
import scrapi.msg.PasswordDigest;
import scrapi.msg.PasswordMacAlgorithm;
import scrapi.msg.PbeMacAlgorithm;
import scrapi.util.Assert;
import scrapi.util.Bytes;

import javax.crypto.Mac;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Provider;
import java.util.function.Consumer;

class DefaultPbeMacAlgorithm extends AbstractMacAlgorithm<
        Password,
        PbeMacAlgorithm.Params,
        PasswordGenerator,
        PasswordDigest<PbeMacAlgorithm>,
        PbeMacAlgorithm
        >
        implements PbeMacAlgorithm {

    private static int defaultIterations(Sized alg) {
        int defaultIterations; // https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
        int bits = alg.size().bits();
        if (bits >= 512) {
            defaultIterations = 210_000;
        } else if (bits >= 384) {
            defaultIterations = 415_000;
        } else if (bits >= 256) {
            defaultIterations = 600_000;
        } else if (bits >= 224) {
            defaultIterations = 900_000;
        } else {
            defaultIterations = 1_300_000;
        }
        return defaultIterations;
    }

    protected final int DEFAULT_ITERATIONS;

    DefaultPbeMacAlgorithm(HmacAlgorithm hmacAlg) {
        this("PBEWith" + hmacAlg.id(), null, hmacAlg.size(), defaultIterations(hmacAlg));
    }

    private DefaultPbeMacAlgorithm(String id, Provider provider, Size digestSize, int defaultIterations) {
        super(id, provider, digestSize, DefaultPasswordGenerator::new);
        this.DEFAULT_ITERATIONS = DefaultPassword.assertIterationsGte(defaultIterations);
    }

    @Override
    public Hasher<PasswordDigest<PbeMacAlgorithm>> with(Consumer<Params> p) {
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

    static class Builder extends KeyableSupport<Password, Params> implements Params {

        private final PbeMacAlgorithm alg;
        private byte[] salt;
        private int iterations;

        private Builder(PbeMacAlgorithm alg) {
            super(Assert.notNull(alg, "alg must not be null.").id());
            this.alg = alg;
        }

        @Override
        public Params salt(byte[] salt) {
            this.salt = Assert.notEmpty(salt, "salt cannot be null or empty").clone();
            return self();
        }

        @Override
        public Params cost(int i) {
            this.iterations = DefaultPassword.assertIterationsGte(i);
            return self();
        }

        private Hasher<PasswordDigest<PbeMacAlgorithm>> get() {
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
