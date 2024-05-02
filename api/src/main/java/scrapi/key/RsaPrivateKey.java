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
package scrapi.key;

import scrapi.util.Classes;

import java.math.BigInteger;
import java.util.Optional;

public interface RsaPrivateKey extends PrivateKey<java.security.PrivateKey, RsaPublicKey>, RsaKey<java.security.PrivateKey> {

    /**
     * Returns the private exponent {@code d}, a positive integer, if it is available. The value may not be
     * {@link Optional#isPresent() present} if the implementation does not expose key material, as might be the case
     * with some JCA {@link java.security.Provider Provider}s and/or Hardware Security Modules.
     *
     * @return the private exponent {@code d}, a positive integer, if it is available.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>
     */
    Optional<BigInteger> privateExponent();

    interface Mutator<T extends Mutator<T>> extends RsaKey.Mutator<T>, PrivateKey.Mutator<RsaPublicKey, T> {
        T privateExponent(BigInteger privateExponent);
    }

    interface Builder extends PrivateKey.Builder<RsaPublicKey, RsaPrivateKey, Builder>, CrtRsaPrivateKey.Mutator<Builder> {
    }

    static RsaPrivateKey.Builder builder() {
        return Classes.newInstance("scrapi.impl.key.DefaultRsaPrivateKeyBuilder");
    }
}
