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
 * Interface implemented by {@code Object}s that report a relevant cryptographic {@link Size}, for example,
 * block size, digest size, etc.
 *
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface Sized {

    /**
     * Returns the object's associated cryptographic {@link Size}.
     *
     * @return the object's associated cryptographic {@link Size}.
     */
    Size size();

}
