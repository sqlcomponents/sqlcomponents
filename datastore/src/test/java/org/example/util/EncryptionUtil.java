package org.example.util;

public class EncryptionUtil {
    public static String enAnDecrypt(final String input) {
        return input == null ? null : new StringBuilder(input).reverse().toString();
    }
}
