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
import javax.crypto.spec.SecretKeySpec;
import java.lang.ref.Cleaner;
import java.lang.ref.Reference;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Optional;

public final class DefaultPassword implements Password, scrapi.lang.Destroyable, AutoCloseable {

    public static final int MIN_ITERATIONS = 1024;
    public static final String MIN_ITERATIONS_MSG = "iterations must be >= " + MIN_ITERATIONS;

    private final char[] chars;
    private transient boolean destroyed;
    private final Cleaner.Cleanable cleanable;

    public static int assertIterationsGte(int iterations) {
        return Assert.gte(iterations, MIN_ITERATIONS, MIN_ITERATIONS_MSG);
    }

    public DefaultPassword(final char[] chars) {
        char[] theChars = Assert.notEmpty(chars, "chars cannot be null or empty.").clone();
        this.chars = theChars;
        this.cleanable = AbstractKey.cleaner.register(this, new CharsClearer(theChars));
    }

    @Override
    public char[] chars() {
        try {
            if (this.destroyed) throw new IllegalStateException("Password has been destroyed");
            return this.chars.clone();
        } finally {
            // prevent this from being cleaned for the above block
            Reference.reachabilityFence(this);
        }
    }

    @Override
    public Optional<Size> size() {
        // Passwords have poor entropy, so there is no direct bit size correlation - a KDF must be used instead to
        // derive a valid key from the password:
        return Optional.empty();
    }

    @Override
    public void destroy() {
        try {
            if (!this.destroyed) {
                this.destroyed = true;
                this.cleanable.clean();
            }
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    @Override
    public boolean isDestroyed() {
        return this.destroyed;
    }

    @Override
    public void close() {
        destroy();
    }

    public static SecretKeySpec toJcaKey(char[] chars) {
        char[] pwd = Strings.EMPTY_CHARS;
        byte[] pwdBytes = Bytes.EMPTY;
        try {
            pwd = Assert.notEmpty(chars, "chars cannot be null or empty.").clone();
            pwdBytes = Strings.utf8(CharBuffer.wrap(chars));
            return new SecretKeySpec(pwdBytes, "PBE");
        } finally {
            Arrays.fill(pwd, '\0');
            Arrays.fill(pwdBytes, (byte) 0);
        }
    }

    @Override
    public SecretKey toJcaKey() {
        String msg = Password.class.getName() + " instances cannot be used directly as JCA keys.";
        throw new UnsupportedOperationException(msg);
    }

    @Override
    public int hashCode() {
        try {
            int hashCode = 0;
            for (int i = 1; i < this.chars.length; i++) {
                hashCode += this.chars[i] * i;
            }
            return hashCode;
        } finally {
            // prevent this from being cleaned for the above block
            Reference.reachabilityFence(this);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Password pwd)) return false;
        char[] otherChars = Strings.EMPTY_CHARS;
        try {
            otherChars = pwd.chars();
            return isEqual(this.chars, otherChars);
        } finally {
            Arrays.fill(otherChars, '\0');
            // prevent this from being cleaned for the above block
            Reference.reachabilityFence(this);
        }
    }

    static boolean isEqual(char[] a, char[] b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        int aLen = a.length;
        int bLen = b.length;
        if (bLen == 0) return aLen == 0;

        int result = 0;
        result |= aLen - bLen;

        // time-constant comparison
        for (int i = 0; i < aLen; i++) {
            // If i >= lenB, indexB is 0; otherwise, i.
            int indexB = ((i - bLen) >>> 31) * i;
            result |= a[i] ^ b[indexB];
        }
        return result == 0;
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
}
