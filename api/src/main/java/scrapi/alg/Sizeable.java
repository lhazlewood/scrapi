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
package scrapi.alg;

/**
 * Interface implemented by {@code Object}s that allow configuration of a cryptographic {@link Size}, for example,
 * block size, key length, etc.
 *
 * @param <T> the type of {@code Object} returned after setting the {@code SecureRandom}, usually for method chaining.
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface Sizeable<T> {

    /**
     * Sets the cryptographic {@link Size}.
     *
     * @param s the cryptographic size.
     * @return the associated object for method chaining.
     */
    T size(Size s);
}
