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

import scrapi.key.KeyGenerator;
import scrapi.key.PrivateKey;
import scrapi.key.PublicKey;
import scrapi.msg.SignatureAlgorithm;
import scrapi.msg.Signer;
import scrapi.msg.Verifier;
import scrapi.util.Assert;

import java.security.Provider;
import java.security.SecureRandom;
import java.util.function.Supplier;

public class DefaultSignatureAlgorithm<U extends PublicKey<?>, R extends PrivateKey<?, U>, G extends KeyGenerator<R, G>>
        extends AbstractIntegrityAlgorithm<SignatureAlgorithm<U, R, G>>
        implements SignatureAlgorithm<U, R, G> {

    protected final SecureRandom RANDOM;

    private final Supplier<G> SUPPLIER;

    DefaultSignatureAlgorithm(String id, Provider provider, SecureRandom random, int bitLength, Supplier<G> genSupplier) {
        super(id, provider, bitLength);
        this.RANDOM = random;
        this.SUPPLIER = Assert.notNull(genSupplier, "KeyGenerator supplier cannot be null.");
    }

    @Override
    public Signer key(R privateKey) {
        return new JcaSignatureSupport.JcaSigner(this.ID, this.PROVIDER, this.RANDOM, privateKey);
    }

    @Override
    public Verifier<?> key(U publicKey) {
        return new JcaSignatureSupport.JcaVerifier(this.ID, this.PROVIDER, publicKey);
    }

    @Override
    public SignatureAlgorithm<U, R, G> provider(Provider provider) {
        return new DefaultSignatureAlgorithm<>(this.ID, provider, this.RANDOM, this.BITLEN, this.SUPPLIER);
    }

    @Override
    public SignatureAlgorithm<U, R, G> random(SecureRandom random) {
        return new DefaultSignatureAlgorithm<>(this.ID, this.PROVIDER, random, this.BITLEN, this.SUPPLIER);
    }

    @Override
    public G keygen() {
        return Assert.notNull(this.SUPPLIER.get(), "KeyGenerator suppler cannot produce null generators.");
    }
}
