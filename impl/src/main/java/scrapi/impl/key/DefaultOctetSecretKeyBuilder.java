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

import scrapi.alg.Size;
import scrapi.key.OctetSecretKey;
import scrapi.util.Assert;
import scrapi.util.Bytes;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DefaultOctetSecretKeyBuilder extends AbstractKeyFactory<OctetSecretKey, OctetSecretKey.Builder>
        implements OctetSecretKey.Builder {

    private transient byte[] octets;

    public DefaultOctetSecretKeyBuilder(String jcaName, Size minSize) {
        super(jcaName, minSize);
    }

    @Override
    public OctetSecretKey.Builder octets(byte[] octets) {
        this.octets = Assert.notEmpty(octets, "octets cannot be null or empty.");
        return self();
    }

    @Override
    public OctetSecretKey get() {
        byte[] octets = this.octets;
        if (Bytes.isEmpty(octets)) {
            String msg = "Octets cannot be null or empty.";
            throw new IllegalStateException(msg);
        }
        SecretKey jcaKey = new SecretKeySpec(octets, this.jcaName);
        return new DefaultOctetSecretKey(jcaKey);
    }
}
