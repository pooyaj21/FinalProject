package Project.Logic.DataBase.SQL;

import Project.Logic.Board;
import Project.Logic.DataBase.SQL.CrossTabel.BoardIssuesDataBaseSql;
import Project.Logic.Issue;
import Project.Util.EnumChanger;

import java.sql.*;
import java.util.ArrayList;

public class BoardDataBaseSql {
    String url = "jdbc:mysql://localhost:3306/FinalProject";
    String username = "root";
    String password = "pooya1234";
    static BoardDataBaseSql instance;
    private BoardDataBaseSql() {
    }
    public static BoardDataBaseSql getInstance(){
        if (instance==null){
            return instance = new BoardDataBaseSql();
        }else return instance;
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
                    BoardIssuesDataBaseSql.getInstance().removeBoardAndAssociations(boardId);
                    System.out.println("Board deleted successfully.");
                } else {
                    throw new IllegalArgumentException("Board deletion failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Issue> getAllIssuesOfBoard(int boardId) {
        ArrayList<Issue> issues = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM Issues WHERE board_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, boardId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Issue issue = new Issue(
                                resultSet.getInt("issue_id"),
                                resultSet.getInt("project_id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("issue_title"),
                                resultSet.getLong("issue_addTime"),
                                resultSet.getLong("issue_updateTime"),
                                EnumChanger.toEnumType(resultSet.getString("issue_type")),
                                EnumChanger.toEnumPriority(resultSet.getString("issue_priority")),
                                EnumChanger.toEnumStatus(resultSet.getString("issue_status"))
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

    public ArrayList<Board> getAllBoardsOfProject(int projectId) {
        ArrayList<Board> boards = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM Board WHERE project_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, projectId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Board board = new Board(
                                resultSet.getInt("board_id"),
                                resultSet.getInt("project_id"),
                                resultSet.getString("board_name")
                        );

                        boards.add(board);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return boards;
    }
}
