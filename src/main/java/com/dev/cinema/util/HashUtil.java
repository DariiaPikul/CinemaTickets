package com.dev.cinema.util;

import com.dev.cinema.exceptions.PasswordHashingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String HASHING_ALGORITHM = "SHA-512";
    private static final String MESSAGE =
            "Password hashing failed. Unable to find the chosen algorithm: " + HASHING_ALGORITHM;

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPassword = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte element : digest) {
                hashedPassword.append(String.format("%02x", element));
            }
        } catch (NoSuchAlgorithmException exception) {
            throw new PasswordHashingException(MESSAGE, exception);
        }
        return hashedPassword.toString();
    }
}
