package scrapi.key;

public interface PasswordGenerator extends KeyGenerator<Password, PasswordGenerator> {

    PasswordGenerator alphabet(char[] alphabet);

    PasswordGenerator length(int numCharacters);
}
