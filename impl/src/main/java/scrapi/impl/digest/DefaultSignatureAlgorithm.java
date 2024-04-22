
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
import scrapi.util.Assert;

import java.security.Provider;
import java.security.SecureRandom;
import java.util.function.Supplier;

public class DefaultSignatureAlgorithm<U extends PublicKey<?>, R extends PrivateKey<?, U>, KB extends PrivateKey.Builder<U, R, KB>>
        extends AbstractDigestAlgorithm<SignatureAlgorithm<U, R, KB>>
        implements SignatureAlgorithm<U, R, KB> {

    protected final SecureRandom RANDOM;

    private final Supplier<KB> BUILDER_FN;

    DefaultSignatureAlgorithm(String id, Provider provider, SecureRandom random, int bitLength, Supplier<KB> builderSupplier) {
        super(id, provider, bitLength);
        this.RANDOM = random;
        this.BUILDER_FN = Assert.notNull(builderSupplier, "Builder supplier cannot be null.");
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
    public SignatureAlgorithm<U, R, KB> provider(Provider provider) {
        return new DefaultSignatureAlgorithm<>(this.ID, provider, this.RANDOM, this.BITLEN, this.BUILDER_FN);
    }

    @Override
    public SignatureAlgorithm<U, R, KB> random(SecureRandom random) {
        return new DefaultSignatureAlgorithm<>(this.ID, this.PROVIDER, random, this.BITLEN, this.BUILDER_FN);
    }

    @Override
    public KB key() {
        return Assert.notNull(this.BUILDER_FN.get(), "Builder suppler cannot produce null builders.");
    }
}
