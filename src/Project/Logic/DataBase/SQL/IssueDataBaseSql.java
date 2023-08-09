package Project.Logic.DataBase.SQL;

import Project.Logic.DataBase.SQL.CrossTabel.BoardIssuesDataBaseSql;
import Project.Logic.Issue;
import Project.Util.EnumChanger;

import java.sql.*;
import java.util.ArrayList;

public class IssueDataBaseSql {
    static IssueDataBaseSql instance;
    private IssueDataBaseSql() {
    }
    public static IssueDataBaseSql getInstance(){
        if (instance==null){
            return instance = new IssueDataBaseSql();
        }else return instance;
    }

    private Connection getConnection() throws SQLException {
        return SqlConnection.getConnection();
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

    public void createIssue(Issue issue, int projectId) {
        try (Connection connection = getConnection()) {
            String insertQuery = "INSERT INTO Issues (project_id, user_id, issue_title, issue_addTime, issue_updateTime, issue_type, issue_priority, issue_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, projectId);

                // Set user_id only if the user is not null
                if (issue.getUser() != null) {
                    preparedStatement.setInt(2, issue.getUser().getId());
                } else {
                    preparedStatement.setNull(2, java.sql.Types.INTEGER);
                }

                preparedStatement.setString(3, issue.getDescription());
                preparedStatement.setLong(4, issue.getAddTime());
                preparedStatement.setLong(5, issue.getLastUpdateTime());
                preparedStatement.setString(6, issue.getType().toString());
                preparedStatement.setString(7, issue.getPriority().toString());
                preparedStatement.setString(8, issue.getStatus().toString());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Issue created successfully and assigned to project and board.");
                } else {
                    throw new IllegalArgumentException("Issue creation failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Edit issue details
    public void editIssue(Issue issue) {
        try (Connection connection = getConnection()) {
            String updateQuery = "UPDATE Issues SET project_id = ?, user_id = ?, issue_title = ?, issue_addTime = ?, issue_updateTime = ?, issue_type = ?, issue_priority = ?, issue_status = ? WHERE issue_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, issue.getProjectId());

                // Set user_id only if the user is not null
                if (issue.getUser() != null) {
                    preparedStatement.setInt(2, issue.getUser().getId());
                } else {
                    preparedStatement.setNull(2, java.sql.Types.INTEGER);
                }

                preparedStatement.setString(3, issue.getDescription());
                preparedStatement.setLong(4, issue.getAddTime());
                preparedStatement.setLong(5, issue.getLastUpdateTime());
                preparedStatement.setString(6, issue.getType().toString());
                preparedStatement.setString(7, issue.getPriority().toString());
                preparedStatement.setString(8, issue.getStatus().toString());
                preparedStatement.setInt(9, issue.getId());

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
    //Delete Issue
    public void removeIssue(int issueId) {
        try (Connection connection = getConnection()) {
            String deleteQuery = "DELETE FROM Issues WHERE issue_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, issueId);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    BoardIssuesDataBaseSql.getInstance().removeIssueFromAllBoards(issueId);
                    System.out.println("Issue removed successfully.");
                } else {
                    throw new IllegalArgumentException("Issue with ID not found: " + issueId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Issue> getIssuesOfProjectAndUser(int projectId, int userId) {
        ArrayList<Issue> issues = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM Issues WHERE project_id = ? AND user_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setInt(2, userId);

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

    public int countIssuesInStatusForProject(int projectId, String status) {
        int issueCount = 0;
        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT COUNT(*) FROM Issues WHERE project_id = ? AND issue_status = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setString(2, status);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        issueCount = resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return issueCount;
    }


}
