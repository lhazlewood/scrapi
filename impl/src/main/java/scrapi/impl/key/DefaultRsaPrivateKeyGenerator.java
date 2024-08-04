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
import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;
import scrapi.util.Assert;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;

public class DefaultRsaPrivateKeyGenerator extends AbstractKeyGenerator<RsaPrivateKey, DefaultRsaPrivateKeyGenerator> {

    /**
     * Per NIST doc <a href="https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-57pt1r5.pdf">
     * https://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-57pt1r5.pdf</a>, Table 2 (page 54),
     * RSA 3072-bit keys have 128 bits of security strength, so that's our default.
     */
    private static final Size DEFAULT_KEY_SIZE = Size.bits(AbstractRsaKey.MIN_SIZE.bits() + 1024);

    public DefaultRsaPrivateKeyGenerator() {
        super(AbstractRsaKey.JCA_ALG_NAME, "RSA modulus size", AbstractRsaKey.MIN_SIZE, DEFAULT_KEY_SIZE);
    }

    @Override
    public RsaPrivateKey get() {
        Size size = resolveSize();
        KeyPair pair = jca().generateKeyPair(size.bits());
        RSAPublicKey jcaPub = Assert.isInstance(RSAPublicKey.class, pair.getPublic(), DefaultRsaPublicKey.JCA_PUB_TYPE_MSG);
        RsaPublicKey pub = new DefaultRsaPublicKey(jcaPub);
        PrivateKey jcaPriv = Assert.notNull(pair.getPrivate(), "RSA KeyPair private key cannot be null.");
        return DefaultRsaPrivateKey.of(jcaPriv, pub);
    }
}
