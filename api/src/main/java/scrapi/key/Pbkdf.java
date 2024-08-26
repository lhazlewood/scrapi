package scrapi.key;

import java.util.function.Supplier;

public interface Pbkdf<T extends Pbkdf<T>> extends PasswordStretcher<T>, Supplier<OctetSecretKey> {
}
