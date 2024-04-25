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
package scrapi.lang

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertSame
import static org.junit.jupiter.api.Assertions.fail

class CheckedFunctionTest {

    @Test
    void noThrow() {
        def a = 'input'
        def b = 'output'
        def f = new CheckedFunction() {
            @Override
            Object apply(Object o) throws Throwable {
                assertSame(a, o)
                return b
            }
        }
        assertSame b, f.unchecked().apply(a) // no exception
    }

    @Test
    void sneakyThrow() {

        def ex = new IOException("checked")

        def f = new CheckedFunction() {
            @Override
            Object apply(Object o) throws Throwable {
                throw ex
            }
        }

        try {
            f.unchecked().apply('whatever')
            fail()
        } catch (IOException expected) {
            assertSame(ex, expected)
        }
    }
}
