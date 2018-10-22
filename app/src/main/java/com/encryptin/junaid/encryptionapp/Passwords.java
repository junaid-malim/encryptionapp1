package com.encryptin.junaid.encryptionapp;

import java.security.SecureRandom;
import java.util.Random;

public class Passwords {

    private static final Random RANDOM = new SecureRandom();

    protected   Passwords() { }

    public String generateRandomPassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int c = RANDOM.nextInt(62);
            if (c <= 9) {
                sb.append(String.valueOf(c));
            } else if (c < 36) {
                sb.append((char) ('a' + c - 10));
            } else {
                sb.append((char) ('A' + c - 36));
            }
        }
        return sb.toString();
    }


}