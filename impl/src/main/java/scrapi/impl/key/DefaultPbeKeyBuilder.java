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

import scrapi.impl.jca.AbstractSecurityBuilder;
import scrapi.impl.jca.JcaTemplate;
import scrapi.key.PbeKey;
import scrapi.util.Assert;
import scrapi.util.Bytes;
import scrapi.util.Randoms;

import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;

public class DefaultPbeKeyBuilder
        extends AbstractSecurityBuilder<PbeKey, PbeKey.Builder>
        implements PbeKey.Builder {

    private static final char[] ALPHABET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_+={}[]|\\;:\"<>,./?"
                    .toCharArray();

    public static final int MIN_ITERATIONS = 1024;
    public static final String MIN_ITERATIONS_MSG = "iterations must be >= " + MIN_ITERATIONS;
    private static final char[] EMPTY_CHARS = new char[0];

    private final int DEFAULT_ITERATIONS;

    private char[] password = EMPTY_CHARS;
    private byte[] salt;
    private int iterations;
    private final int keyBitLength;

    public DefaultPbeKeyBuilder(String jcaName, int keyBitLength, int defaultIterations) {
        super(jcaName);
        this.keyBitLength = Assert.gte(keyBitLength, 160, "keyBitLength must be >= 160");
        this.DEFAULT_ITERATIONS = Assert.gte(defaultIterations, MIN_ITERATIONS, MIN_ITERATIONS_MSG);
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
        this.salt = salt;
        return self();
    }

    @Override
    public PbeKey.Builder iterations(int iterations) {
        this.iterations = Assert.gte(iterations, MIN_ITERATIONS, MIN_ITERATIONS_MSG);
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
        byte[] salt = this.salt;
        if (Bytes.isEmpty(salt)) {
            salt = Bytes.randomBits(this.keyBitLength);
        }
        int iterations = this.iterations;
        if (iterations < MIN_ITERATIONS) {
            iterations = DEFAULT_ITERATIONS;
        }

        JcaTemplate template = new JcaTemplate(this.jcaName, this.provider, this.random);
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, this.keyBitLength);
        javax.crypto.SecretKey jcaKey = template.withSecretKeyFactory(factory -> factory.generateSecret(spec));
        PBEKey jcaPbeKey = Assert.isInstance(PBEKey.class, jcaKey, "JCA did not create a PBEKey instance.");
        return new JcaPbeKey(jcaPbeKey);
    }
}
