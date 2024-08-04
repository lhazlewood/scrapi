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

import scrapi.alg.Providable;
import scrapi.alg.Randomizable;
import scrapi.key.Keyable;
import scrapi.key.PrivateKey;
import scrapi.msg.Signer;

import java.util.function.Supplier;

public interface SignerBuilder<K extends PrivateKey<?, ?>>
        extends Providable<SignerBuilder<K>>,
        Randomizable<SignerBuilder<K>>,
        Keyable<K, SignerBuilder<K>>,
        Supplier<Signer> {
}
