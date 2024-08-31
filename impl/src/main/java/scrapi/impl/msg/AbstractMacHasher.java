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

import scrapi.impl.jca.JcaTemplate;
import scrapi.key.SymmetricKey;
import scrapi.msg.Digest;
import scrapi.msg.Hasher;
import scrapi.msg.MacAlgorithm;
import scrapi.util.Assert;

import javax.crypto.Mac;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.Provider;

abstract class AbstractMacHasher<D extends Digest<A>, A extends MacAlgorithm<?, ?, D, ?, ?>>
        extends AbstractMessageConsumer<Hasher<D>> implements Hasher<D> {

    public static final String JCA_KEY_NOT_NULL = SymmetricKey.class.getName() + " toJcaKey() value cannot be null.";

    protected final A alg;
    protected final Mac MAC;

    AbstractMacHasher(A alg, Mac mac) {
        this.alg = Assert.notNull(alg, "alg must not be null.");
        this.MAC = Assert.notNull(mac, "Mac must not be null");
    }

    AbstractMacHasher(A alg, Provider provider, SymmetricKey key) {
        this.alg = Assert.notNull(alg, "alg must not be null.");
        Assert.notNull(key, "MAC key cannot be null.");
        javax.crypto.SecretKey jcaKey = Assert.notNull(key.toJcaKey(), JCA_KEY_NOT_NULL);
        this.MAC = new JcaTemplate(alg.id(), provider).withMac(mac -> {
            mac.init(jcaKey);
            return mac;
        });
    }

    @Override
    protected void doApply(byte input) {
        this.MAC.update(input);
    }

    @Override
    protected void doApply(byte[] input) {
        this.MAC.update(input);
    }

    @Override
    protected void doApply(byte[] input, int offset, int len) {
        this.MAC.update(input, offset, len);
    }

    @Override
    protected void doApply(ByteBuffer input) {
        this.MAC.update(input);
    }

    @Override
    public boolean test(byte[] bytes) {
        return MessageDigest.isEqual(get().octets(), bytes); // constant time operation
    }
}
