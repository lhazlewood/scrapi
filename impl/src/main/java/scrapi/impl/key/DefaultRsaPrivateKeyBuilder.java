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
import scrapi.impl.util.Parameter;
import scrapi.key.RsaPrimeFactor;
import scrapi.key.RsaPrivateKey;
import scrapi.key.RsaPublicKey;
import scrapi.util.Assert;
import scrapi.util.Collections;

import java.math.BigInteger;
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

    private static final Collection<Parameter<BigInteger>> ALL_CRT_PARAMS = Collections.setOf(
            DefaultCrtRsaPrivateKey.P,
            DefaultCrtRsaPrivateKey.Q,
            DefaultCrtRsaPrivateKey.DP,
            DefaultCrtRsaPrivateKey.DQ,
            DefaultCrtRsaPrivateKey.QINV);

    private RsaPublicKey publicKey;
    private BigInteger privateExponent;
    private BigInteger firstFactor;
    private BigInteger secondFactor;
    private BigInteger firstFactorCrtExponent;
    private BigInteger secondFactorCrtExponent;
    private BigInteger firstCrtCoefficient;

    private final Set<Parameter<BigInteger>> crtParamsChanged = new LinkedHashSet<>();
    private final List<RsaPrimeFactor> factors = new ArrayList<>();

    public DefaultRsaPrivateKeyBuilder() {
    }

    @Override
    public RsaPrivateKey.Builder publicKey(RsaPublicKey publicKey) {
        Assert.notNull(publicKey, "publicKey cannot be null");
        modulus(publicKey.modulus());
        publicExponent(publicKey.publicExponent());
        this.publicKey = publicKey;
        return self();
    }

    @Override
    public RsaPrivateKey.Builder privateExponent(BigInteger privateExponent) {
        this.privateExponent = DefaultRsaPrivateKey.D.check(privateExponent);
        return self();
    }

    @Override
    public RsaPrivateKey.Builder firstFactor(BigInteger firstFactor) {
        Parameter<BigInteger> param = DefaultCrtRsaPrivateKey.P;
        this.firstFactor = param.check(firstFactor);
        this.crtParamsChanged.add(param);
        return self();
    }

    @Override
    public RsaPrivateKey.Builder secondFactor(BigInteger secondFactor) {
        Parameter<BigInteger> param = DefaultCrtRsaPrivateKey.Q;
        this.secondFactor = param.check(secondFactor);
        this.crtParamsChanged.add(param);
        return self();
    }

    @Override
    public RsaPrivateKey.Builder firstFactorExponent(BigInteger firstFactorExponent) {
        Parameter<BigInteger> param = DefaultCrtRsaPrivateKey.DP;
        this.firstFactorCrtExponent = param.check(firstFactorExponent);
        this.crtParamsChanged.add(param);
        return self();
    }

    @Override
    public RsaPrivateKey.Builder secondFactorExponent(BigInteger secondFactorExponent) {
        Parameter<BigInteger> param = DefaultCrtRsaPrivateKey.DQ;
        this.secondFactorCrtExponent = param.check(secondFactorExponent);
        this.crtParamsChanged.add(param);
        return self();
    }

    @Override
    public RsaPrivateKey.Builder firstFactorCoefficient(BigInteger firstFactorCoefficient) {
        Parameter<BigInteger> param = DefaultCrtRsaPrivateKey.QINV;
        this.firstCrtCoefficient = param.check(firstFactorCoefficient);
        this.crtParamsChanged.add(param);
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
        RsaPrimeFactor factor = b.get();
        return add(factor);
    }

    private static boolean isCrt(PrivateKey key) {
        return key instanceof RSAPrivateCrtKey || key instanceof RSAMultiPrimePrivateCrtKey;
    }

    private boolean hasFieldState() {
        return this.modulus != null || this.publicExponent != null || this.publicKey != null ||
                this.privateExponent != null || !this.crtParamsChanged.isEmpty() || !factors.isEmpty();
    }

    @Override
    public RsaPrivateKey get() {

        //TODO: if publicKey is set, it should be retained below instead of using KeyBuilder:

        RSAPrivateKeySpec privSpec;

        if (crtParamsChanged.isEmpty()) {
            // no crt fields configured, ensure that no additional prime factors have been specified either:
            if (!factors.isEmpty()) {
                String msg = "Additional Prime Factors may only be specified when also specifying first and " +
                        "second prime values " + ALL_CRT_PARAMS;
                throw new IllegalStateException(msg);
            }
            // non-CRT RSA Private Key:
            privSpec = new RSAPrivateKeySpec(modulus, privateExponent);
        } else {
            if (crtParamsChanged.size() != ALL_CRT_PARAMS.size()) {
                Set<Parameter<BigInteger>> unset = new LinkedHashSet<>(ALL_CRT_PARAMS);
                unset.removeAll(crtParamsChanged);
                String msg = "First and Second prime factor values [" +
                        crtParamsChanged + "] have been specified, but the remaining required values [" + unset +
                        "] have not been specified. All " + ALL_CRT_PARAMS.size() + " values are required when " +
                        "creating CRT RSA private keys.";
                throw new IllegalStateException(msg);
            }
            if (factors.isEmpty()) {
                privSpec = new RSAPrivateCrtKeySpec(modulus, publicExponent, privateExponent,
                        firstFactor, secondFactor, firstFactorCrtExponent, secondFactorCrtExponent, firstCrtCoefficient);
            } else {
                RSAOtherPrimeInfo[] infos = new RSAOtherPrimeInfo[factors.size()];
                for (RsaPrimeFactor f : factors) {
                    RSAOtherPrimeInfo info = new RSAOtherPrimeInfo(f.prime(), f.exponent(), f.coefficient());
                }
                privSpec = new RSAMultiPrimePrivateCrtKeySpec(modulus, publicExponent, privateExponent,
                        firstFactor, secondFactor, firstFactorCrtExponent, secondFactorCrtExponent, firstCrtCoefficient, infos);
            }
        }

        return new JcaTemplate(this.jcaName, this.provider).withKeyFactory(f -> {

            RSAPublicKeySpec pubSpec;

            RSAPrivateKey priv = Assert.isInstance(RSAPrivateKey.class, f.generatePrivate(privSpec),
                    "RSA Key builder Provider must return an instance of RSAPrivateKey.");

            if (isCrt(priv)) {
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
                    "RSA Key builder Provider must return an instance of RSAPublicKey.");
            RsaPublicKey pub = new DefaultRsaPublicKey(jcaPub);

            return isCrt(priv) ? new DefaultCrtRsaPrivateKey(priv, pub) : new DefaultRsaPrivateKey(priv, pub);
        });
    }
}
