package scrapi.impl.key;

import scrapi.key.PrivateKey;
import scrapi.key.PublicKey;
import scrapi.util.Assert;

abstract class AbstractPrivateKey<J extends java.security.PrivateKey, U extends PublicKey<?>>
        extends DestroyableKey<J>
        implements PrivateKey<J, U> {

    private final U publicKey;

    private static int assertSameLength(java.security.PrivateKey key, PublicKey<?> publicKey) {
        Integer privLen = AbstractKey.findBitLength(Assert.notNull(key, "JCA PrivateKey cannot be null."));
        Integer pubLen = Assert.notNull(publicKey, "PublicKey cannot be null.")
                .bitLength().orElseThrow(() -> new IllegalArgumentException("PublicKey bitLength cannot be null."));
        Assert.eq(privLen, pubLen, "JCA PrivateKey bitLength and PublicKey bitLengths must be the same.");
        return privLen;
    }

    protected AbstractPrivateKey(J key, U publicKey) {
        super(key, assertSameLength(key, publicKey));
        this.publicKey = publicKey; // non-null with non-null bitLength guaranteed in assertSameLength call
    }

    @Override
    public U publicKey() {
        return this.publicKey;
    }
}
