package Project.Logic.DataBase.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
//    private final static String url = "jdbc:mysql://85.190.22.134:3501/ngi";
//    private final static String username = "root";
//    private final static String password = "Pooya@6742";
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(url, username, password);
//    }

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static final String DATABASE_URL = "jdbc:sqlite:DB/FinalProject.db";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }
}
