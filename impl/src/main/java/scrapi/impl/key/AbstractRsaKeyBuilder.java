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

import scrapi.alg.Providable;
import scrapi.alg.Size;
import scrapi.key.RsaKey;

import java.math.BigInteger;
import java.util.function.Supplier;

abstract class AbstractRsaKeyBuilder<K extends RsaKey<?>, T extends Providable<T> & Supplier<K> & RsaKey.Mutator<T>>
        extends AbstractKeyFactory<K, T> implements RsaKey.Mutator<T> {

    private static final String SIZE_NAME = AbstractRsaKey.N.name() + " size";

    protected BigInteger modulus;
    protected BigInteger publicExponent;

    protected AbstractRsaKeyBuilder() {
        this(AbstractRsaKey.MIN_SIZE);
    }

    protected AbstractRsaKeyBuilder(Size minSize) {
        this(AbstractRsaKey.JCA_ALG_NAME, minSize);
    }

    protected AbstractRsaKeyBuilder(String jcaName, Size minSize) {
        super(jcaName, SIZE_NAME, minSize);
    }

    @Override
    public T modulus(BigInteger modulus) {
        BigInteger n = AbstractRsaKey.N.check(modulus);
        this.SIZE_VALIDATOR.apply(Size.bits(n.bitLength()));
        this.modulus = n;
        return self();
    }

    @Override
    public T publicExponent(BigInteger publicExponent) {
        this.publicExponent = AbstractRsaKey.E.check(publicExponent);
        return self();
    }
}
