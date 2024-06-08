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
package scrapi.impl.key;

import scrapi.key.PbeKey;
import scrapi.util.Arrays;
import scrapi.util.Assert;
import scrapi.util.Bytes;

public class DefaultPbeKeyBuilder
        extends AbstractKeyFactory<PbeKey, PbeKey.Builder>
        implements PbeKey.Builder {

    private final int MIN_ITERATIONS;

    private char[] password;
    private byte[] salt;
    private int iterations;

    public DefaultPbeKeyBuilder(String jcaName, int derivedKeySize, int minIterations) {
        super(jcaName, derivedKeySize);
        this.MIN_ITERATIONS = DefaultPbeKey.assertIterationsGte(minIterations, DefaultPbeKey.MIN_ITERATIONS);
    }

    @Override
    public PbeKey.Builder password(char[] password) {
        this.password = Assert.notEmpty(password, "password cannot be null or empty.").clone();
        return self();
    }

    @Override
    public PbeKey.Builder salt(byte[] salt) {
        this.salt = Assert.notEmpty(salt, "salt cannot be null or empty.").clone();
        return self();
    }

    @Override
    public PbeKey.Builder iterations(int iterations) {
        this.iterations = DefaultPbeKey.assertIterationsGte(iterations, this.MIN_ITERATIONS);
        return self();
    }

    @Override
    public PbeKey get() {
        if (Arrays.isEmpty(this.password)) {
            throw new IllegalStateException("password cannot be null or empty.");
        }
        if (Bytes.isEmpty(this.salt)) {
            throw new IllegalStateException("salt cannot be null or empty.");
        }
        if (this.iterations < this.MIN_ITERATIONS) {
            throw new IllegalStateException("iterations must be >= " + this.MIN_ITERATIONS);
        }
        return new DefaultPbeKey(this.jcaName, password, salt, iterations, this.MIN_SIZE);
    }
}
