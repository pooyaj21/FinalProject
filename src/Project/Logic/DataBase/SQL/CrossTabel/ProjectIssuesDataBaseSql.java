package Project.Logic.DataBase.SQL.CrossTabel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectIssuesDataBaseSql {
    String url = "jdbc:mysql://localhost:3306/FinalProject";
    String username = "root";
    String password = "pooya1234";
    ProjectIssuesDataBaseSql instance;

    private ProjectIssuesDataBaseSql() {
    }

    public ProjectIssuesDataBaseSql getInstance() {
        if (instance == null) {
            return instance = new ProjectIssuesDataBaseSql();
        } else return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // Assign an issue to a project
    public void assignIssueToProject(int projectId, int issueId) {
        try (Connection connection = getConnection()) {
            String insertQuery = "INSERT INTO Project_Issues (project_id, issue_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setInt(2, issueId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Issue assigned to project successfully.");
                } else {
                    throw new IllegalArgumentException("Failed to assign issue to project.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
