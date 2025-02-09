/*
 * Copyright © 2025 Les Hazlewood
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
import scrapi.msg.HashAlgorithm
import scrapi.msg.HmacAlgorithm

import static org.junit.jupiter.api.Assertions.assertEquals

class HmacAlgorithmTest extends AbstractMacAlgorithmTest<HmacAlgorithm> {

    protected Collection<HmacAlgorithm> algs() {
        return HmacAlgorithm.registry().values()
    }

    @Test
    void equality() {
        assertEquals HmacAlgorithm.registry(), new DefaultHmacAlgorithmRegistry()
    }

    @Test
    void count() {
        int expected = HashAlgorithm.registry().size() - 1 // no JDK standard hmac alg for MD2 hash alg
        assertEquals expected, HmacAlgorithm.registry().size()
    }

    @SuppressWarnings('GrDeprecatedAPIUsage')
    @Test
    void ids() {
        assertEquals 'HmacMD5', HmacAlgorithm.HMD5.id()
        assertEquals 'HmacSHA1', HmacAlgorithm.HS1.id()
        assertEquals 'HmacSHA224', HmacAlgorithm.HS224.id()
        assertEquals 'HmacSHA256', HmacAlgorithm.HS256.id()
        assertEquals 'HmacSHA384', HmacAlgorithm.HS384.id()
        assertEquals 'HmacSHA512', HmacAlgorithm.HS512.id()
        assertEquals 'HmacSHA512/224', HmacAlgorithm.HS512_224.id()
        assertEquals 'HmacSHA512/256', HmacAlgorithm.HS512_256.id()
        assertEquals 'HmacSHA3-224', HmacAlgorithm.HS3_224.id()
        assertEquals 'HmacSHA3-256', HmacAlgorithm.HS3_256.id()
        assertEquals 'HmacSHA3-384', HmacAlgorithm.HS3_384.id()
        assertEquals 'HmacSHA3-512', HmacAlgorithm.HS3_512.id()
    }

    @Test
    void sizes() {
        assertEquals 128, HmacAlgorithm.HMD5.size().bits()
        assertEquals 160, HmacAlgorithm.HS1.size().bits()
        assertEquals 224, HmacAlgorithm.HS224.size().bits()
        assertEquals 256, HmacAlgorithm.HS256.size().bits()
        assertEquals 384, HmacAlgorithm.HS384.size().bits()
        assertEquals 512, HmacAlgorithm.HS512.size().bits()
        assertEquals 224, HmacAlgorithm.HS512_224.size().bits()
        assertEquals 256, HmacAlgorithm.HS512_256.size().bits()
        assertEquals 224, HmacAlgorithm.HS3_224.size().bits()
        assertEquals 256, HmacAlgorithm.HS3_256.size().bits()
        assertEquals 384, HmacAlgorithm.HS3_384.size().bits()
        assertEquals 512, HmacAlgorithm.HS3_512.size().bits()
    }

}
