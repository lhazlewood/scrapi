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
 * A {@code KeyBuilderSupplier} produces {@link KeyBuilder}s that may be used to create
 * {@link Key}s suitable for use with an associated cryptographic algorithm.
 *
 * @param <K> type of {@link Key} produced by the {@link KeyBuilder}
 * @param <B> type of {@link KeyBuilder} produced each time the {@link #key()} method is invoked.
 * @see #key()
 * @see KeyBuilder
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface KeyBuilderSupplier<K extends Key<?>, B extends KeyBuilder<K, B>> {

    /**
     * Returns a new {@link KeyBuilder} instance that will produce keys with a length sufficient
     * to be used by the component's associated cryptographic algorithm.
     *
     * @return a new {@link KeyBuilder} instance that will produce keys with a length sufficient
     * to be used by the component's associated cryptographic algorithm.
     */
    B key();
}