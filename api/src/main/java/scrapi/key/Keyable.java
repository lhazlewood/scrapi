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
 * Represents the ability to accept a {@link Key} for use in cryptographic algorithms.
 *
 * <p>This is a {@link FunctionalInterface functionl interface} whose functional method is {@link #key(Key)}.</p>
 *
 * @param <K> the type of key accepted.
 * @param <T> the type of object returned after accepting the key, often useful for method chaining.
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface Keyable<K extends Key<?>, T> {

    /**
     * Accepts the specified key.
     *
     * @param key the key to accept.
     * @return the key to accept.
     */
    T key(K key);

}
