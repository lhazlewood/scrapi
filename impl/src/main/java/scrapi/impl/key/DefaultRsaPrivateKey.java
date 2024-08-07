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

import scrapi.impl.util.Parameter;
import scrapi.impl.util.Parameters;
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

    static final Parameter<BigInteger> D = Parameters.positiveBigInt("d", "RSA key private exponent", true);

    private final RsaPublicKey publicKey;

    public DefaultRsaPrivateKey(java.security.PrivateKey priv, RsaPublicKey publicKey) {
        super(priv);
        this.publicKey = Assert.notNull(publicKey, "RsaPublicKey cannot be null.");
        if (priv instanceof RSAKey rsaPriv) { // might not implement RSAKey if HSM or PKCS11
            Assert.eq(rsaPriv.getModulus(), publicKey.modulus(),
                    "RSA PrivateKey modulus and RsaPublicKey modulus must be equal.");
        }
    }

    @Override
    public RsaPublicKey publicKey() {
        return this.publicKey;
    }

    @Override
    public BigInteger modulus() {
        return this.publicKey.modulus(); // guaranteed to be the same in the constructor
    }

    @Override
    public BigInteger publicExponent() {
        // JCA RSA Private Keys don't expose this, so we delegate to the public key:
        return publicKey.publicExponent();
    }

    @Override
    public Optional<BigInteger> privateExponent() {
        BigInteger d = this.key instanceof RSAPrivateKey rsaPriv ? rsaPriv.getPrivateExponent() : null;
        return Optional.ofNullable(d);
    }

    static RsaPrivateKey of(java.security.PrivateKey jcaPriv, RsaPublicKey pub) {
        if (jcaPriv instanceof RSAPrivateCrtKey || jcaPriv instanceof RSAMultiPrimePrivateCrtKey) {
            return new DefaultCrtRsaPrivateKey(jcaPriv, pub);
        }
        return new DefaultRsaPrivateKey(jcaPriv, pub);
    }
}
