package Project.Logic.DataBase.SQL;

import Project.Logic.Board;
import Project.Logic.Project;
import Project.Util.DateUtil;


import java.sql.*;
import java.util.ArrayList;

public class ProjectDatabaseSQL {
    String url = "jdbc:mysql://localhost:3306/FinalProject";
    String username = "root";
    String password = "pooya1234";
    static ProjectDatabaseSQL instance;
    private ProjectDatabaseSQL() {
    }
    public static ProjectDatabaseSQL getInstance(){
        if (instance==null){
            return instance = new ProjectDatabaseSQL();
        }else return instance;
    }
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }


    public void createProject(Project project) {
        try (Connection connection = getConnection()) {
            String insertProjectQuery = "INSERT INTO Project (project_name, project_discription, project_addTime) VALUES (?, ?, ?)";
            String insertBoardQuery = "INSERT INTO Board (project_id, board_name) VALUES (?, ?)";

            try (PreparedStatement projectStatement = connection.prepareStatement(insertProjectQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement boardStatement = connection.prepareStatement(insertBoardQuery)) {

                projectStatement.setString(1, project.getName());
                projectStatement.setString(2, project.getDescription());
                projectStatement.setLong(3, project.getAddDate());

                int rowsAffected = projectStatement.executeUpdate();

                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = projectStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int projectId = generatedKeys.getInt(1);
                            boardStatement.setInt(1, projectId);
                            boardStatement.setString(2, "Main Board");
                            boardStatement.executeUpdate();
                            System.out.println("Project and board created successfully.");
                        }
                    }
                } else {
                    throw new IllegalArgumentException("Failed to create project and board.");
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
    // Get All projects
    public ArrayList<Project> getAllProjects() {
        ArrayList<Project> projects = new ArrayList<>();

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM Project";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
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
    // Get project by project ID
    public Project getProjectById(int projectId) {
        Project project = null;

        try (Connection connection = getConnection()) {
            String selectQuery = "SELECT * FROM Project WHERE project_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, projectId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        project = new Project(
                                resultSet.getInt("project_id"),
                                resultSet.getString("project_name"),
                                resultSet.getString("project_discription"),
                                resultSet.getLong("project_addTime")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return project;
    }
}
