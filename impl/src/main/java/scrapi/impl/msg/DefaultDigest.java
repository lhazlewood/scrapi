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

import scrapi.msg.Digest;
import scrapi.msg.IntegrityAlgorithm;
import scrapi.util.Assert;
import scrapi.util.Objects;

import java.security.MessageDigest;

class DefaultDigest<A extends IntegrityAlgorithm> implements Digest<A> {

    protected final A algorithm;
    protected final byte[] octets;

    DefaultDigest(A algorithm, byte[] octets) {
        this.algorithm = Assert.notNull(algorithm, "algorithm must not be null");
        this.octets = Assert.notNull(octets, "octets must not be null");
    }

    @Override
    public A algorithm() {
        return this.algorithm;
    }

    @Override
    public byte[] octets() {
        return this.octets.clone();
    }

    @Override
    public int hashCode() {
        return Objects.nullSafeHashCode(this.algorithm, this.octets);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        return obj instanceof Digest<?> d &&
                this.algorithm.equals(d.algorithm()) &&
                MessageDigest.isEqual(this.octets, d.octets());
    }
}
