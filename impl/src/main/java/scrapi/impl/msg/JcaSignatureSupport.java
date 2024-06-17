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
import scrapi.key.AsymmetricKey;
import scrapi.key.PrivateKey;
import scrapi.key.PublicKey;
import scrapi.lang.CheckedRunnable;
import scrapi.msg.MessageConsumer;
import scrapi.msg.MessageException;
import scrapi.msg.Signer;
import scrapi.msg.Verifier;
import scrapi.util.Assert;

import java.nio.ByteBuffer;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

abstract class JcaSignatureSupport<T extends MessageConsumer<T>, K extends AsymmetricKey<?>> extends AbstractMessageConsumer<T> {

    private static final String JCA_KEY_TYPE_MSG = "AsymmetricKey must be an instance of either " +
            PublicKey.class.getName() + " or " + PrivateKey.class.getName();

    protected final Signature SIG;

    protected JcaSignatureSupport(String id, Provider provider, final SecureRandom random, final K key) {
        Assert.notNull(key, "Signature or Verification key cannot be null.");
        if (!(key instanceof PublicKey || key instanceof PrivateKey)) {
            throw new IllegalArgumentException(JCA_KEY_TYPE_MSG);
        }
        this.SIG = new JcaTemplate(id, provider, random).withSignature(sig -> {
            if (key instanceof PrivateKey) {
                sig.initSign(((PrivateKey<?, ?>) key).toJcaKey(), random);
            } else {
                sig.initVerify(((PublicKey<?>) key).toJcaKey());
            }
            return sig;
        });
    }

    private void apply(CheckedRunnable r) {
        try {
            r.run();
        } catch (Throwable t) {
            String msg = "Unable to apply data to " + Signature.class.getName() + " instance: " + t.getMessage();
            throw new MessageException(msg, t);
        }
    }

    @Override
    protected void doApply(byte input) {
        apply(() -> this.SIG.update(input));
    }

    @Override
    protected void doApply(byte[] input) {
        apply(() -> this.SIG.update(input));
    }

    @Override
    protected void doApply(byte[] input, int offset, int len) {
        apply(() -> this.SIG.update(input, offset, len));
    }

    @Override
    protected void doApply(ByteBuffer input) {
        apply(() -> this.SIG.update(input));
    }

    static class JcaSigner extends JcaSignatureSupport<Signer, PrivateKey<?, ?>> implements Signer {

        JcaSigner(String id, Provider provider, SecureRandom random, PrivateKey<?, ?> key) {
            super(id, provider, random, key);
        }

        @Override
        public byte[] get() {
            try {
                return this.SIG.sign();
            } catch (SignatureException e) {
                String msg = "Unable to sign data: " + e.getMessage();
                throw new MessageException(msg, e);
            }
        }
    }

    static class JcaVerifier extends JcaSignatureSupport<JcaVerifier, PublicKey<?>> implements Verifier<JcaVerifier> {
        JcaVerifier(String id, Provider provider, PublicKey<?> key) {
            super(id, provider, null, key);
        }

        @Override
        public boolean test(byte[] bytes) {
            try {
                return this.SIG.verify(bytes);
            } catch (SignatureException e) {
                String msg = "Unable to verify signature: " + e.getMessage();
                throw new MessageException(msg, e);
            }
        }
    }
}
