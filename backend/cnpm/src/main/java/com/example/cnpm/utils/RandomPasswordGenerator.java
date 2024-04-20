package com.example.cnpm.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomPasswordGenerator {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>])(?=.*[^\\da-zA-Z]).{6,}$";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+{};:,<.>";

    public String generateRandomPassword() {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);

        Random random = new Random();
        StringBuilder passwordBuilder = new StringBuilder();

        do {
            passwordBuilder.setLength(0);

            for (int i = 0; i < 10; i++) {
                int index = random.nextInt(CHARACTERS.length());
                passwordBuilder.append(CHARACTERS.charAt(index));
            }
        } while (!matchesPattern(passwordBuilder.toString(), pattern));

        return passwordBuilder.toString();
    }

    private boolean matchesPattern(String password, Pattern pattern) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
