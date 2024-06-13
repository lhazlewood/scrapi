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

import scrapi.msg.Hasher;
import scrapi.impl.jca.JcaTemplate;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.Provider;

class JcaMessageDigester extends AbstractMessageConsumer<Hasher> implements Hasher {

    private final MessageDigest MD;

    JcaMessageDigester(String jcaName, Provider provider) {
        this.MD = new JcaTemplate(jcaName, provider).withMessageDigest(md -> md);
    }

    @Override
    protected void doApply(byte input) {
        this.MD.update(input);
    }

    @Override
    protected void doApply(byte[] input) {
        this.MD.update(input);
    }

    @Override
    protected void doApply(byte[] input, int offset, int len) {
        this.MD.update(input, offset, len);
    }

    @Override
    protected void doApply(ByteBuffer input) {
        this.MD.update(input);
    }

    @Override
    public byte[] get() {
        return this.MD.digest();
    }

    @Override
    public boolean test(byte[] bytes) {
        return MessageDigest.isEqual(get(), bytes);
    }
}
