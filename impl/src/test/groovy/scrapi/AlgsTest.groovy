package scrapi

import org.junit.jupiter.api.Test

class AlgsTest {

    @SuppressWarnings(['GroovyAccessibility', 'GroovyResultOfObjectAllocationIgnored'])
    @Test
    void privateCtors() {
        new Algs()
        new Algs.Hash()
        new Algs.Mac()
        new Algs.Sig()
    }
}
