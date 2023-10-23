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
package scrapi.lang

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotEquals
import static org.junit.jupiter.api.Assertions.assertThrows

class DefaultPairTest {

    static DefaultPair pair

    @BeforeEach
    void initEach() {
        pair = new DefaultPair('a', 'b')
    }

    @Test
    void whenAIsNull() {
        def thrown = assertThrows(NullPointerException.class, () -> {
            return new DefaultPair(null, 'foo')
        })
        assertEquals 'First argument cannot be null.', thrown.getMessage()
    }

    @Test
    void whenBIsNull() {
        def thrown = assertThrows(NullPointerException.class, () -> {
            return new DefaultPair('foo', null)
        })
        assertEquals 'Second argument cannot be null.', thrown.getMessage()
    }

    @Test
    void newInstance() {
        assertEquals 'a', pair.getA()
        assertEquals 'b', pair.getB()
    }

    @Test
    void identityEquals() {
        assertEquals pair, pair
    }

    @Test
    void equals() {
        def another = new DefaultPair('a', 'b')
        assertEquals pair, another
    }

    @Test
    void typeNotEquals() {
        assertNotEquals pair, 1
    }

    @Test
    void bNotEquals() { // a values are equal, but not b values
        def ac = new DefaultPair('a', 'c')
        assertNotEquals pair, ac
    }

    @Test
    void aNotEquals() { // b values are equal, but not a values
        def cb = new DefaultPair('c', 'b')
        assertNotEquals pair, cb
    }

    @Test
    void testHashCode() {
        assertEquals Objects.hash('a', 'b'), pair.hashCode()
    }

    @Test
    void testToString() {
        assertEquals '{a: a, b: b}', pair.toString()
    }
}
