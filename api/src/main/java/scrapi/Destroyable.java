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
package scrapi;

/**
 * Potentially sensitive {@code Object}s (such as {@link scrapi.key.Key}s and credentials) may optionally implement
 * this interface to provide the capability to destroy its contents.
 */
public interface Destroyable {

    /**
     * Destroy this {@code Object}.
     *
     * <p>Sensitive information associated with this {@code Object} is destroyed or cleared. Subsequent calls to
     * certain methods on this {@code Object} will result in an {@code IllegalStateException} being thrown.
     */
    void destroy();

    /**
     * Returns {@code true} if this {@code Object} has been destroyed, {@code false} otherwise.
     *
     * @return {@code true} if this {@code Object} has been destroyed, {@code false} otherwise.
     */
    boolean isDestroyed();
}
