package Project.Logic.DataBase.SQL.CrossTabel;

import Project.Logic.DataBase.SQL.SqlConnection;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Util.EnumChanger;

import java.sql.*;
import java.util.ArrayList;

public class UserProjectDataBaseSql {
    static UserProjectDataBaseSql instance;

    private UserProjectDataBaseSql() {
    }

    public static UserProjectDataBaseSql getInstance() {
        if (instance == null) {
            return instance = new UserProjectDataBaseSql();
        } else return instance;
    }

    private Connection getConnection() throws SQLException {
        return SqlConnection.getConnection();
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
    public ArrayList<Project> getAllProjectsOfUser(int userId) {
        ArrayList<Project> projects = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT Project.* FROM Project " +
                    "INNER JOIN User_Project ON Project.project_id = User_Project.project_id " +
                    "WHERE User_Project.user_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Project project = new Project(
                                resultSet.getInt("project_id"),
                                resultSet.getString("project_name"),
                                resultSet.getString("project_discription"),
                                resultSet.getLong("project_addTime")
                        );

                        projects.add(project);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }
    public ArrayList<User> getAllUsersOfProject(int projectId) {
        ArrayList<User> users = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM User " +
                    "INNER JOIN User_Project ON User.user_id = User_Project.user_id " +
                    "WHERE User_Project.project_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, projectId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User(
                                resultSet.getInt("user_id"),
                                resultSet.getString("user_name"),
                                resultSet.getString("user_email"),
                                resultSet.getString("user_password"),
                                EnumChanger.toEnumRole(resultSet.getString("user_role"))
                        );

                        users.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
