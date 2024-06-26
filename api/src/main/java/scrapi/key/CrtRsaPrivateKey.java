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

import java.math.BigInteger;
import java.util.List;
import java.util.function.Consumer;

/**
 * An RSA Private Key using Chinese Remainder Theorem (CRT) values as defined by
 * <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2</a>'s second
 * representation.
 *
 * @since SCRAPI_RELEASE_VERSION
 */
public interface CrtRsaPrivateKey extends RsaPrivateKey {

    /**
     * Returns the first factor, a positive integer.
     *
     * @return the first factor, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2 (second representation)</a>
     */
    BigInteger firstFactor();

    /**
     * Returns the second factor, a positive integer.
     *
     * @return the second factor, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2 (second representation)</a>
     */
    BigInteger secondFactor();

    /**
     * Returns the first factor's CRT exponent, a positive integer.
     *
     * @return the first factor's CRT exponent, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2 (second representation)</a>
     */
    BigInteger firstFactorExponent();

    /**
     * Returns the second factor's CRT exponent, a positive integer.
     *
     * @return the second factor's CRT exponent, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2 (second representation)</a>
     */
    BigInteger secondFactorExponent();

    /**
     * Returns the (first) CRT coefficient, a positive integer.
     *
     * @return the (first) CRT coefficient, a positive integer.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2 (second representation)</a>
     */
    BigInteger firstFactorCoefficient();

    /**
     * Returns any additional (possibly empty) prime factors of the {@link #modulus() modulus} {@code modulus}.
     *
     * @return any additional (possibly empty) prime factors of the {@link #modulus() modulus} {@code modulus}.
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc8017#section-3.2">RFC 8017, Section 3.2 (second representation)</a>
     */
    List<RsaPrimeFactor> otherFactors();

    interface Mutator<T extends Mutator<T>> extends RsaPrivateKey.Mutator<T> {

        T firstFactor(BigInteger firstFactor);

        T secondFactor(BigInteger secondFactor);

        T firstFactorExponent(BigInteger firstFactorExponent);

        T secondFactorExponent(BigInteger secondFactorExponent);

        T firstFactorCoefficient(BigInteger firstFactorCoefficient);

        T add(RsaPrimeFactor factor);

        T add(Consumer<RsaPrimeFactor.Mutator<?>> c);
    }

    interface Builder extends Mutator<Builder>, PrivateKey.Builder<RsaPublicKey, CrtRsaPrivateKey, Builder> {
    }
}
