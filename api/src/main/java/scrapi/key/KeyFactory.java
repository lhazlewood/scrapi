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

import java.util.function.Supplier;

/**
 * A KeyFactory produces Key results. There is no requirement that a new or distinct result be returned each time
 * the factory is invoked.
 *
 * @param <K> the type of key provided in the factory result
 * @param <R> the type of result which contains the key
 * @param <T> the KeyFactory subtype, useful for method chaining
 */
public interface KeyFactory<K extends Key<?>, R extends Keyed<K>, T extends KeyFactory<K, R, T>>
        extends Providable<T>, Supplier<R> {
}
