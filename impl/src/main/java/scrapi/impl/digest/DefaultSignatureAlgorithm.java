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

import scrapi.digest.Digester;
import scrapi.digest.SignatureAlgorithm;
import scrapi.digest.Verifier;
import scrapi.key.PrivateKey;
import scrapi.key.PublicKey;

import java.security.Provider;
import java.security.SecureRandom;

public class DefaultSignatureAlgorithm<U extends PublicKey<?>, R extends PrivateKey<?, U>>
        extends AbstractDigestAlgorithm<SignatureAlgorithm<U, R>> implements SignatureAlgorithm<U, R> {

    protected final SecureRandom RANDOM;

    DefaultSignatureAlgorithm(String id, Provider provider, SecureRandom random, int bitLength) {
        super(id, provider, bitLength);
        this.RANDOM = random;
    }

    @Override
    public Digester<?> key(R privateKey) {
        return new JcaSignatureSupport.JcaSigner(this.ID, this.PROVIDER, this.RANDOM, privateKey);
    }

    @Override
    public Verifier<?> key(U publicKey) {
        return new JcaSignatureSupport.JcaVerifier(this.ID, this.PROVIDER, publicKey);
    }

    @Override
    public SignatureAlgorithm<U, R> provider(Provider provider) {
        return new DefaultSignatureAlgorithm<>(this.ID, provider, this.RANDOM, this.BITLEN);
    }

    @Override
    public SignatureAlgorithm<U, R> random(SecureRandom random) {
        return new DefaultSignatureAlgorithm<>(this.ID, this.PROVIDER, random, this.BITLEN);
    }
}
