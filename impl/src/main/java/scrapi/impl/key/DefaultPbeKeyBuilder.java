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
import scrapi.util.Assert;
import scrapi.util.Bytes;
import scrapi.util.Randoms;

public class DefaultPbeKeyBuilder
        extends AbstractKeyBuilder<PbeKey, PbeKey.Builder>
        implements PbeKey.Builder {

    private static final char[] ALPHABET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_+={}[]|\\;:\"<>,./?"
                    .toCharArray();

    private static final char[] EMPTY_CHARS = new char[0];

    private final int DEFAULT_ITERATIONS;

    private char[] password = EMPTY_CHARS;
    private byte[] salt;
    private int iterations;

    public DefaultPbeKeyBuilder(String jcaName, int generatedKeySize, int defaultIterations) {
        super(jcaName, generatedKeySize);
        this.DEFAULT_ITERATIONS = Assert.gte(defaultIterations, DefaultPbeKey.MIN_ITERATIONS, DefaultPbeKey.MIN_ITERATIONS_MSG);
    }

    @Override
    public PbeKey.Builder password(char[] password) {
        if (password == null || password.length == 0) {
            this.password = EMPTY_CHARS;
        } else {
            this.password = password.clone();
        }
        return self();
    }

    @Override
    public PbeKey.Builder salt(byte[] salt) {
        this.salt = Assert.notEmpty(salt, "salt cannot be null or empty.").clone();
        return self();
    }

    @Override
    public PbeKey.Builder iterations(int iterations) {
        this.iterations = Assert.gte(iterations, DefaultPbeKey.MIN_ITERATIONS, DefaultPbeKey.MIN_ITERATIONS_MSG);
        return self();
    }

    private static char[] randomPassword() {
        final char[] password = new char[16];
        for (int i = 0; i < password.length; i++) {
            int index = Randoms.secureRandom().nextInt(ALPHABET.length);
            password[i] = ALPHABET[index];
        }
        return password;
    }

    @Override
    public PbeKey build() {
        char[] password = this.password;
        if (password == null || password.length == 0) {
            password = randomPassword();
        }
        int size = Math.max(this.size /* might not have been configured, so default to min: */, this.minSize);
        byte[] salt = this.salt;
        if (Bytes.isEmpty(salt)) {
            salt = Bytes.randomBits(size);
        }
        int iterations = this.iterations;
        if (iterations < DefaultPbeKey.MIN_ITERATIONS) {
            iterations = DEFAULT_ITERATIONS;
        }

        return new DefaultPbeKey(this.jcaName, password, salt, iterations, size);
    }
}
