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
package scrapi.impl.digest;

import scrapi.digest.DigestAlgorithm;
import scrapi.impl.alg.AbstractAlgorithm;
import scrapi.util.Assert;
import scrapi.util.Objects;

import java.security.Provider;

abstract class AbstractDigestAlgorithm<A extends DigestAlgorithm<A>> extends AbstractAlgorithm<A> implements DigestAlgorithm<A> {

    protected final int BITLEN;

    AbstractDigestAlgorithm(String id, Provider provider, int bitLength) {
        super(id, provider);
        this.BITLEN = Assert.gt(bitLength, 0, "bitLength must be positive (greater than zero).");
    }

    @Override
    public int bitLength() {
        return this.BITLEN;
    }

    @Override
    public int hashCode() {
        return Objects.nullSafeHashCode(this.ID, this.BITLEN);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof DigestAlgorithm) {
            DigestAlgorithm<?> other = (DigestAlgorithm<?>) obj;
            return this.BITLEN == other.bitLength() && this.ID.equals(other.id());
        }
        return false;
    }
}
