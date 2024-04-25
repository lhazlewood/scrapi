package scrapi.lang

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertSame
import static org.junit.jupiter.api.Assertions.fail

class CheckedSupplierTest {

    @Test
    void noThrow() {
        def val = 'hello'
        def cs = new CheckedSupplier() {
            @Override
            Object get() throws Throwable {
                return val
            }
        }
        assertSame val, cs.unchecked().get() // no exception
    }

    @Test
    void sneakyThrow() {

        def ex = new IOException("checked")

        def cs = new CheckedSupplier() {
            @Override
            Object get() throws Throwable {
                throw ex
            }
        }

        try {
            cs.unchecked().get()
            fail()
        } catch (IOException expected) {
            assertSame(ex, expected)
        }
    }
}
