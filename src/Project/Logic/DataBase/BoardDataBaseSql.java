package Project.Logic.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardDataBaseSql {
    String url = "jdbc:mysql://localhost:3306/FinalProject";
    String username = "root";
    String password = "pooya1234";

    public static void main(String[] args) {
        BoardDataBaseSql boardDataBaseSql =new BoardDataBaseSql();

    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    public void createBoard(int projectId, String boardName) {
        try (Connection connection = getConnection()) {
            String insertQuery = "INSERT INTO Board (project_id, board_name) VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setString(2, boardName);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Board created successfully.");
                } else {
                    throw new IllegalArgumentException("Board creation failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Edit board details
    public void editBoard(int boardId, String newBoardName) {
        try (Connection connection = getConnection()) {
            String updateQuery = "UPDATE Board SET board_name = ? WHERE board_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newBoardName);
                preparedStatement.setInt(2, boardId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Board updated successfully.");
                } else {
                    throw new IllegalArgumentException("Board update failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a board
    public void deleteBoard(int boardId) {
        try (Connection connection = getConnection()) {
            String deleteQuery = "DELETE FROM Board WHERE board_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, boardId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Board deleted successfully.");
                } else {
                    throw new IllegalArgumentException("Board deletion failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
