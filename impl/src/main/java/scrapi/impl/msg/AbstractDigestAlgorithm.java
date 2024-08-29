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
import scrapi.alg.Sized;
import scrapi.impl.alg.AbstractAlgorithm;
import scrapi.msg.IntegrityAlgorithm;
import scrapi.util.Assert;
import scrapi.util.Objects;

import java.security.Provider;

abstract class AbstractDigestAlgorithm extends AbstractAlgorithm implements IntegrityAlgorithm, Sized {

    protected final Size DIGEST_SIZE;

    AbstractDigestAlgorithm(String id, Provider provider, Size digestSize) {
        super(id, provider);
        this.DIGEST_SIZE = Assert.notNull(digestSize, "digestSize cannot be null");
    }

    @Override
    public Size size() {
        return this.DIGEST_SIZE;
    }

    @Override
    public int hashCode() {
        return Objects.nullSafeHashCode(this.ID, this.DIGEST_SIZE);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        return obj instanceof AbstractDigestAlgorithm a &&
                super.equals(a) && this.DIGEST_SIZE.equals(a.DIGEST_SIZE);
    }
}
