package Project.Logic.DataBase.SQL;

import Project.Logic.Role;
import Project.Logic.User;
import Project.Util.EnumChanger;

import java.sql.*;
import java.util.ArrayList;

public class UserDataBaseSQL {
    static UserDataBaseSQL instance;
    String url = "jdbc:mysql://localhost:3306/FinalProject";
    String username = "root";
    String password = "pooya1234";

    private UserDataBaseSQL() {
    }

    public static UserDataBaseSQL getInstance() {
        if (instance == null) {
            return instance = new UserDataBaseSQL();
        } else return instance;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public void addUser(String name, String email, String password, Role role) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, this.username, this.password);

            String insertQuery = "INSERT INTO User (user_name, user_email, user_password, user_role) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email.toLowerCase());
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, role.toString());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User added successfully.");
                } else {
                    throw new IllegalArgumentException("user didn't added");
                }
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void editUser(int userId, String name, String email, String password, Role role) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, this.username, this.password);

            String updateQuery = "UPDATE User SET user_name = ?, user_email = ?, user_password = ?, user_role = ? WHERE user_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                // Set the new values for the user
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email.toLowerCase());
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, role.toString());
                preparedStatement.setInt(5, userId); // Provide the user_id of the user to update

                // Execute the update statement
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User updated successfully.");
                } else {
                    System.out.println("User not found or not updated.");
                }
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, this.username, this.password);

            String deleteQuery = "DELETE FROM User WHERE user_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, userId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User deleted successfully.");
                } else {
                    System.out.println("User not found or not deleted.");
                }
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, this.username, this.password);

            String selectQuery = "SELECT * FROM User";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    String name = resultSet.getString("user_name");
                    String email = resultSet.getString("user_email");
                    String password = resultSet.getString("user_password");
                    String roleName = resultSet.getString("user_role");

                    Role role = Role.valueOf(roleName); // Assuming Role is an enum

                    User user = new User(userId, name, email, password, role);
                    users.add(user);
                }
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public boolean isEmailExist(String email) {
        boolean exists = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, this.username, this.password);

            String selectQuery = "SELECT COUNT(*) FROM User WHERE user_email = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, email);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        exists = count > 0;
                    }
                }
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    public User getUserFromId(int userId) {
        User user = null;

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM User WHERE user_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User(
                                resultSet.getInt("user_id"),
                                resultSet.getString("user_name"),
                                resultSet.getString("user_email"),
                                resultSet.getString("user_password"),
                                EnumChanger.toEnumRole(resultSet.getString("user_role"))
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public int getUserIdByEmail(String userEmail) {
        int userId = -1;

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT user_id FROM User WHERE user_email = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, userEmail.toLowerCase());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userId = resultSet.getInt("user_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (userId == -1) throw new IllegalArgumentException("Email Didn't Found");
        else return userId;
    }

    public boolean doesEmailAndPasswordMatch(String userEmail, String userPassword) {
        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT COUNT(*) AS count FROM User WHERE user_email = ? AND user_password = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, userEmail);
                preparedStatement.setString(2, userPassword);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt("count");
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}


