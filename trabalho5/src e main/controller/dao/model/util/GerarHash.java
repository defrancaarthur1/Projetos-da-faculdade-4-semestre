package br.com.trabalho2Usuario.util;

public class GerarHash {

    public static void main(String[] args) {

        String senha = "trabalho5_senha";

        String hash = SenhaUtil.gerarHash(senha);

        System.out.println("Senha: " + senha);
        System.out.println("Hash: " + hash);
    }
}
