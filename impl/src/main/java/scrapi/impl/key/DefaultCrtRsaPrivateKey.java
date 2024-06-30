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

import scrapi.key.CrtRsaPrivateKey;
import scrapi.key.RsaPrimeFactor;
import scrapi.key.RsaPublicKey;
import scrapi.util.Arrays;
import scrapi.util.Collections;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.List;

public class DefaultCrtRsaPrivateKey extends DefaultRsaPrivateKey implements CrtRsaPrivateKey {

    public static final String KEY_TYPE_MSG = "JCA CRT RSA PrivateKey instance must implement either " +
            RSAPrivateCrtKey.class.getName() + " or " + RSAMultiPrimePrivateCrtKey.class.getName() + ".";

    private final List<RsaPrimeFactor> factors;

    public DefaultCrtRsaPrivateKey(PrivateKey key, RsaPublicKey publicKey) {
        super(key, publicKey);
        if (key instanceof RSAPrivateCrtKey) {
            this.factors = Collections.emptyList();
        } else if (key instanceof RSAMultiPrimePrivateCrtKey) {
            RSAOtherPrimeInfo[] infos = ((RSAMultiPrimePrivateCrtKey) key).getOtherPrimeInfo();
            if (Arrays.isEmpty(infos)) {
                this.factors = Collections.emptyList();
            } else {
                List<RsaPrimeFactor> factors = new ArrayList<>(infos.length);
                for (RSAOtherPrimeInfo info : infos) {
                    RsaPrimeFactor f = new DefaultRsaPrimeFactor(info);
                    factors.add(f);
                }
                this.factors = Collections.immutable(factors);
            }
        } else {
            throw new IllegalArgumentException(KEY_TYPE_MSG);
        }
    }

    @Override
    public BigInteger firstFactor() {
        return key instanceof RSAPrivateCrtKey ?
                ((RSAPrivateCrtKey) key).getPrimeP() :
                ((RSAMultiPrimePrivateCrtKey) key).getPrimeP();
    }

    @Override
    public BigInteger secondFactor() {
        return key instanceof RSAPrivateCrtKey ?
                ((RSAPrivateCrtKey) key).getPrimeQ() :
                ((RSAMultiPrimePrivateCrtKey) key).getPrimeQ();
    }

    @Override
    public BigInteger firstFactorExponent() {
        return key instanceof RSAPrivateCrtKey ?
                ((RSAPrivateCrtKey) key).getPrimeExponentP() :
                ((RSAMultiPrimePrivateCrtKey) key).getPrimeExponentP();
    }

    @Override
    public BigInteger secondFactorExponent() {
        return key instanceof RSAPrivateCrtKey ?
                ((RSAPrivateCrtKey) key).getPrimeExponentQ() :
                ((RSAMultiPrimePrivateCrtKey) key).getPrimeExponentQ();
    }

    @Override
    public BigInteger firstFactorCoefficient() {
        return key instanceof RSAPrivateCrtKey ?
                ((RSAPrivateCrtKey) key).getCrtCoefficient() :
                ((RSAMultiPrimePrivateCrtKey) key).getCrtCoefficient();
    }

    @Override
    public List<RsaPrimeFactor> otherFactors() {
        return this.factors;
    }
}
