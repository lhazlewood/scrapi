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
import scrapi.msg.Signer;

import java.security.Provider;
import java.security.SecureRandom;
import java.security.SignatureException;

class DefaultSigner extends AbstractSignatureConsumer<PrivateKey<?, ?>, Signer> implements Signer {

    DefaultSigner(String id, Provider provider, SecureRandom random, PrivateKey<?, ?> key) {
        super(id, provider, random, key);
    }

    @Override
    public byte[] get() {
        try {
            return this.SIG.sign();
        } catch (SignatureException e) {
            String msg = "Unable to produce signature: " + e.getMessage();
            throw new MessageException(msg, e);
        }
    }
}
