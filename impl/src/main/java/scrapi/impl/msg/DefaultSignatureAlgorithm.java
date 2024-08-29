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
import scrapi.msg.UnarySignatureAlgorithm;
import scrapi.msg.Verifier;
import scrapi.util.Assert;

import java.security.Provider;
import java.util.function.Consumer;
import java.util.function.Supplier;

class DefaultSignatureAlgorithm<
        S extends PrivateKey<?, V>,
        V extends PublicKey<?>,
        G extends KeyGenerator<S, G>>
        extends AbstractAlgorithm
        implements UnarySignatureAlgorithm<S, V, DefaultSignerBuilder<S>, DefaultVerifierBuilder<V>, G> {

    private final Supplier<G> SUPPLIER;

    DefaultSignatureAlgorithm(String id, Provider provider, Supplier<G> genSupplier) {
        super(id, provider);
        this.SUPPLIER = Assert.notNull(genSupplier, "KeyGenerator supplier cannot be null.");
    }

    @Override
    public Signer with(Consumer<DefaultSignerBuilder<S>> c) {
        DefaultSignerBuilder<S> builder = new DefaultSignerBuilder<S>(this.ID).provider(this.PROVIDER);
        c.accept(builder);
        return builder.get();
    }

    @Override
    public Verifier verifier(Consumer<DefaultVerifierBuilder<V>> c) {
        DefaultVerifierBuilder<V> builder = new DefaultVerifierBuilder<V>(this.ID).provider(this.PROVIDER);
        c.accept(builder);
        return builder.get();
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
