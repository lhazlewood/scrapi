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
