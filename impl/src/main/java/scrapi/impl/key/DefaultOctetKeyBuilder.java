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
import scrapi.key.OctetSecretKey;
import scrapi.util.Bytes;

import javax.crypto.spec.SecretKeySpec;

public class DefaultOctetKeyBuilder
        extends AbstractKeyBuilder<OctetSecretKey, OctetSecretKey.Builder>
        implements OctetSecretKey.Builder {

    protected byte[] octets;

    public DefaultOctetKeyBuilder(String jcaName, int size) {
        super(jcaName, size);
    }

    @Override
    public OctetSecretKey.Builder octets(byte[] octets) {
        this.octets = octets;
        return this;
    }

//    public DefaultOctetKeyBuilder(String jcaName, int bitLength) {
//        this.JCA_NAME = Assert.hasText(jcaName, "jcaName cannot be null or empty.");
//        if (bitLength % Byte.SIZE != 0) {
//            String msg = "bitLength must be an even multiple of 8";
//            throw new IllegalArgumentException(msg);
//        }
//        this.BIT_LENGTH = Assert.gt(bitLength, 0, "bitLength must be > 0");
//    }

    @Override
    public OctetSecretKey build() {
        if (!Bytes.isEmpty(this.octets)) {
            SecretKeySpec spec = new SecretKeySpec(this.octets, this.jcaName);
            return new DefaultOctetSecretKey(spec);
        }

        // otherwise, no octets configured, so generate a new random key:
        JcaTemplate template = new JcaTemplate(this.jcaName, this.provider, this.random);
//        javax.crypto.SecretKey jcaKey;
//        if (this.BIT_LENGTH > 0) {
//            jcaKey = template.generateSecretKey(this.BIT_LENGTH);
//            return new AbstractSymmetricKey(jcaKey, this.BIT_LENGTH);
//        }
//        // else, alg name implies associated length:
        javax.crypto.SecretKey jcaKey = template.generateSecretKey();
        return new DefaultOctetSecretKey(jcaKey);
    }
}
