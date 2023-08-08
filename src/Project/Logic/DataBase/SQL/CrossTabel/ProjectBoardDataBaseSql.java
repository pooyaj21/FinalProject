package Project.Logic.DataBase.SQL.CrossTabel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProjectBoardDataBaseSql {
    String url = "jdbc:mysql://localhost:3306/FinalProject";
    String username = "root";
    String password = "pooya1234";
    static ProjectBoardDataBaseSql instance;

    private ProjectBoardDataBaseSql() {
    }

    public static ProjectBoardDataBaseSql getInstance() {
        if (instance == null) {
            return instance = new ProjectBoardDataBaseSql();
        } else return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
