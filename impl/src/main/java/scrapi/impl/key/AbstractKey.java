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

import scrapi.impl.lang.NoOpCleanable;
import scrapi.key.ConfidentialKey;
import scrapi.key.Key;
import scrapi.key.KeyException;
import scrapi.util.Assert;
import scrapi.util.Bytes;
import scrapi.util.Strings;

import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;
import java.lang.ref.Cleaner;
import java.lang.ref.Reference;

abstract class AbstractKey<K extends java.security.Key> implements Key<K>, scrapi.lang.Destroyable, AutoCloseable {

    private static final String SUNPKCS11_GENERIC_SECRET_CLASSNAME = "sun.security.pkcs11.P11Key$P11SecretKey";
    private static final String SUNPKCS11_GENERIC_SECRET_ALGNAME = "Generic Secret"; // https://github.com/openjdk/jdk/blob/4f90abaf17716493bad740dcef76d49f16d69379/src/jdk.crypto.cryptoki/share/classes/sun/security/pkcs11/P11KeyStore.java#L1292

    static final Cleaner cleaner = Cleaner.create();

    protected final K key;
    private transient boolean destroyed;
    private final Cleaner.Cleanable cleanable;

    protected AbstractKey(final K key) {
        this.key = Assert.notNull(key, "Key cannot be null.");
        this.cleanable = this instanceof ConfidentialKey<?> ? // private or secret key:
                cleaner.register(this, new KeyDestroyer(key)) :
                // nothing to clean on public keys:
                NoOpCleanable.INSTANCE;
    }

    @Override
    public K toJcaKey() {
        return this.key;
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

    static Integer findBitLength(java.security.Key key) {

        Integer bitlen = null;

        // try to parse the length from key specification
        if (key instanceof javax.crypto.SecretKey secretKey) {
            if ("RAW".equals(secretKey.getFormat())) {
                byte[] encoded = findEncoded(secretKey);
                if (!Bytes.isEmpty(encoded)) {
                    bitlen = (int) Bytes.bitLength(encoded);
                    Bytes.clear(encoded);
                }
            }
        } else if (key instanceof java.security.interfaces.RSAKey rsaKey) {
            bitlen = rsaKey.getModulus().bitLength();
        } else if (key instanceof java.security.interfaces.ECKey ecKey) {
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

    private record KeyDestroyer(java.security.Key key) implements Runnable {
        @Override
        public void run() {
            if (!(key instanceof Destroyable dkey)) return;
            try {
                dkey.destroy();
            } catch (DestroyFailedException e) {
                String msg = "Unable to destroy internal JCA key of type " + this.key.getClass().getName() + ": " +
                        e.getMessage();
                throw new KeyException(msg, e);
            }
        }
    }

    @Override
    public void destroy() {
        try {
            if (!this.destroyed) {
                this.destroyed = true;
                this.cleanable.clean();
            }
        } finally {
            Reference.reachabilityFence(this);
        }
    }

    @Override
    public boolean isDestroyed() {
        return this.destroyed;
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    public void close() {
        destroy();
    }
}
