package scrapi.key;

public interface PasswordStretcher<T extends PasswordStretcher<T>> extends Keyable<Password, T> {

    T salt(byte[] salt);

    T iterations(int iterations);
}
