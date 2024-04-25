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
import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;

import java.math.BigInteger;

abstract class AbstractRsaPrivateKeyBuilder<K extends RsaPrivateKey, T extends KeyBuilder<K, T> & RsaPrivateKey.Mutator<T>>
        extends AbstractRsaKeyBuilder<K, T> implements RsaPrivateKey.Mutator<T> {

    protected RsaPublicKey publicKey;
    protected BigInteger privateExponent;
    protected int bitLength;

    @Override
    public T publicKey(RsaPublicKey publicKey) {
        this.publicKey = publicKey;
        if (publicKey != null) {
            modulus(publicKey.modulus());
            publicExponent(publicKey.publicExponent());
            bitLength(publicKey.bitLength().orElseThrow(() ->
                    new IllegalArgumentException("RsaPublicKey bitLength cannot be null.")));
        }
        return self();
    }

    @Override
    public T privateExponent(BigInteger privateExponent) {
        this.privateExponent = privateExponent;
        return self();
    }

    public T bitLength(int bitLength) {
        this.bitLength = bitLength;
        return self();
    }
}
