package scrapi.lang

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertSame
import static org.junit.jupiter.api.Assertions.fail

class CheckedRunnableTest {

    @Test
    void noThrow() {
        def r = new CheckedRunnable() {
            @Override
            void run() throws Throwable {
            }
        }
        r.unchecked().run() // no exception
    }

    @Test
    void sneakyThrow() {

        def ex = new IOException("checked")

        def r = new CheckedRunnable() {
            @Override
            void run() throws Throwable {
                throw ex
            }
        }

        try {
            r.unchecked().run()
            fail()
        } catch (IOException expected) {
            assertSame(ex, expected)
        }
    }
}
