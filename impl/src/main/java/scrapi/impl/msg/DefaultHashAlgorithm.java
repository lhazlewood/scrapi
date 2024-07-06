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
import scrapi.impl.jca.JcaTemplate;
import scrapi.msg.HashAlgorithm;
import scrapi.msg.Hasher;
import scrapi.util.Assert;

import java.security.MessageDigest;
import java.security.Provider;
import java.util.function.Supplier;

class DefaultHashAlgorithm
        extends AbstractDigestAlgorithm<Hasher, Hasher, Supplier<Hasher>, Supplier<Hasher>>
        implements HashAlgorithm {

    private static Size digestSize(String jcaName, Provider provider) {
        int numBytes = new JcaTemplate(jcaName, provider).withMessageDigest(MessageDigest::getDigestLength);
        Assert.gt(numBytes, 0, "JCA digestLength must be > 0");
        return Size.bytes(numBytes);
    }

    DefaultHashAlgorithm(String id, Provider provider) {
        this(id, provider, digestSize(id, provider));
    }

    DefaultHashAlgorithm(String id, Provider provider, Size digestSize) {
        super(id, provider, digestSize);
    }

    @Override
    public Supplier<Hasher> digester() {
        return () -> new JcaMessageDigester(this.ID, this.PROVIDER);
    }

    @Override
    public Supplier<Hasher> verifier() {
        return digester();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        return obj instanceof HashAlgorithm && super.equals(obj);
    }

}
