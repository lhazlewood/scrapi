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

import scrapi.Algs;
import scrapi.msg.HashAlgorithm;
import scrapi.msg.MacAlgorithm;
import scrapi.impl.lang.IdentifiableRegistry;
import scrapi.util.Collections;
import scrapi.util.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class StandardMacAlgorithms extends IdentifiableRegistry<String, MacAlgorithm<?, ?>> {

    private static String suffix(HashAlgorithm alg) {
        String id = alg.id();
        if (!id.startsWith("SHA3-")) {
            id = id.replace("-", Strings.EMPTY);
        }
        return id;
    }

    // ------------------------------------------------------------------------------------------------
    // https://docs.oracle.com/en/java/javase/21/docs/specs/security/standard-names.html#mac-algorithms
    // ------------------------------------------------------------------------------------------------
    private static List<MacAlgorithm<?, ?>> createAlgs() {
        Collection<HashAlgorithm> hashAlgs = Algs.Hash.get().values();
        List<MacAlgorithm<?, ?>> macs = new ArrayList<>(hashAlgs.size() * 3);
        for (HashAlgorithm hashAlg : hashAlgs) {

            if (Algs.Hash.MD2.equals(hashAlg)) continue; // no JCA standard hmac alg for this one

            String suffix = suffix(hashAlg);
            String id = "Hmac" + suffix;
            int bitLength = hashAlg.bitLength();
            macs.add(new DefaultMacAlgorithm(id, null, bitLength));

            // Standard PBEWith* and HmacPBE* algs only support SHA1 and SHA2 family algorithms, so if we've
            // encountered anything other than those families, skip:
            if (!hashAlg.id().startsWith("SHA-")) continue;

            int defaultIterations; // https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
            if (bitLength >= 512) {
                defaultIterations = 210_000;
            } else if (bitLength >= 384) {
                defaultIterations = 415_000;
            } else if (bitLength >= 256) {
                defaultIterations = 600_000;
            } else if (bitLength >= 224) {
                defaultIterations = 900_000;
            } else {
                defaultIterations = 1_300_000;
            }

            macs.add(new DefaultPbeMacAlgorithm("PBEWithHmac" + suffix, null, bitLength, defaultIterations));
            macs.add(new DefaultPbeMacAlgorithm("HmacPBE" + suffix, null, bitLength, defaultIterations));
        }
        return Collections.immutable(macs);
    }

    public StandardMacAlgorithms() {
        super("Mac Algorithm", createAlgs());
    }
}