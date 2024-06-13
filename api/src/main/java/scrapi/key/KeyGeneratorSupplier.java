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
 * A {@code KeyGeneratorSupplier} produces {@link KeyGenerator}s that may be used to generate new
 * {@link Key}s suitable for use with an associated cryptographic algorithm.
 *
 * @param <K> type of {@link Key} generated
 * @param <G> type of {@link KeyGenerator} created each time the {@link #keygen()} method is invoked.
 * @see #keygen()
 * @see KeyGenerator
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface KeyGeneratorSupplier<K extends ConfidentialKey<?>, G extends KeyGenerator<K, G>> {

    /**
     * Returns a new {@link KeyGenerator} instance that will generate new keys with a length sufficient
     * to be used by the component's associated cryptographic algorithm.
     *
     * @return a new {@link KeyGenerator} instance that will generate new keys with a length sufficient
     * to be used by the component's associated cryptographic algorithm.
     */
    G keygen();
}
