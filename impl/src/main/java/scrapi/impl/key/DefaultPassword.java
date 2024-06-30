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
import scrapi.key.Password;
import scrapi.util.Assert;
import scrapi.util.Bytes;
import scrapi.util.Strings;

import javax.crypto.SecretKey;
import java.io.Serial;
import java.lang.ref.Cleaner;
import java.nio.CharBuffer;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public class DefaultPassword extends AbstractKey<SecretKey> implements Password {

    private static final String JCA_ALG_NAME = "PBEKey";

    private final char[] chars;
    private final Cleaner.Cleanable charsCleanable;

    protected DefaultPassword(final char[] chars) {
        super(new DestroyableSecretKey(Strings.utf8(CharBuffer.wrap(
                Assert.notEmpty(chars, "chars cannot be null or empty.").clone()
        )), JCA_ALG_NAME));
        char[] theChars = chars.clone();
        this.chars = theChars;
        this.charsCleanable = cleaner.register(this, new CharsClearer(theChars));
    }

    @Override
    public char[] chars() {
        if (isDestroyed()) throw new IllegalStateException("Password has been destroyed");
        return this.chars.clone();
    }

    @Override
    public Optional<Size> size() {
        return Optional.empty();
    }

    @Override
    public void destroy() {
        super.destroy();
        this.charsCleanable.clean();
    }

    private static final class CharsClearer implements Runnable {
        private final char[] chars;

        public CharsClearer(char[] chars) {
            this.chars = chars;
        }

        @Override
        public void run() {
            Arrays.fill(this.chars, '\u0000');
        }
    }

    private static final class DestroyableSecretKey implements SecretKey {

        @Serial
        private static final long serialVersionUID = 266611362737780161L;

        private transient boolean destroyed;
        private final byte[] bytes;
        private final String alg;

        public DestroyableSecretKey(byte[] bytes, String alg) {
            this.bytes = Assert.notEmpty(bytes, "bytes cannot be null or empty.");
            this.alg = Assert.hasText(alg, "alg cannot be null or empty.");
        }

        @Override
        public String getAlgorithm() {
            return this.alg;
        }

        @Override
        public String getFormat() {
            return "RAW";
        }

        @Override
        public byte[] getEncoded() {
            return this.bytes.clone();
        }

        @Override
        public void destroy() {
            this.destroyed = true;
            Arrays.fill(this.bytes, (byte) 0);
        }

        @Override
        public boolean isDestroyed() {
            return this.destroyed;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(this.bytes) ^ this.alg.toLowerCase(Locale.ENGLISH).hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof SecretKey sk)) return false;

            String oAlg = sk.getAlgorithm();
            if (!this.alg.equals(oAlg)) return false;

            byte[] oBytes = AbstractKey.findEncoded(sk);
            try {
                return MessageDigest.isEqual(this.bytes, oBytes);
            } finally {
                Bytes.clear(oBytes);
            }
        }
    }
}
