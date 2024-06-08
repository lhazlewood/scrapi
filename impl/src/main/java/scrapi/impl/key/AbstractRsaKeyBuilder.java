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
        extends AbstractKeyFactory<K, T> implements RsaKey.Mutator<T> {

    protected BigInteger modulus;
    protected BigInteger publicExponent;

    protected AbstractRsaKeyBuilder() {
        this(AbstractRsaKey.MIN_SIZE);
    }

    protected AbstractRsaKeyBuilder(int minSize) {
        this(AbstractRsaKey.JCA_ALG_NAME, minSize);
    }

    protected AbstractRsaKeyBuilder(String jcaName, int minSize) {
        super(jcaName, "RSA Key modulus size", minSize);
    }

    @Override
    public T n(BigInteger modulus) {
        Assert.notNull(modulus, "RSA Key modulus cannot be null.");
        this.SIZE_VALIDATOR.apply(modulus.bitLength());
        this.modulus = AbstractRsaKey.N.validate(modulus);
        return self();
    }

    @Override
    public T e(BigInteger publicExponent) {
        this.publicExponent = AbstractRsaKey.E.validate(publicExponent);
        return self();
    }
}
