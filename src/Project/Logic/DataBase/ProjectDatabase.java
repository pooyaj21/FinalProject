package Project.Logic.DataBase;

import Project.Logic.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectDatabase {
    private static ProjectDatabase instance;
    private final Map<Integer, Project> projects;

    private ProjectDatabase() {
        projects = new HashMap<>();
    }

    public static ProjectDatabase getInstance() {
        if (instance == null) {
            instance = new ProjectDatabase();
        }
        return instance;
    }

    public void addProject(Project project) {
        projects.put(project.getId(), project);
    }

    public void removeProject(Project project) {
        projects.remove(project.getId());
    }

    public ArrayList<Project> getAllProjects() {
        return new ArrayList<>(projects.values());
    }

    public Project getProjectById(int projectId) {
        return projects.get(projectId);
    }
    public Project getProject(Project project) {
        return projects.get(project.getId());
    }

    public boolean containsProject(Project project) {
        return projects.containsKey(project.getId());
    }
}
