/*
 * Copyright © 2025 Les Hazlewood
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

import scrapi.util.Assert;

@SuppressWarnings("ClassCanBeRecord") // https://github.com/openclover/clover/issues/270
class DefaultKeyResult<T extends Key<?>> implements Keyed<T> {

    private final T key;

    DefaultKeyResult(T key) {
        this.key = Assert.notNull(key, "Key cannot be null.");
    }

    public T key() {
        return this.key;
    }
}
