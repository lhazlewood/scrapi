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

import scrapi.alg.Size;
import scrapi.key.ConfidentialKey;
import scrapi.key.KeyGenerator;
import scrapi.util.Assert;

abstract class AbstractKeyGenerator<K extends ConfidentialKey<?>, T extends KeyGenerator<K, T>> extends AbstractKeyFactory<K, T>
        implements KeyGenerator<K, T> {

    protected final Size DEFAULT_SIZE;
    protected Size size;

    AbstractKeyGenerator(String jcaName, Size minSize, Size defaultSize) {
        this(jcaName, "size", minSize, defaultSize);
    }

    AbstractKeyGenerator(String jcaName, String sizeName, Size minSize, Size defaultSize) {
        super(jcaName, sizeName, minSize);
        this.DEFAULT_SIZE = Assert.gte(defaultSize, this.MIN_SIZE, "defaultSize must be >= minSize");
    }

    public final T size(Size size) {
        this.size = this.SIZE_VALIDATOR.apply(size);
        return self();
    }

    protected Size resolveSize() {
        return this.size == null ? this.DEFAULT_SIZE : this.size;
    }
}
