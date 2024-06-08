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

import scrapi.impl.jca.AlgorithmSupport;
import scrapi.impl.lang.Interval;
import scrapi.impl.util.BitLengthValidator;
import scrapi.jca.Providable;
import scrapi.key.Key;
import scrapi.util.Assert;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

abstract class AbstractKeyFactory<K extends Key<?>, T extends Providable<T> & Supplier<K>> extends AlgorithmSupport<T> {

    protected final int MIN_SIZE;
    protected final UnaryOperator<Integer> SIZE_VALIDATOR;

    AbstractKeyFactory(String jcaName, int minSize) {
        this(jcaName, "size", minSize);
    }

    AbstractKeyFactory(String jcaName, String sizeName, int minSize) {
        super(jcaName);
        this.MIN_SIZE = Assert.gt(minSize, 0, "minSize must be > 0");
        this.SIZE_VALIDATOR = new BitLengthValidator(sizeName, Interval.gte(minSize));
    }
}
