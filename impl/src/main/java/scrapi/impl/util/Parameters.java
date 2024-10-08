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
package scrapi.impl.util;

import scrapi.impl.lang.Predicates;

import java.math.BigInteger;

public final class Parameters {

    public static Parameter<BigInteger> positiveBigInt(String id, String name, boolean secret) {
        return new DefaultParameter<>(id, name, secret, Predicates.gt(BigInteger.ZERO), " must be >= 0");
    }
}
