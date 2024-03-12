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

import scrapi.impl.jca.AbstractSecurityBuilder;
import scrapi.impl.jca.JcaTemplate;
import scrapi.key.OctetKey;

public class DefaultOctetKeyBuilder
        extends AbstractSecurityBuilder<OctetKey, OctetKey.Builder>
        implements OctetKey.Builder {

    //protected final int BIT_LENGTH;

    public DefaultOctetKeyBuilder(String jcaName) {
        super(jcaName);
        //this.BIT_LENGTH = 0;
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
    public OctetKey build() {
        JcaTemplate template = new JcaTemplate(this.jcaName, this.provider, this.random);
//        javax.crypto.SecretKey jcaKey;
//        if (this.BIT_LENGTH > 0) {
//            jcaKey = template.generateSecretKey(this.BIT_LENGTH);
//            return new AbstractSymmetricKey(jcaKey, this.BIT_LENGTH);
//        }
//        // else, alg name implies associated length:
        javax.crypto.SecretKey jcaKey = template.generateSecretKey();
        return new DefaultOctetKey(jcaKey);
    }
}
