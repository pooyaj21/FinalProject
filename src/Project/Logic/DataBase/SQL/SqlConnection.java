package Project.Logic.DataBase.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
    private final static String url = "jdbc:mysql://localhost:3306/FinalProject";
    private final static String username = "root";
    private final static String password = "pooya1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

//    private final static String url = "jdbc:sqlite:/path/to/your/database.db";
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(url);
//    }
}
