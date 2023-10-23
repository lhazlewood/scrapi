/*
 * Copyright © 2023 Les Hazlewood
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
package scrapi

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertSame

class ScrapiExceptionTest {

    @Test
    void message() {
        String msg = 'foo'
        assertEquals msg, new ScrapiException(msg).message
    }

    @Test
    void messageCause() {
        String msg = 'foo'
        def cause = new RuntimeException()
        def ex = new ScrapiException(msg, cause)
        assertEquals msg, ex.message
        assertSame cause, ex.cause
    }
}
