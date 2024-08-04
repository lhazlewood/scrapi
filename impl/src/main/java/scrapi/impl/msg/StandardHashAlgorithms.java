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
package scrapi.impl.msg;

import scrapi.impl.lang.IdentifiableRegistry;
import scrapi.msg.HashAlgorithm;
import scrapi.util.Collections;

public final class StandardHashAlgorithms extends IdentifiableRegistry<String, HashAlgorithm> {

    // ----------------------------------------------------------------------------------------------------------
    // https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#messagedigest-algorithms
    // ----------------------------------------------------------------------------------------------------------
    public StandardHashAlgorithms() {
        super("Hash Algorithm", Collections.<HashAlgorithm>of(
                new DefaultHashAlgorithm("MD2"),
                new DefaultHashAlgorithm("MD5"),
                new DefaultHashAlgorithm("SHA-1"),
                new DefaultHashAlgorithm("SHA-224"),
                new DefaultHashAlgorithm("SHA-256"),
                new DefaultHashAlgorithm("SHA-384"),
                new DefaultHashAlgorithm("SHA-512"),
                new DefaultHashAlgorithm("SHA-512/224"),
                new DefaultHashAlgorithm("SHA-512/256"),
                new DefaultHashAlgorithm("SHA3-224"),
                new DefaultHashAlgorithm("SHA3-256"),
                new DefaultHashAlgorithm("SHA3-384"),
                new DefaultHashAlgorithm("SHA3-512")
        ));
    }
}
