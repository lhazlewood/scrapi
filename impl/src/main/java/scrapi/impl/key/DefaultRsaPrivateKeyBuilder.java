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

import scrapi.impl.jca.JcaTemplate;
import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;
import scrapi.util.Assert;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;

@SuppressWarnings("unused") // used reflectively by RsaPrivateKey.builder()
public class DefaultRsaPrivateKeyBuilder
        extends AbstractRsaPrivateKeyBuilder<RsaPrivateKey, RsaPrivateKey.Builder>
        implements RsaPrivateKey.Builder {

    private static final String RSA_PUB_TYPE_MSG = "RSA PublicKey must be a " + RSAKey.class.getName() + " instance.";

    @Override
    public RsaPrivateKey build() {
        KeyPair pair = new JcaTemplate("RSA").generateKeyPair(this.bitLength);
        RSAPublicKey jcaPub = Assert.isInstance(RSAPublicKey.class, pair.getPublic(), RSA_PUB_TYPE_MSG);
        RsaPublicKey pub = new DefaultRsaPublicKey(jcaPub);
        PrivateKey jcaPriv = Assert.notNull(pair.getPrivate(), "RSA KeyPair private key cannot be null.");
        return new DefaultRsaPrivateKey(jcaPriv, pub);
    }
}
