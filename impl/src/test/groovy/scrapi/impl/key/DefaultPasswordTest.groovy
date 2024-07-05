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
package scrapi.impl.key

import org.junit.jupiter.api.Test
import scrapi.key.Password

import static org.junit.jupiter.api.Assertions.*

class DefaultPasswordTest {

    private static final char[] JP_CHARS = '第十章 第五章 第九章 第七章 第四章 第六章. 第十二章 第十一章 第十八章 第十五章. ' +
            '.復讐者」 伯母さん . 第二章 第三章 第九章. 復讐者」 ' +
            '. 第十五章 第十八章 第十三章 第十七章 第十六章 第十九章. 復讐者」 伯母さん. ' +
            '第五章 第二章 第六章 第七章.'.toString().toCharArray() as char[]

    @Test
    void testToString() {
        // ensure no password chars are printed.  We just default to Object.toString() format:
        def pwd = new DefaultPassword(JP_CHARS)
        def prefix = DefaultPassword.class.getName() + "@"
        assertTrue pwd.toString().startsWith(prefix)
    }

    @Test
    void testPasswordOf() {
        def pwd = new DefaultPassword(JP_CHARS)
        def pwd2 = Password.of(JP_CHARS)
        assertTrue pwd2 instanceof DefaultPassword
        assertTrue pwd.equals(pwd2)
        assertEquals pwd.hashCode(), pwd2.hashCode()
        assertFalse pwd.equals(Password.of("fubar".toCharArray()))
    }
}
