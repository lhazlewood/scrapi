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
package scrapi.lang;

import java.util.Set;

/**
 * An {@link Identified} instance that has additional aliases in addition to its primary {@link #id() id}.
 *
 * @param <T> the type of id and aliases
 * @since SCRAPI_RELEASE_VERSION
 */
public interface Aliased<T> extends Identified<T> {

    /**
     * Returns the aliases that identify this instance in addition to its primary {@link #id() id}.
     *
     * @return the aliases that identify this instance in addition to its primary {@link #id() id}.
     */
    Set<T> aliases();
}
