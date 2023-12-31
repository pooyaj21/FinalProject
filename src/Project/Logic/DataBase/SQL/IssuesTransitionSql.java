package Project.Logic.DataBase.SQL;

import Project.Logic.Issue;
import Project.Logic.IssuesTransition;
import Project.Logic.Status;
import Project.Util.EnumChanger;

import java.sql.*;
import java.util.ArrayList;

public class IssuesTransitionSql {
    static IssuesTransitionSql instance;
    private IssuesTransitionSql() {
    }
    public static IssuesTransitionSql getInstance(){
        if (instance==null){
            return instance = new IssuesTransitionSql();
        }else return instance;
    }

    private Connection getConnection() throws SQLException {
        return SqlConnection.getConnection();
    }

    public void createIssueTransition(int issueId, Status previousState, Status newState, long transitionTime) {
        try (Connection connection = getConnection()) {
            String insertQuery = "INSERT INTO IssueTransitions (issue_id, previous_state, new_state, transition_time) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, issueId);
                preparedStatement.setString(2, previousState.toString());
                preparedStatement.setString(3, newState.toString());
                preparedStatement.setLong(4, transitionTime);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Issue transition recorded successfully.");
                } else {
                    throw new IllegalArgumentException("Failed to record issue transition.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<IssuesTransition> getAllIssueTransitions() {
        ArrayList<IssuesTransition> transitions = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM IssueTransitions";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        IssuesTransition transition = new IssuesTransition(
                                resultSet.getInt("transition_id"),
                                resultSet.getInt("issue_id"),
                                EnumChanger.toEnumStatus(resultSet.getString("previous_state")),
                                EnumChanger.toEnumStatus(resultSet.getString("new_state")),
                                resultSet.getLong("transition_time")
                        );

                        transitions.add(transition);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transitions;
    }

    public ArrayList<IssuesTransition> getIssueTransitionsBetweenTimestamps(long startTime, long endTime) {
        ArrayList<IssuesTransition> transitions = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM IssueTransitions WHERE transition_time BETWEEN ? AND ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setLong(1, startTime);
                preparedStatement.setLong(2, endTime);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        IssuesTransition transition = new IssuesTransition(
                                resultSet.getInt("transition_id"),
                                resultSet.getInt("issue_id"),
                                EnumChanger.toEnumStatus(resultSet.getString("previous_state")),
                                EnumChanger.toEnumStatus(resultSet.getString("new_state")),
                                resultSet.getLong("transition_time")
                        );

                        transitions.add(transition);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transitions;
    }

    public ArrayList<Issue> getIssuesWithStatusTransition(int projectId, int userId, String previousStatus, String newStatus) {
        ArrayList<Issue> filteredIssues = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM Issues WHERE project_id = ? AND user_id = ? AND issue_status = ? AND issue_id IN (SELECT issue_id FROM IssueTransitions WHERE previous_state = ? AND new_state = ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setInt(2, userId);
                preparedStatement.setString(3, newStatus);
                preparedStatement.setString(4, previousStatus);
                preparedStatement.setString(5, newStatus);

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

                        filteredIssues.add(issue);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filteredIssues;
    }

    public ArrayList<Integer> getTransitionIdsWithStatusTransition(int projectId, int userId, String previousStatus, String newStatus) {
        ArrayList<Integer> transitionIds = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT transition_id FROM IssueTransitions WHERE issue_id IN (SELECT issue_id FROM Issues WHERE project_id = ? AND user_id = ? ) AND previous_state = ? AND new_state = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setInt(2, userId);
                preparedStatement.setString(3, previousStatus);
                preparedStatement.setString(4, newStatus);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int transitionId = resultSet.getInt("transition_id");
                        transitionIds.add(transitionId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transitionIds;
    }

    public ArrayList<Issue> getIssuesWithStatusAndTransitionTime(int projectId, int userId, String oldStatus, String newStatus, long startTime, long endTime) {
        ArrayList<Issue> filteredIssues = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String query = "SELECT i.* FROM Issues i " +
                    "INNER JOIN IssueTransitions it ON i.issue_id = it.issue_id " +
                    "WHERE i.project_id = ? AND it.previous_state = ? AND it.new_state = ? " +
                    "AND it.transition_time BETWEEN ? AND ? AND i.user_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, projectId);
                preparedStatement.setString(2, oldStatus);
                preparedStatement.setString(3, newStatus);
                preparedStatement.setLong(4, startTime);
                preparedStatement.setLong(5, endTime);
                preparedStatement.setInt(6, userId);

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
                        filteredIssues.add(issue);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filteredIssues;
    }



}
