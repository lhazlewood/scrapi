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

import scrapi.key.Key;
import scrapi.util.Assert;
import scrapi.util.Bytes;
import scrapi.util.Strings;

import java.util.Optional;

abstract class AbstractKey<K extends java.security.Key> implements Key<K> {

    private static final String SUNPKCS11_GENERIC_SECRET_CLASSNAME = "sun.security.pkcs11.P11Key$P11SecretKey";
    private static final String SUNPKCS11_GENERIC_SECRET_ALGNAME = "Generic Secret"; // https://github.com/openjdk/jdk/blob/4f90abaf17716493bad740dcef76d49f16d69379/src/jdk.crypto.cryptoki/share/classes/sun/security/pkcs11/P11KeyStore.java#L1292

    protected final K key;
    private final Integer bitLength;

    protected AbstractKey(K key) {
        this.key = Assert.notNull(key, "Key cannot be null.");
        this.bitLength = findBitLength(key);
    }

    protected AbstractKey(K key, int bitLength) {
        this.key = Assert.notNull(key, "Key cannot be null.");
        this.bitLength = bitLength;
    }

    @Override
    public K toJcaKey() {
        return this.key;
    }

    @Override
    public Optional<Integer> bitLength() {
        return Optional.ofNullable(bitLength);
    }

    static String findAlgorithm(java.security.Key key) {
        return key != null ? Strings.clean(key.getAlgorithm()) : null;
    }

    /**
     * Returns the specified key's available encoded bytes, or {@code null} if not available.
     *
     * <p>Some KeyStore implementations - like Hardware Security Modules, PKCS11 key stores, and later versions
     * of Android - will not allow applications or libraries to obtain a key's encoded bytes.  In these cases,
     * this method will return null.</p>
     *
     * @param key the key to inspect
     * @return the specified key's available encoded bytes, or {@code null} if not available.
     */
    public static byte[] findEncoded(java.security.Key key) {
        Assert.notNull(key, "Key cannot be null.");
        byte[] encoded = null;
        try {
            encoded = key.getEncoded();
        } catch (Throwable ignored) {
        }
        return encoded;
    }

    public static boolean isSunPkcs11GenericSecret(java.security.Key key) {
        return key instanceof javax.crypto.SecretKey &&
                key.getClass().getName().equals(SUNPKCS11_GENERIC_SECRET_CLASSNAME) &&
                SUNPKCS11_GENERIC_SECRET_ALGNAME.equals(key.getAlgorithm());
    }

    private static Integer findBitLength(java.security.Key key) {

        Integer bitlen = null;

        // try to parse the length from key specification
        if (key instanceof javax.crypto.SecretKey) {
            javax.crypto.SecretKey secretKey = (javax.crypto.SecretKey) key;
            if ("RAW".equals(secretKey.getFormat())) {
                byte[] encoded = findEncoded(secretKey);
                if (!Bytes.isEmpty(encoded)) {
                    bitlen = Integer.valueOf((int) Bytes.bitLength(encoded));
                    Bytes.clear(encoded);
                }
            }
        } else if (key instanceof java.security.interfaces.RSAKey) {
            java.security.interfaces.RSAKey rsaKey = (java.security.interfaces.RSAKey) key;
            bitlen = rsaKey.getModulus().bitLength();
        } else if (key instanceof java.security.interfaces.ECKey) {
            java.security.interfaces.ECKey ecKey = (java.security.interfaces.ECKey) key;
            bitlen = ecKey.getParams().getOrder().bitLength();
        }
        // TODO:
//        else {
//            // We can check additional logic for EdwardsCurve even if the current JDK version doesn't support it:
//            EdwardsCurve curve = EdwardsCurve.findByKey(key);
//            if (curve != null) bitlen = curve.getKeyBitLength();
//        }

        return bitlen;
    }


}
