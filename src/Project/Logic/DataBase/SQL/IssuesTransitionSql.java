package Project.Logic.DataBase.SQL;

import Project.Logic.IssuesTransition;
import Project.Logic.Status;
import Project.Util.EnumChanger;

import java.sql.*;
import java.util.ArrayList;

public class IssuesTransitionSql {
    String url = "jdbc:mysql://localhost:3306/FinalProject";
    String username = "root";
    String password = "pooya1234";
    static IssuesTransitionSql instance;
    private IssuesTransitionSql() {
    }
    public static IssuesTransitionSql getInstance(){
        if (instance==null){
            return instance = new IssuesTransitionSql();
        }else return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
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


}
