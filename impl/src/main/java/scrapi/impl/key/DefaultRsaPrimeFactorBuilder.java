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

public class DefaultRsaPrimeFactorBuilder implements RsaPrimeFactor.Builder {

    private BigInteger prime;
    private BigInteger exponent;
    private BigInteger coefficient;

    @Override
    public RsaPrimeFactor.Builder prime(BigInteger prime) {
        this.prime = Assert.notNull(prime, "prime cannot be null.");
        return this;
    }

    @Override
    public RsaPrimeFactor.Builder exponent(BigInteger exponent) {
        this.exponent = Assert.notNull(exponent, "exponent cannot be null.");
        return this;
    }

    @Override
    public RsaPrimeFactor.Builder coefficient(BigInteger coefficient) {
        this.coefficient = Assert.notNull(coefficient, "coefficient cannot be null.");
        return this;
    }

    @Override
    public RsaPrimeFactor get() {
        return new DefaultRsaPrimeFactor(new RSAOtherPrimeInfo(this.prime, this.exponent, this.coefficient));
    }
}
