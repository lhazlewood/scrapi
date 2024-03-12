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
package scrapi.impl.lang;

import scrapi.lang.Identifiable;

import java.util.Collection;

public class IdentifiableRegistry<K, V extends Identifiable<K>> extends DefaultRegistry<K, V> {

    public IdentifiableRegistry(String name, Collection<? extends V> values) {
        super(name, "id", values, Identifiable::id);
    }
}