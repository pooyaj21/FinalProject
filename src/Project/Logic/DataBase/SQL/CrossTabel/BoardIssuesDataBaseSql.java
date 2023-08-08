package Project.Logic.DataBase.SQL.CrossTabel;

import Project.Logic.Board;
import Project.Logic.DataBase.SQL.UserDataBaseSQL;
import Project.Logic.Issue;
import Project.Util.EnumChanger;

import java.sql.*;
import java.util.ArrayList;

public class BoardIssuesDataBaseSql {
    String url = "jdbc:mysql://localhost:3306/FinalProject";
    String username = "root";
    String password = "pooya1234";
    static BoardIssuesDataBaseSql instance;

    private BoardIssuesDataBaseSql() {
    }

    public static BoardIssuesDataBaseSql getInstance() {
        if (instance == null) {
            return instance = new BoardIssuesDataBaseSql();
        } else return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
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

    public void removeIssueFromBoard(int issueId, int boardId) {
        try (Connection connection = getConnection()) {
            String deleteQuery = "DELETE FROM Board_Issues WHERE issue_id = ? AND board_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, issueId);
                preparedStatement.setInt(2, boardId);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Issue removed from board successfully.");
                } else {
                    throw new IllegalArgumentException("Issue with ID not found on board: " + issueId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Board> getAllBoardsWithIssue(int issueId) {
        ArrayList<Board> boards = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT Board.* FROM Board " +
                    "INNER JOIN Board_Issues ON Board.board_id = Board_Issues.board_id " +
                    "WHERE Board_Issues.issue_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, issueId);

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

    public void removeBoardAndAssociations(int boardId) {
        try (Connection connection = getConnection()) {
            String deleteQuery = "DELETE FROM Board_Issues WHERE board_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, boardId);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Associations removed from Board_Issues table for the board.");
                } else {
                    throw new IllegalArgumentException("Board with ID not found: " + boardId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeIssueFromAllBoards(int issueId) {
        try (Connection connection = getConnection()) {
            String deleteQuery = "DELETE FROM Board_Issues WHERE issue_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, issueId);
                int rowsAffected = preparedStatement.executeUpdate();

                System.out.println(rowsAffected + " associations removed from Board_Issues table for the issue.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Issue> getAllIssuesOfBoard(int boardId) {
        ArrayList<Issue> issues = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM Issues WHERE issue_id IN (SELECT issue_id FROM Board_Issues WHERE board_id = ?)";

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
}
