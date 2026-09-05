package br.com.trabalho2Usuario.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexao {
    private static final String URL =
            "jdbc:mysql://localhost:3306/trabalho2Usuario?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String USUARIO =
            System.getenv().getOrDefault("DB_USER", "trabalho2_app");
    private static final String SENHA =
            System.getenv().getOrDefault("DB_PASSWORD", "trabalho2_senha");

    private Conexao() {
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
