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

import scrapi.key.PrivateKey;
import scrapi.msg.MessageException;
import scrapi.msg.Signature;
import scrapi.msg.SignatureAlgorithm;
import scrapi.msg.Signer;
import scrapi.util.Assert;

import java.security.Provider;
import java.security.SecureRandom;
import java.security.SignatureException;

class DefaultSigner<A extends SignatureAlgorithm<?, ?, ?, ?, ?, A>>
        extends AbstractSignatureConsumer<PrivateKey<?, ?>, Signer<A>>
        implements Signer<A> {

    protected final A alg;

    DefaultSigner(A alg, Provider provider, SecureRandom random, PrivateKey<?, ?> key) {
        super(Assert.notNull(alg, "alg must not be null.").id(), provider, random, key);
        this.alg = alg;
    }

    @Override
    public Signature<A> get() {
        try {
            return new DefaultSignature<>(this.alg, this.SIG.sign());
        } catch (SignatureException e) {
            String msg = "Unable to produce signature: " + e.getMessage();
            throw new MessageException(msg, e);
        }
    }

    private static class DefaultSignature<A extends SignatureAlgorithm<?, ?, ?, ?, ?, A>> extends DefaultDigest<A> implements Signature<A> {
        DefaultSignature(A algorithm, byte[] octets) {
            super(algorithm, octets);
        }
    }
}
