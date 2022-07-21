package server_utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class PasswordHashGenerator {
    private static MessageDigest algorithm;

    private PasswordHashGenerator() {

    }

    public static void setNewAlgorithm(String name) {
        try {
            algorithm = MessageDigest.getInstance(name);
        } catch (NoSuchAlgorithmException e) {
            setDefaultAlgorithm();
        }
    }

    private static void setDefaultAlgorithm() {
        try {
            algorithm = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ignored) {
            // никогда не возникнет
        }
    }

    public static String generateHash(String password) {
        if(Optional.ofNullable(algorithm).isEmpty())
            setDefaultAlgorithm();
        return new String(algorithm.digest(password.getBytes(StandardCharsets.UTF_8)));
    }
}
