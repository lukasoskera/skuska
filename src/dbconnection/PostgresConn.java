package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConn {

    private final String url = "jdbc:postgresql://34.76.208.91:5432/pre_stredosk_db";
    private final String name = "postgres";
    private final String password = "admin";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, name, password);
            System.out.println("Pripojene na "+url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }





}
