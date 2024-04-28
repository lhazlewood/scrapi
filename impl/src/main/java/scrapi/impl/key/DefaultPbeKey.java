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
package scrapi.impl.key;

import scrapi.key.PbeKey;
import scrapi.util.Assert;

import javax.crypto.interfaces.PBEKey;
import java.util.Optional;

public class DefaultPbeKey extends AbstractKey<PBEKey> implements PbeKey {

    private final int generatedKeySize;

    public DefaultPbeKey(PBEKey key, int generatedKeySize) {
        super(key);
        this.generatedKeySize = Assert.gt(generatedKeySize, 0, "generatedKeySize must be >= 0");
    }

    @Override
    public Optional<Integer> bitLength() {
        return Optional.of(generatedKeySize);
    }

    @Override
    public char[] password() {
        return this.key.getPassword();
    }

    @Override
    public byte[] salt() {
        return this.key.getSalt();
    }

    @Override
    public int iterations() {
        return this.key.getIterationCount();
    }
}
