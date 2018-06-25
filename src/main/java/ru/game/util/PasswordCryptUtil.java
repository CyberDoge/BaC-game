package ru.game.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordCryptUtil {
    private static int workload = 12;

    public static String hashPassword(String passwordPlainText) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(passwordPlainText, salt);

        return (hashed_password);
    }

    public static boolean checkPassword(String passwordPlainText, String storedHash) {
        if (null == storedHash || !storedHash.startsWith("$2a$"))
            throw new IllegalArgumentException("Invalid hash provided for comparison");
        return BCrypt.checkpw(passwordPlainText, storedHash);
    }

    public static void setWorkload(int workload) {
        PasswordCryptUtil.workload = workload;
    }
}
