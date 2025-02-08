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

/**
 * Represents a {@link Key} supplier. There is no requirement that a new or distinct key is returned each time the
 * {@link #key()} method is invoked.
 *
 * <p>This is a {@link FunctionalInterface functional interface} whose functional method is {@link #key()}.</p>
 *
 * @param <K> the type of Key.
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface Keyed<K extends Key<?>> {

    /**
     * Returns a key.
     *
     * @return a key.
     */
    K key();

    static <K extends Key<?>> Keyed<K> of(K key) {
        return new DefaultKeyResult<>(key);
    }
}
