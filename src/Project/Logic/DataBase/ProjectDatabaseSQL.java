package Project.Logic.DataBase;

import Project.Logic.Issue;
import Project.Util.DateUtil;

import java.sql.*;
import java.util.ArrayList;

public class ProjectDatabaseSQL {
    String url = "jdbc:mysql://localhost:3306/FinalProject";
    String username = "root";
    String password = "pooya1234";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {
        ProjectDatabaseSQL projectDatabaseSQL = new ProjectDatabaseSQL();
    }
    public void createProject(String projectName, String description) {
        try (Connection connection = getConnection()) {
            String insertQuery = "INSERT INTO Project (project_name, project_discription, project_addTime) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, projectName);
                preparedStatement.setString(2, description);
                preparedStatement.setLong(3, DateUtil.timeOfNow());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Project created successfully.");
                } else {
                    throw new IllegalArgumentException("Project creation failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Edit project details
    public void editProject(int projectId, String newProjectName, String newDescription) {
        try (Connection connection = getConnection()) {
            String updateQuery = "UPDATE Project SET project_name = ?, project_discription = ? WHERE project_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newProjectName);
                preparedStatement.setString(2, newDescription);
                preparedStatement.setInt(3, projectId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Project updated successfully.");
                } else {
                    throw new IllegalArgumentException("Project update failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a project
    public void deleteProject(int projectId) {
        try (Connection connection = getConnection()) {
            String deleteQuery = "DELETE FROM Project WHERE project_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, projectId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Project deleted successfully.");
                } else {
                    throw new IllegalArgumentException("Project deletion failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add user to project
    public void addUserToProject(int projectId, int userId) {
        try (Connection connection = getConnection()) {
            String insertQuery = "INSERT INTO User_Project (project_id, user_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setInt(2, userId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User added to project successfully.");
                } else {
                    throw new IllegalArgumentException("Failed to add user to project.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remove user from project
    public void removeUserFromProject(int projectId, int userID) {
        try (Connection connection = getConnection()) {
            String deleteQuery = "DELETE FROM User_Project WHERE project_id = ? AND user_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setInt(2, userID);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User removed from project successfully.");
                } else {
                    throw new IllegalArgumentException("Failed to remove user from project.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Get all issues of a project
    public ArrayList<Issue> getAllIssuesOfProject(int projectId) {
        ArrayList<Issue> issues = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM Issues WHERE project_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, projectId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Issue issue = new Issue(
                                resultSet.getInt("issue_id"),
                                resultSet.getInt("project_id"),
                                resultSet.getInt("board_id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("issue_title"),
                                resultSet.getLong("issue_addTime"),
                                resultSet.getLong("issue_updateTime"),
                                resultSet.getString("issue_type"),
                                resultSet.getString("issue_priority"),
                                resultSet.getString("issue_status")
                        );

                        issues.add(issue);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return issues;
    }
}
