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

import scrapi.alg.Providable;
import scrapi.alg.Randomizable;

import java.util.function.Supplier;

/**
 * A {@code KeyGenerator} creates new secure-random {@link Key}s suitable for use with an associated cryptographic
 * algorithm each time its {@link #get()} method is invoked.
 *
 * @param <K> the type of key to generate
 * @param <T> the generator subtype for method chaining
 * @since SCRAPI_RELEASE_VERSION
 */
public interface KeyGenerator<K extends ConfidentialKey<?>, T extends KeyGenerator<K, T>>
        extends Providable<T>, Randomizable<T>, Supplier<K> {
}
