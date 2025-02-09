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
package scrapi.impl.msg

import org.junit.jupiter.api.Test
import scrapi.msg.PbeMacAlgorithm

import static org.junit.jupiter.api.Assertions.assertEquals

class PbeMacAlgorithmTest extends AbstractMacAlgorithmTest<PbeMacAlgorithm> {

    @Override
    protected Collection<PbeMacAlgorithm> algs() {
        return PbeMacAlgorithm.registry().values()
    }

    @Test
    void equality() {
        assertEquals PbeMacAlgorithm.registry(), new DefaultPbeMacAlgorithmRegistry()
    }

    @Test
    void count() {
        assertEquals 7, algs().size()
    }

    @SuppressWarnings('GrDeprecatedAPIUsage')
    @Test
    void ids() {
        assertEquals 'PBEWithHmacSHA1', PbeMacAlgorithm.PBEHS1.id()
        assertEquals 'PBEWithHmacSHA224', PbeMacAlgorithm.PBEHS224.id()
        assertEquals 'PBEWithHmacSHA256', PbeMacAlgorithm.PBEHS256.id()
        assertEquals 'PBEWithHmacSHA384', PbeMacAlgorithm.PBEHS384.id()
        assertEquals 'PBEWithHmacSHA512', PbeMacAlgorithm.PBEHS512.id()
        assertEquals 'PBEWithHmacSHA512/224', PbeMacAlgorithm.PBEHS512_224.id()
        assertEquals 'PBEWithHmacSHA512/256', PbeMacAlgorithm.PBEHS512_256.id()
//        assertEquals 'HmacPBESHA1', PbeMacAlgorithm.PKCS12HS1.id()
//        assertEquals 'HmacPBESHA224', PbeMacAlgorithm.PKCS12HS224.id()
//        assertEquals 'HmacPBESHA256', PbeMacAlgorithm.PKCS12HS256.id()
//        assertEquals 'HmacPBESHA384', PbeMacAlgorithm.PKCS12HS384.id()
//        assertEquals 'HmacPBESHA512', PbeMacAlgorithm.PKCS12HS512.id()
//        assertEquals 'HmacPBESHA512/224', PbeMacAlgorithm.PKCS12HS512_224.id()
//        assertEquals 'HmacPBESHA512/256', PbeMacAlgorithm.PKCS12HS512_256.id()
    }

    @Test
    void sizes() {
        assertEquals 160, PbeMacAlgorithm.PBEHS1.size().bits()
        assertEquals 224, PbeMacAlgorithm.PBEHS224.size().bits()
        assertEquals 256, PbeMacAlgorithm.PBEHS256.size().bits()
        assertEquals 384, PbeMacAlgorithm.PBEHS384.size().bits()
        assertEquals 512, PbeMacAlgorithm.PBEHS512.size().bits()
        assertEquals 224, PbeMacAlgorithm.PBEHS512_224.size().bits()
        assertEquals 256, PbeMacAlgorithm.PBEHS512_256.size().bits()
    }

}
