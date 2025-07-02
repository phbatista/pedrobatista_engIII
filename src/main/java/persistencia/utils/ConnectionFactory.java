package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/db_esiii";
    private static final String USER = "postgres";
    private static final String PASSWORD = "2003"; // Use sua senha

    public static Connection getConnection() {
        try {
            // ADICIONE ESTA LINHA:
            Class.forName("org.postgresql.Driver");

            // Tenta estabelecer a conexão
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) { // Adicione ClassNotFoundException ao catch
            // Em caso de falha, lança uma exceção
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
}