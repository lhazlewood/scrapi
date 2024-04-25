package scrapi.key;

public interface RsaPublicKey extends PublicKey<java.security.interfaces.RSAPublicKey>, RsaKey<java.security.interfaces.RSAPublicKey> {

    interface Builder extends Mutator<Builder>, KeyBuilder<RsaPublicKey, Builder> {
    }
}
