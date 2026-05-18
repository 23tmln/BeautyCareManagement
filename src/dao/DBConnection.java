package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
        "jdbc:sqlserver://localhost:1433;databaseName=beauty_care;encrypt=true;trustServerCertificate=true";

    private static final String USER = "sa";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {

        try {

            Connection conn =
                DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Ket noi SQL Server thanh cong");

            return conn;

        } catch (SQLException e) {

            System.out.println("Ket noi SQL Server that bai");
            e.printStackTrace();

            return null;
        }
    }
}