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

import scrapi.impl.lang.IdentifiableRegistry;
import scrapi.msg.HmacAlgorithm;
import scrapi.msg.PbeMacAlgorithm;
import scrapi.util.Collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public final class DefaultPbeMacAlgorithmRegistry extends IdentifiableRegistry<String, PbeMacAlgorithm> {

    private static final int PREFIX_LENGTH = "hmac".length();

    // ------------------------------------------------------------------------------------------------
    // https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#mac-algorithms
    // ------------------------------------------------------------------------------------------------
    private static List<PbeMacAlgorithm> createAlgs() {
        Collection<HmacAlgorithm> hmacs = HmacAlgorithm.registry().values();
        List<PbeMacAlgorithm> pbeMacs = new ArrayList<>(hmacs.size());
        for (HmacAlgorithm hmac : hmacs) {
            // Standard PBEWith* and HmacPBE* algs only support SHA1 and SHA2 family algorithms, so if we've
            // encountered anything other than those families, skip:
            String id = hmac.id().toLowerCase(Locale.US);
            if (id.startsWith("md5", PREFIX_LENGTH) || id.startsWith("sha3-", PREFIX_LENGTH)) continue;

            pbeMacs.add(new DefaultPbeMacAlgorithm(hmac));
        }
        return Collections.immutable(pbeMacs);
    }

    public DefaultPbeMacAlgorithmRegistry() {
        super("PBE Mac Algorithm", createAlgs());
    }
}