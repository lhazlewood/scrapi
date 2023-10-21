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
