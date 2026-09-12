package br.com.trabalho2Usuario.util;

import org.mindrot.jbcrypt.BCrypt;

public class SenhaUtil {

    public static String gerarHash(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt(12));
    }

    public static boolean verificar(String senhaDigitada, String hashArmazenado) {
        return BCrypt.checkpw(senhaDigitada, hashArmazenado);
    }
}
