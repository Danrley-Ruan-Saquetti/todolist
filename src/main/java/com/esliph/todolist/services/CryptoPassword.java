package com.esliph.todolist.services;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class CryptoPassword {
    public static String hashPassword(String password) {
        var hasher = BCrypt.withDefaults();
        var result = hasher.hashToString(12, password.toCharArray());

        return result.toString();
    }

    public static boolean verifyPassword(String candidatePassword, String storedPassword) {
        var verifyer = BCrypt.verifyer();
        var result = verifyer.verify(candidatePassword.toCharArray(), storedPassword);

        return result.verified;
    }
}
