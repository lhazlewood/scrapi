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
import scrapi.util.Assert;

import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import java.util.Optional;

@SuppressWarnings("serial")
public class DefaultPbeKey extends PBEKeySpec implements PbeKey, PBEKey {

    public static final Size MIN_SIZE = Size.bits(128);
    public static final String MIN_SIZE_MSG = "size must be >= " + MIN_SIZE;
    public static final int MIN_ITERATIONS = 1024;
    public static final String MIN_ITERATIONS_MSG = "iterations must be >= " + MIN_ITERATIONS;

    private final String jcaAlg;
    private volatile boolean destroyed;

    public DefaultPbeKey(String jcaAlg, char[] password, byte[] salt, int iterations, Size derivedKeySize) {
        super(Assert.notEmpty(password, "password cannot be null or empty."),
                Assert.notEmpty(salt, "salt cannot be null or empty."),
                assertIterationsGte(iterations, MIN_ITERATIONS),
                Assert.gte(Assert.notNull(derivedKeySize, "derivedKeySize cannot be null"), MIN_SIZE, MIN_SIZE_MSG).bits());
        this.jcaAlg = Assert.hasText(jcaAlg, "jcaAlg cannot be null or empty.");
    }

    public static int assertIterationsGte(int iterations, int requirement) {
        if (iterations < requirement) {
            String msg = "iterations must be >= " + requirement;
            throw new IllegalArgumentException(msg);
        }
        return iterations;
    }

    @Override
    public Optional<Size> size() {
        return Optional.of(Size.bits(getKeyLength()));
    }

    @Override
    public char[] password() {
        return super.getPassword();
    }

    @Override
    public byte[] salt() {
        return super.getSalt();
    }

    @Override
    public int iterations() {
        return super.getIterationCount();
    }

    @Override
    public String getAlgorithm() {
        return this.jcaAlg;
    }

    @Override
    public String getFormat() {
        return "RAW";
    }

    @Override
    public byte[] getEncoded() {
        // 'Real' PBE keys are derived using a cryptographic algorithm, and this class implementation does not
        // represent a real derived key - only the values needed to derive one.  As such, we don't return an encoded
        // byte array here to avoid the ambiguity and confusion that could result if the caller might expect
        // the (non-derived) password bytes and not the 'real' derived bytes (or vice versa).
        //
        // (that is, this implementation is only used with derivation algorithms, and does not represent an
        //  already-derived key).
        return null;
    }

    @Override
    public void destroy() {
        destroyed = true;
        clearPassword();
    }

    @Override
    public boolean isDestroyed() {
        return this.destroyed;
    }

    @Override
    public void close() throws Exception {
        destroy();
    }

    @Override
    public PBEKey toJcaKey() {
        return this;
    }
}
