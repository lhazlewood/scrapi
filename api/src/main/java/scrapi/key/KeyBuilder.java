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

import scrapi.jca.SecurityBuilder;

/**
 * A {@code KeyBuilder} produces {@link Key}s suitable for use with an associated cryptographic algorithm.
 *
 * <p>{@code KeyBuilder}s are provided by components that implement the {@link KeyBuilderSupplier} interface,
 * ensuring the resulting {@link Key}s are compatible with their associated cryptographic algorithm.</p>
 *
 * @param <K> the type of key to build
 * @param <B> the type of the builder, for subtype method chaining
 * @see KeyBuilderSupplier
 */
public interface KeyBuilder<K extends Key<?>, B extends KeyBuilder<K, B>> extends SecurityBuilder<K, B> {
}