package scrapi.key

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertSame

class KeyExceptionTest {

    @Test
    void message() {
        String msg = 'foo'
        assertEquals msg, new KeyException(msg).message

    }

    @Test
    void messageCause() {
        String msg = 'foo'
        def cause = new RuntimeException()
        def ex = new KeyException(msg, cause)
        assertEquals msg, ex.message
        assertSame cause, ex.cause
    }
}
