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

import scrapi.Destroyable;
import scrapi.impl.jca.JcaTemplate;
import scrapi.key.OctetSecretKey;
import scrapi.util.Assert;
import scrapi.util.Bytes;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DefaultOctetSecretKeyBuilder extends AbstractKeyBuilder<OctetSecretKey, OctetSecretKey.Builder>
        implements OctetSecretKey.Builder, Destroyable {

    private byte[] octets;

    public DefaultOctetSecretKeyBuilder(String jcaName, int minSize) {
        super(jcaName, minSize);
    }

    @Override
    public OctetSecretKey.Builder octets(byte[] octets) {
        this.octets = Assert.notEmpty(octets, "octets cannot be null or empty.");
        return self();
    }

    @Override
    public OctetSecretKey build() {

        if (!Bytes.isEmpty(this.octets)) {
            if (this.size != 0) {
                throw new IllegalStateException("Both size and octets cannot be specified.");
            }
            return new DefaultOctetSecretKey(new SecretKeySpec(this.octets, this.jcaName));
        }

        // otherwise need to generate:
        JcaTemplate template = new JcaTemplate(this.jcaName, this.provider, this.random);

        SecretKey jcaKey = this.size > 0 ?
                template.generateSecretKey(this.size) :
                template.generateSecretKey();

        return new DefaultOctetSecretKey(jcaKey);
    }

    @Override
    public void destroy() {
        Bytes.clear(this.octets);
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }
}
