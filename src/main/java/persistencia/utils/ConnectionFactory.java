package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/db_esiii?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "postgres";
    private static final String PASSWORD = "2003";

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
}