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
package scrapi.key

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class RsaPrimeFactorTest {

    @Test
    void builder() {
        // random BigInteger values are fine for this test, we're just seeing if the values are set correctly:
        def f = RsaPrimeFactor.builder()
                .prime(BigInteger.ONE).exponent(BigInteger.TWO).coefficient(BigInteger.TEN)
                .build()

        assertEquals(BigInteger.ONE, f.prime())
        assertEquals(BigInteger.TWO, f.exponent())
        assertEquals(BigInteger.TEN, f.coefficient())
    }
}
