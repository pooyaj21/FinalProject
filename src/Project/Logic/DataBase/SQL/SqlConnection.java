package Project.Logic.DataBase.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
//    private final static String url = "jdbc:mysql://localhost:3306/FinalProject";
//    private final static String username = "root";
//    private final static String password = "pooya1234";
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(url, username, password);
//    }

    private static final String DATABASE_URL = "jdbc:sqlite:DB/FinalProject.db";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }
}
