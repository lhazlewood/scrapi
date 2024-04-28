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
import java.security.KeyPair;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Optional;

public class DefaultRsaPrivateKey extends AbstractRsaKey<java.security.PrivateKey>
        implements RsaPrivateKey {

    private final RsaPublicKey publicKey;

    public DefaultRsaPrivateKey(java.security.PrivateKey key, RsaPublicKey publicKey) {
        super(key);
        this.publicKey = Assert.notNull(publicKey, "RsaPublicKey cannot be null.");
        if (key instanceof RSAKey) {
            RSAKey rsaKey = (RSAKey) key;
            Assert.eq(rsaKey.getModulus(), publicKey.modulus(), "RSA PrivateKey modulus and RsaPublicKey modulus must be equal.");
        }
    }

    @Override
    public RsaPublicKey publicKey() {
        return this.publicKey;
    }

    @Override
    public BigInteger modulus() {
        return this.publicKey.modulus(); // guaranteed to be the same in the constructor if possible
    }

    @Override
    public BigInteger publicExponent() {
        // JCA RSA Private Keys don't expose this, so we delegate to the public key:
        return publicKey.publicExponent();
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
    public KeyPair toJcaKeyPair() {
        return new KeyPair(this.publicKey.toJcaKey(), toJcaKey());
    }
}
