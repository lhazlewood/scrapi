/*
 * Copyright © 2023 Les Hazlewood
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
package scrapi.lang;

/**
 * Type-safe interface that reflects the <a href="https://en.wikipedia.org/wiki/Builder_pattern">Builder</a> design
 * pattern.
 *
 * <p>There is no requirement that a new or distinct result be returned each time the builder is invoked.</p>
 *
 * <p>This is a {@link FunctionalInterface} whose functional method is {@link #build()}.
 *
 * @param <T> The type of result returned when {@link #build()} is invoked.
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface Builder<T> {

    /**
     * Returns the build result.
     *
     * @return the build result.
     */
    T build();
}
