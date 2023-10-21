package scrapi.lang

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class PairTest {

    @Test
    void testOf() {
        def pair = Pair.of('a', 'b')
        def expected = new DefaultPair('a', 'b')
        assertEquals expected, pair
    }
}
