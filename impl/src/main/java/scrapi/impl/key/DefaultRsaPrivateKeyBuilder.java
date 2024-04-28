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
package scrapi.impl.key;

import scrapi.impl.jca.JcaTemplate;
import scrapi.key.RsaPrimeFactor;
import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;
import scrapi.util.Assert;
import scrapi.util.Collections;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAMultiPrimePrivateCrtKeySpec;
import java.security.spec.RSAOtherPrimeInfo;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@SuppressWarnings("unused") // used reflectively by RsaPrivateKey.builder()
public class DefaultRsaPrivateKeyBuilder
        extends AbstractRsaKeyBuilder<RsaPrivateKey, RsaPrivateKey.Builder>
        implements RsaPrivateKey.Builder {

    private static final String RSA_PUB_TYPE_MSG = "RSA PublicKey must be a " + RSAKey.class.getName() + " instance.";

    private static final String pName = "'p' (first factor)";
    private static final String qName = "'q' (second factor)";
    private static final String dPName = "'dP' (first factor CRT exponent)";
    private static final String dQName = "'dQ' (second factor CRT exponent)";
    private static final String qInvName = "'qInv' (first CRT coefficient)";

    private static final Collection<String> ALL_NAMES = Collections.setOf(pName, qName, dPName, dQName, qInvName);

    private RsaPublicKey publicKey;
    private BigInteger privateExponent;
    private BigInteger prime1;
    private BigInteger prime2;
    private BigInteger exponent1;
    private BigInteger exponent2;
    private BigInteger coefficient;

    private final Set<String> crtFieldsChanged = new LinkedHashSet<>();
    private final List<RsaPrimeFactor> factors = new ArrayList<>();

    public DefaultRsaPrivateKeyBuilder() {
    }

    @Override
    public RsaPrivateKey.Builder publicKey(RsaPublicKey publicKey) {
        Assert.notNull(publicKey, "publicKey cannot be null");
        BigInteger pubExp = Assert.notNull(publicKey.publicExponent(), "RsaPublicKey publicExponent cannot be null");
        modulus(publicKey.modulus());
        publicExponent(pubExp);
        this.publicKey = publicKey;
        return self();
    }

    @Override
    public RsaPrivateKey.Builder privateExponent(BigInteger privateExponent) {
        this.privateExponent = Assert.notNull(privateExponent, "Private exponent cannot be null.");
        return self();
    }

    @Override
    public RsaPrivateKey.Builder prime1(BigInteger prime1) {
        this.prime1 = Assert.notNull(prime1, "prime1 cannot be null.");
        this.crtFieldsChanged.add(pName);
        return self();
    }

    @Override
    public RsaPrivateKey.Builder prime2(BigInteger prime2) {
        this.prime2 = Assert.notNull(prime2, "prime2 cannot be null.");
        this.crtFieldsChanged.add(qName);
        return self();
    }

    @Override
    public RsaPrivateKey.Builder exponent1(BigInteger exponent1) {
        this.exponent1 = Assert.notNull(exponent1, "exponent1 cannot be null.");
        this.crtFieldsChanged.add(dPName);
        return self();
    }

    @Override
    public RsaPrivateKey.Builder exponent2(BigInteger exponent2) {
        this.exponent2 = Assert.notNull(exponent2, "exponent2 cannot be null.");
        this.crtFieldsChanged.add(dQName);
        return self();
    }

    @Override
    public RsaPrivateKey.Builder coefficient(BigInteger coefficient) {
        this.coefficient = Assert.notNull(coefficient, "coefficient cannot be null.");
        this.crtFieldsChanged.add(qInvName);
        return self();
    }

    @Override
    public RsaPrivateKey.Builder add(RsaPrimeFactor factor) {
        Assert.notNull(factor, "factor cannot be null.");
        factors.add(factor);
        return self();
    }

    @Override
    public RsaPrivateKey.Builder add(Consumer<RsaPrimeFactor.Mutator<?>> c) {
        Assert.notNull(c, "Consumer cannot be null.");
        RsaPrimeFactor.Builder b = RsaPrimeFactor.builder();
        c.accept(b);
        RsaPrimeFactor factor = b.build();
        return add(factor);
    }

    private static boolean isCrt(PrivateKey key) {
        return key instanceof RSAPrivateCrtKey || key instanceof RSAMultiPrimePrivateCrtKey;
    }

    private RsaPrivateKey generate() {
        KeyPair pair = new JcaTemplate("RSA").generateKeyPair(this.size);
        RSAPublicKey jcaPub = Assert.isInstance(RSAPublicKey.class, pair.getPublic(), RSA_PUB_TYPE_MSG);
        RsaPublicKey pub = new DefaultRsaPublicKey(jcaPub);
        PrivateKey jcaPriv = Assert.notNull(pair.getPrivate(), "RSA KeyPair private key cannot be null.");
        return new DefaultRsaPrivateKey(jcaPriv, pub);
    }

    @Override
    public RsaPrivateKey build() {

        if (this.size > 0) { // generation requested:
            return generate();
        }

        //TODO: if publicKey is set, it should be retained below instead of using KeyFactory:

        RSAPrivateKeySpec privSpec;

        if (crtFieldsChanged.isEmpty()) {
            // no crt fields configured, ensure that no additional prime factors have been specified either:
            if (!factors.isEmpty()) {
                String msg = "Additional Prime Factors may only be specified when also specifying first and " +
                        "second prime values " + ALL_NAMES;
                throw new IllegalStateException(msg);
            }
            // non-CRT RSA Private Key:
            privSpec = new RSAPrivateKeySpec(modulus, privateExponent);
        } else {
            if (crtFieldsChanged.size() != ALL_NAMES.size()) {
                Set<String> unset = new LinkedHashSet<>(ALL_NAMES);
                unset.removeAll(crtFieldsChanged);
                String msg = "First and Second prime factor values [" +
                        crtFieldsChanged + "] have been specified, but the remaining required values [" +
                        unset + "] have not been specified. All " + ALL_NAMES.size() + " values are required when " +
                        "creating CRT RSA private keys.";
                throw new IllegalStateException(msg);
            }
            if (factors.isEmpty()) {
                privSpec = new RSAPrivateCrtKeySpec(modulus, publicExponent, privateExponent,
                        prime1, prime2, exponent1, exponent2, coefficient);
            } else {
                RSAOtherPrimeInfo[] infos = new RSAOtherPrimeInfo[factors.size()];
                for (RsaPrimeFactor f : factors) {
                    RSAOtherPrimeInfo info = new RSAOtherPrimeInfo(f.prime(), f.exponent(), f.coefficient());
                }
                privSpec = new RSAMultiPrimePrivateCrtKeySpec(modulus, publicExponent, privateExponent,
                        prime1, prime2, exponent1, exponent2, coefficient, infos);
            }
        }

        return new JcaTemplate("RSA", this.provider).withKeyFactory(f -> {

            RSAPublicKeySpec pubSpec;

            RSAPrivateKey priv = Assert.isInstance(RSAPrivateKey.class, f.generatePrivate(privSpec),
                    "RSA KeyFactory Provider must return an instance of RSAPrivateKey.");

            if (priv instanceof RSAPrivateCrtKey || priv instanceof RSAMultiPrimePrivateCrtKey) {
                // public exponent already available from the private key, use it directly:
                BigInteger pubExp = priv instanceof RSAPrivateCrtKey ?
                        ((RSAPrivateCrtKey) priv).getPublicExponent() :
                        ((RSAMultiPrimePrivateCrtKey) priv).getPublicExponent();
                pubSpec = new RSAPublicKeySpec(modulus, pubExp);
            } else {
                //otherwise non-CRT key, use specified value to derive the public key:
                pubSpec = new RSAPublicKeySpec(modulus, publicExponent);
            }

            RSAPublicKey jcaPub = Assert.isInstance(RSAPublicKey.class, f.generatePublic(pubSpec),
                    "RSA KeyFactory Provider must return an instance of RSAPublicKey.");
            RsaPublicKey pub = new DefaultRsaPublicKey(jcaPub);

            return isCrt(priv) ? new DefaultCrtRsaPrivateKey(priv, pub) : new DefaultRsaPrivateKey(priv, pub);
        });
    }
}
