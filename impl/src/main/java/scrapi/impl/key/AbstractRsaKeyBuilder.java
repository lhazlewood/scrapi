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

import scrapi.key.KeyBuilder;
import scrapi.key.RsaKey;
import scrapi.util.Assert;

import java.math.BigInteger;

abstract class AbstractRsaKeyBuilder<K extends RsaKey<?>, T extends KeyBuilder<K, T> & RsaKey.Mutator<T>>
        extends AbstractKeyBuilder<K, T> implements RsaKey.Mutator<T> {

    static final String ALG_NAME = "RSA";
    static final int MIN_KEY_SIZE = 1024;

    protected BigInteger modulus;
    protected BigInteger publicExponent;

    protected AbstractRsaKeyBuilder() {
        this(MIN_KEY_SIZE);
    }

    protected AbstractRsaKeyBuilder(int minSize) {
        this(ALG_NAME, minSize);
    }

    protected AbstractRsaKeyBuilder(String jcaName, int minSize) {
        super(jcaName, minSize);
    }

    @Override
    public T modulus(BigInteger modulus) {
        Assert.notNull(modulus, "RSA Key modulus cannot be null.");
        assertSize(modulus.bitLength(), this.minSize, "RSA Key modulus size");
        this.modulus = modulus;
        return self();
    }

    @Override
    public T publicExponent(BigInteger publicExponent) {
        this.publicExponent = Assert.notNull(publicExponent, "RSA Key public exponent cannot be null.");
        return self();
    }
}
