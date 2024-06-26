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

import scrapi.alg.Size;
import scrapi.key.PbeKey;
import scrapi.util.Arrays;
import scrapi.util.Assert;
import scrapi.util.Bytes;
import scrapi.util.Randoms;

public class DefaultPbeKeyGenerator extends AbstractKeyGenerator<PbeKey, PbeKey.Generator>
        implements PbeKey.Generator {

    private static final char[] DEFAULT_ALPHABET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_+={}[]|\\;:\"<>,./?"
                    .toCharArray();

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int DEFAULT_PASSWORD_LENGTH = 16;
    private static final String PASSWORD_LENGTH_MSG = "password length must be >= " + MIN_PASSWORD_LENGTH;
    private char[] alphabet;
    private int passwordLength;
    private char[] password;
    private byte[] salt;
    private int iterations;

    public DefaultPbeKeyGenerator(String jcaName, Size keySize, int defaultIterations) {
        super(jcaName, "derived key size", keySize, keySize);
        this.alphabet = DEFAULT_ALPHABET;
        this.passwordLength = DEFAULT_PASSWORD_LENGTH;
        this.iterations = DefaultPbeKey.assertIterationsGte(defaultIterations, DefaultPbeKey.MIN_ITERATIONS);
    }

    @Override
    public PbeKey.Generator password(char[] password) {
        char[] pwd = Assert.notEmpty(password, "password must not be null or empty.");
        Assert.gte(pwd.length, MIN_PASSWORD_LENGTH, PASSWORD_LENGTH_MSG);
        this.password = password.clone();
        return self();
    }

    @Override
    public PbeKey.Generator salt(byte[] salt) {
        Size saltSize = Size.of(salt);
        Assert.gt(saltSize, Size.bits(0), "salt must not be null or empty.");
        if (saltSize.compareTo(this.MIN_SIZE) < 0) {
            String msg = "salt must be >= " + saltSize;
            throw new IllegalArgumentException(msg);
        }
        this.salt = salt;
        return self();
    }

    @Override
    public PbeKey.Generator iterations(int iterations) {
        this.iterations = DefaultPbeKey.assertIterationsGte(iterations, DefaultPbeKey.MIN_ITERATIONS);
        return self();
    }

    @Override
    public PbeKey.Generator alphabet(char[] alphabet) {
        this.alphabet = Assert.notEmpty(alphabet, "alphabet must not be null or empty.");
        return self();
    }

    @Override
    public PbeKey.Generator passwordLength(int numCharacters) {
        this.passwordLength = Assert.gte(numCharacters, MIN_PASSWORD_LENGTH, PASSWORD_LENGTH_MSG);
        return self();
    }

    private char[] randomPassword() {
        final char[] password = new char[this.passwordLength];
        for (int i = 0; i < password.length; i++) {
            int index = Randoms.secureRandom().nextInt(this.alphabet.length);
            password[i] = this.alphabet[index];
        }
        return password;
    }


    @Override
    public PbeKey get() {
        char[] password = this.password;
        if (Arrays.isEmpty(password)) {
            password = randomPassword();
        }
        Size size = resolveSize();
        byte[] salt = this.salt;
        if (Bytes.isEmpty(salt)) {
            salt = Bytes.randomBits(size.bits());
        }
        return new DefaultPbeKey(this.jcaName, password, salt, this.iterations, size);
    }
}
