package scrapi.impl.key;

import scrapi.key.Password;
import scrapi.util.Assert;
import scrapi.util.Randoms;
import scrapi.util.Strings;

import java.security.Provider;
import java.security.SecureRandom;
import java.util.Arrays;

public class DefaultPasswordGenerator implements Password.Generator {

    private static final char[] DEFAULT_ALPHABET =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_+={}[]|\\;:\"<>,./?"
                    .toCharArray();

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int DEFAULT_PASSWORD_LENGTH = 16;
    private static final String PASSWORD_LENGTH_MSG = "password length must be >= " + MIN_PASSWORD_LENGTH;
    private SecureRandom random;
    private char[] alphabet;
    private int length;

    public DefaultPasswordGenerator() {
        this.alphabet = DEFAULT_ALPHABET;
        this.length = DEFAULT_PASSWORD_LENGTH;
        this.random = Randoms.secureRandom();
    }

    private char[] randomPassword() {
        final char[] password = new char[this.length];
        for (int i = 0; i < password.length; i++) {
            int index = this.random.nextInt(this.alphabet.length);
            password[i] = this.alphabet[index];
        }
        return password;
    }

    @Override
    public Password.Generator provider(Provider provider) {
        // no-op, not used in this implementation
        return this;
    }

    @Override
    public Password.Generator random(SecureRandom random) {
        this.random = random != null ? random : Randoms.secureRandom(); // always assume a default
        return this;
    }

    @Override
    public Password.Generator alphabet(char[] alphabet) {
        this.alphabet = Assert.notEmpty(alphabet, "alphabet must not be null or empty.");
        return this;
    }

    @Override
    public Password.Generator length(int numCharacters) {
        this.length = Assert.gte(numCharacters, MIN_PASSWORD_LENGTH, PASSWORD_LENGTH_MSG);
        return this;
    }

    @Override
    public Password get() {
        char[] password = Strings.EMPTY_CHARS;
        try {
            password = randomPassword();
            return new DefaultPassword(password);
        } finally {
            Arrays.fill(password, '\0');
        }
    }
}
