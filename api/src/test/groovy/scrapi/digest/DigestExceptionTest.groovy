package scrapi.digest

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertSame

class DigestExceptionTest {

    @Test
    void messageCause() {
        String msg = 'foo'
        def cause = new RuntimeException()
        def ex = new DigestException(msg, cause)
        assertEquals msg, ex.message
        assertSame cause, ex.cause
    }

}
