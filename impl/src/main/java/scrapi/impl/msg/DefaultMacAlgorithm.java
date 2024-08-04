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

import scrapi.alg.Size;
import scrapi.impl.key.DefaultOctetSecretKeyGenerator;
import scrapi.impl.key.KeyableSupport;
import scrapi.key.OctetSecretKey;
import scrapi.msg.Hasher;

import java.security.Provider;
import java.util.function.Consumer;

public class DefaultMacAlgorithm extends AbstractMacAlgorithm<OctetSecretKey, DefaultMacAlgorithm.MacHasherBuilder, OctetSecretKey.Generator> {

    public DefaultMacAlgorithm(String id, Provider provider, Size digestSize) {
        super(id, provider, digestSize, () -> new DefaultOctetSecretKeyGenerator(id, digestSize));
    }

    @SuppressWarnings("ClassEscapesDefinedScope")
    @Override
    public Hasher digester(Consumer<MacHasherBuilder> c) {
        MacHasherBuilder builder = new MacHasherBuilder(this.ID).provider(this.PROVIDER);
        c.accept(builder);
        return builder.get();
    }

    static final class MacHasherBuilder extends KeyableSupport<OctetSecretKey, MacHasherBuilder> {

        MacHasherBuilder(String jcaName) {
            super(jcaName);
        }

        Hasher get() {
            return new DefaultMacHasher(this.jcaName, this.provider, this.key);
        }
    }
}
