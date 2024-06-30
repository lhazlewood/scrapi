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

import scrapi.impl.alg.AbstractAlgorithm;
import scrapi.key.KeyGenerator;
import scrapi.key.PrivateKey;
import scrapi.key.PublicKey;
import scrapi.msg.SignatureAlgorithm;
import scrapi.msg.Signer;
import scrapi.msg.SignerBuilder;
import scrapi.msg.Verifier;
import scrapi.msg.VerifierBuilder;
import scrapi.util.Assert;

import java.security.Provider;
import java.util.function.Supplier;

public class DefaultSignatureAlgorithm<
        U extends PublicKey<?>,
        R extends PrivateKey<?, U>,
        G extends KeyGenerator<R, G>>
        extends AbstractAlgorithm
        implements SignatureAlgorithm<U, R, Signer, Verifier, SignerBuilder<R>, VerifierBuilder<U>, G> {

    private final Supplier<G> SUPPLIER;

    DefaultSignatureAlgorithm(String id, Provider provider, Supplier<G> genSupplier) {
        super(id, provider);
        this.SUPPLIER = Assert.notNull(genSupplier, "KeyGenerator supplier cannot be null.");
    }

    @Override
    public SignerBuilder<R> digester() {
        return new DefaultSignerBuilder<R>(this.ID).provider(this.PROVIDER);
    }

    @Override
    public VerifierBuilder<U> verifier() {
        return new DefaultVerifierBuilder<U>(this.ID).provider(this.PROVIDER);
    }

    @Override
    public G keygen() {
        return Assert.notNull(this.SUPPLIER.get(), "KeyGenerator suppler cannot produce null generators.");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        return obj instanceof SignatureAlgorithm && super.equals(obj);
    }
}
