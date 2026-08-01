package com.thisispit.tems.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public final class PasswordUtil {

    private PasswordUtil() {
    }

    public static String hashPassword(String rawPassword) {
        return digest(rawPassword);
    }

    public static boolean matches(String rawPassword, String hashedPassword) {
        return digest(rawPassword).equals(hashedPassword);
    }

    private static String digest(String value) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashedBytes);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Unable to hash password", ex);
        }
    }
}