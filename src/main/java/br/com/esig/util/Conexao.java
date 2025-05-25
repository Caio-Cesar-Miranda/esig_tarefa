package br.com.esig.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/gestao_esig";
    private static final String USUARIO = "esig_user";
    private static final String SENHA = "esig_senha123";


    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL não encontrado.", e);
        }
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
