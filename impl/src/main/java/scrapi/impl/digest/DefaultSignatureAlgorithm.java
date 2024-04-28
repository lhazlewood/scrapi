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
import scrapi.key.KeyBuilder;
import scrapi.key.PrivateKey;
import scrapi.key.PublicKey;
import scrapi.util.Assert;

import java.security.Provider;
import java.security.SecureRandom;
import java.util.function.Supplier;

public class DefaultSignatureAlgorithm<U extends PublicKey<?>, R extends PrivateKey<?, U>, B extends KeyBuilder<R, B>>
        extends AbstractDigestAlgorithm<SignatureAlgorithm<U, R, B>>
        implements SignatureAlgorithm<U, R, B> {

    protected final SecureRandom RANDOM;

    private final Supplier<B> SUPPLIER;

    DefaultSignatureAlgorithm(String id, Provider provider, SecureRandom random, int bitLength, Supplier<B> genSupplier) {
        super(id, provider, bitLength);
        this.RANDOM = random;
        this.SUPPLIER = Assert.notNull(genSupplier, "KeyBuilderSupplier supplier cannot be null.");
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
    public SignatureAlgorithm<U, R, B> provider(Provider provider) {
        return new DefaultSignatureAlgorithm<>(this.ID, provider, this.RANDOM, this.BITLEN, this.SUPPLIER);
    }

    @Override
    public SignatureAlgorithm<U, R, B> random(SecureRandom random) {
        return new DefaultSignatureAlgorithm<>(this.ID, this.PROVIDER, random, this.BITLEN, this.SUPPLIER);
    }

    @Override
    public B key() {
        return Assert.notNull(this.SUPPLIER.get(), "Builder suppler cannot produce null builders.");
    }
}
