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

import scrapi.key.RsaPublicKey;

import java.math.BigInteger;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;

public class DefaultRsaPublicKey extends AbstractRsaKey<RSAPublicKey> implements RsaPublicKey {

    static final String JCA_BASE_TYPE_MSG = "JDK RSA PublicKey must be a " + RSAKey.class.getName() + " instance.";
    static final String JCA_PUB_TYPE_MSG = "JDK RSA PublicKey must be a " + RSAPublicKey.class.getName() + " instance.";

    public DefaultRsaPublicKey(RSAPublicKey key) {
        super(key);
    }

    @Override
    public BigInteger modulus() {
        return this.key.getModulus();
    }

    @Override
    public BigInteger publicExponent() {
        return this.key.getPublicExponent();
    }
}
