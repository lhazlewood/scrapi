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
package scrapi.impl.alg

import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import scrapi.alg.Algs
import scrapi.msg.HashAlgs

import java.security.Provider
import java.security.Security

class AlgsTest {

    @SuppressWarnings(['GroovyAccessibility', 'GroovyResultOfObjectAllocationIgnored'])
    @Test
    void privateCtors() {
        new Algs()
        new HashAlgs()
        new Algs.Mac()
        new Algs.Sig()
    }

    @Test()
    @Disabled
    // not needed during build, only for manual or IDE convenience
    void listServices() {
        Security.addProvider(new BouncyCastleProvider())
        def providers = Security.getProviders()
        def services = providers.collectMany { it.getServices() }
        services = services.findAll { s -> s.type.equals('Mac') /*&& !s.algorithm.startsWith('Ssl')*/ }
        services.eachWithIndex { Provider.Service service, int i ->
            println "${i + 1}: $service"
        }
    }
}