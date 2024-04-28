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

import scrapi.key.RsaPrimeFactor;
import scrapi.util.Assert;

import java.math.BigInteger;
import java.security.spec.RSAOtherPrimeInfo;

public class DefaultRsaPrimeFactor implements RsaPrimeFactor {

    private final RSAOtherPrimeInfo info;

    public DefaultRsaPrimeFactor(RSAOtherPrimeInfo info) {
        this.info = Assert.notNull(info, "RSAOtherPrimeInfo cannot be null.");
    }

    @Override
    public BigInteger prime() {
        return this.info.getPrime();
    }

    @Override
    public BigInteger exponent() {
        return this.info.getExponent();
    }

    @Override
    public BigInteger coefficient() {
        return this.info.getCrtCoefficient();
    }
}
