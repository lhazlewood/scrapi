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

public interface RsaPrimeFactor {

    /**
     * Returns the factor's prime, a positive integer.
     *
     * @return the factor's prime, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    BigInteger prime();

    /**
     * Returns the factor's CRT exponent, a positive integer.
     *
     * @return the factor's CRT exponent, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    BigInteger exponent();

    /**
     * Returns the factor's CRT coefficient, a positive integer.
     *
     * @return the factor's CRT coefficient, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>, second representation.
     */
    BigInteger coefficient();

    interface Mutator<T extends Mutator<T>> {

        T prime(BigInteger prime);

        T exponent(BigInteger exponent);

        T coefficient(BigInteger coefficient);
    }

    interface Builder extends Mutator<Builder>, scrapi.lang.Builder<RsaPrimeFactor> {
    }

    static Builder builder() {
        return Classes.newInstance("scrapi.impl.key.DefaultRsaPrimeFactorBuilder");
    }
}
