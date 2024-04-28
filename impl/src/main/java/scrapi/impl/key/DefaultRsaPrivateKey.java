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

import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;
import scrapi.util.Assert;

import java.math.BigInteger;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Optional;

public class DefaultRsaPrivateKey extends AbstractPrivateKey<java.security.PrivateKey, RsaPublicKey>
        implements RsaPrivateKey {

    public DefaultRsaPrivateKey(java.security.PrivateKey key, RsaPublicKey publicKey) {
        super(key, publicKey);
        Assert.isInstance(RSAKey.class, key, "PrivateKey must be an instanceof java.security.interfaces.RSAKey");
    }

    @Override
    public Optional<BigInteger> privateExponent() {
        BigInteger privExp = null;
        if (this.key instanceof RSAPrivateKey) {
            privExp = ((RSAPrivateKey) this.key).getPrivateExponent();
        }
        return Optional.ofNullable(privExp);
    }

    @Override
    public BigInteger modulus() {
        return Assert.isInstance(RSAKey.class, this.key, "RSAKey type required.").getModulus();
    }

    @Override
    public Optional<Integer> bitLength() {
        return Optional.of(modulus().bitLength());
    }

    @Override
    public BigInteger publicExponent() {
        return publicKey().publicExponent();
    }
}
