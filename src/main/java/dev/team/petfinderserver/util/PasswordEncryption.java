package dev.team.petfinderserver.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public final class PasswordEncryption {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encrypt(String password) {
        return encoder.encode(password);
    }

    public static boolean matches(String password, String encryptedPassword) {
        return encoder.matches(password, encryptedPassword);
    }
} 