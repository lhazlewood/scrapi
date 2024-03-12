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

import scrapi.digest.Hasher;
import scrapi.impl.jca.JcaTemplate;
import scrapi.key.SecretKey;
import scrapi.util.Assert;

import javax.crypto.Mac;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.Provider;

class JcaMacDigester extends AbstractDataConsumer<Hasher> implements Hasher {

    public static final String JCA_KEY_NOT_NULL = SecretKey.class.getName() + " toJcaKey() value cannot be null.";

    private final Mac MAC;

    JcaMacDigester(String id, Provider provider, SecretKey<?> key) {
        Assert.notNull(key, "SecretKey cannot be null.");
        javax.crypto.SecretKey jcaKey = Assert.notNull(key.toJcaKey(), JCA_KEY_NOT_NULL);
        this.MAC = new JcaTemplate(id, provider).withMac(mac -> {
            mac.init(jcaKey);
            return mac;
        });
    }

    @Override
    protected void doApply(byte input) {
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
    public byte[] get() {
        return this.MAC.doFinal();
    }

    @Override
    public boolean test(byte[] bytes) {
        return MessageDigest.isEqual(get(), bytes); // constant time operation
    }
}
