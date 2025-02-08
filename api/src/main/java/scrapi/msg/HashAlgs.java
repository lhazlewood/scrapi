/*
 * Copyright © 2025 Les Hazlewood
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
package scrapi.msg;

import scrapi.lang.Registry;
import scrapi.util.Classes;

final class HashAlgs {

    //prevent instantiation
    private HashAlgs() {
    }

    private static final String IMPL_CLASSNAME = "scrapi.impl.msg.StandardHashAlgorithms";
    static final Registry<String, HashAlgorithm> REGISTRY = Classes.newInstance(IMPL_CLASSNAME);
}
