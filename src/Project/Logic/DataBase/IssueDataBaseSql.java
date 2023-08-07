package Project.Logic.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IssueDataBaseSql {
    String url = "jdbc:mysql://localhost:3306/FinalProject";
    String username = "root";
    String password = "pooya1234";

    public static void main(String[] args) {
        BoardDataBaseSql boardDataBaseSql =new BoardDataBaseSql();

    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void createIssue(int projectId, int boardId, int userId, String title, long addTime, long updateTime, String type, String priority, String status) {
        try (Connection connection = getConnection()) {
            String insertQuery = "INSERT INTO Issues (project_id, board_id, user_id, issue_title, issue_addTime, issue_updateTime, issue_type, issue_priority, issue_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setInt(2, boardId);
                preparedStatement.setInt(3, userId);
                preparedStatement.setString(4, title);
                preparedStatement.setLong(5, addTime);
                preparedStatement.setLong(6, updateTime);
                preparedStatement.setString(7, type);
                preparedStatement.setString(8, priority);
                preparedStatement.setString(9, status);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Issue created successfully.");
                } else {
                    throw new IllegalArgumentException("Issue creation failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Edit issue details
    public void editIssue(int issueId, String newTitle, long newUpdateTime, String newType, String newPriority, String newStatus) {
        try (Connection connection = getConnection()) {
            String updateQuery = "UPDATE Issues SET issue_title = ?, issue_updateTime = ?, issue_type = ?, issue_priority = ?, issue_status = ? WHERE issue_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newTitle);
                preparedStatement.setLong(2, newUpdateTime);
                preparedStatement.setString(3, newType);
                preparedStatement.setString(4, newPriority);
                preparedStatement.setString(5, newStatus);
                preparedStatement.setInt(6, issueId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Issue updated successfully.");
                } else {
                    throw new IllegalArgumentException("Issue update failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an issue
    public void deleteIssue(int issueId) {
        try (Connection connection = getConnection()) {
            String deleteQuery = "DELETE FROM Issues WHERE issue_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, issueId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Issue deleted successfully.");
                } else {
                    throw new IllegalArgumentException("Issue deletion failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Add Issue to Board
    public void assignIssueToBoard(int boardId, int issueId) {
        try (Connection connection = getConnection()) {
            String insertQuery = "INSERT INTO Board_Issues (board_id, issue_id) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, boardId);
                preparedStatement.setInt(2, issueId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Issue assigned to board successfully.");
                } else {
                    throw new IllegalArgumentException("Failed to assign issue to board.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
