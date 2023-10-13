package com.esliph.todolist;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class Teste {
    public static String hashPassword(String password) {
        BCrypt.Hasher hasher = BCrypt.withDefaults();
        var result = hasher.hashToString(12, password.toCharArray());
        return result.toString();
    }

    public static boolean verifyPassword(String candidatePassword, String storedPassword) {
        BCrypt.Verifyer verifyer = BCrypt.verifyer();
        BCrypt.Result result = verifyer.verify(candidatePassword.toCharArray(), storedPassword);
        return result.verified;
    }

    public static void main(String[] args) {
        String password = "senha123";

        // Para criar o hash da senha
        String hashedPassword = hashPassword(password);

        // Simular a verificação da senha (normalmente, você recuperaria a senha do
        // banco de dados)
        String storedPassword = hashedPassword;

        // Verificar a senha
        boolean passwordMatch = verifyPassword(password, storedPassword);

        if (passwordMatch) {
            System.out.println("A senha é válida.");
        } else {
            System.out.println("A senha é inválida.");
        }
    }
}
