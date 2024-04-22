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

import scrapi.digest.Digest;
import scrapi.digest.DigestAlgorithm;
import scrapi.util.Assert;
import scrapi.util.Bytes;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Objects;

final class DefaultDigest<A extends DigestAlgorithm<A>> implements Digest<A> {

    private final A algorithm; // should this be here?
    private final byte[] digest;

    public DefaultDigest(A algorithm, byte[] digest) {
        this.digest = Assert.notEmpty(digest, "digest array argument cannot be null or empty.");
        this.algorithm = Assert.notNull(algorithm, "DigestAlgorithm argument cannot be null.");
        //Assert.eq((int) Bytes.bitLength(digest), algorithm.bitLength(), "Algorithm Digest length mismatch.");
    }

    @Override
    public A algorithm() {
        return this.algorithm;
    }

    @Override
    public byte[] get() {
        return this.digest;
    }

    @Override
    public int bitLength() {
        return (int) Bytes.bitLength(digest);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Digest) {
            Digest<?> digest = (Digest<?>) o;
            return this.algorithm.equals(digest.algorithm()) &&
                    MessageDigest.isEqual(this.digest, digest.get());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(algorithm, Arrays.hashCode(digest));
    }
}
