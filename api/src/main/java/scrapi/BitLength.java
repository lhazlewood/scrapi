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
package scrapi;

/**
 * Supplies the length in bits (not bytes) of the implementing object.
 *
 * @since SCRAPI_RELEASE_VERSION
 */
@FunctionalInterface
public interface BitLength {

    /**
     * Returns the length in bits <em>(not bytes)</em> of the associated object.
     *
     * @return the length in bits <em>(not bytes)</em> of the associated object.
     */
    long getBitLength();
}
