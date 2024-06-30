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
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.util.Optional;

public class DefaultRsaPrivateKey extends AbstractRsaKey<java.security.PrivateKey>
        implements RsaPrivateKey {

    private final RsaPublicKey publicKey;

    public DefaultRsaPrivateKey(java.security.PrivateKey key, RsaPublicKey publicKey) {
        super(key);
        this.publicKey = Assert.notNull(publicKey, "RsaPublicKey cannot be null.");
        RSAKey priv = Assert.isInstance(RSAKey.class, key, RSA_KEY_TYPE_MSG);
        Assert.eq(priv.getModulus(), publicKey.n(), "RSA PrivateKey n and RsaPublicKey n must be equal.");
    }

    @Override
    public RsaPublicKey publicKey() {
        return this.publicKey;
    }

    @Override
    public BigInteger n() {
        return this.publicKey.n(); // guaranteed to be the same in the constructor if possible
    }

    @Override
    public BigInteger e() {
        // JCA RSA Private Keys don't expose this, so we delegate to the public key:
        return publicKey.e();
    }

    @Override
    public Optional<BigInteger> d() {
        BigInteger d = null;
        if (this.key instanceof RSAPrivateKey) {
            d = ((RSAPrivateKey) this.key).getPrivateExponent();
        }
        return Optional.ofNullable(d);
    }

    @Override
    public KeyPair toJcaKeyPair() {
        return new KeyPair(this.publicKey.toJcaKey(), toJcaKey());
    }

    static RsaPrivateKey of(java.security.PrivateKey jcaPriv, RsaPublicKey pub) {
        if (jcaPriv instanceof RSAPrivateCrtKey || jcaPriv instanceof RSAMultiPrimePrivateCrtKey) {
            return new DefaultCrtRsaPrivateKey(jcaPriv, pub);
        }
        return new DefaultRsaPrivateKey(jcaPriv, pub);
    }
}
