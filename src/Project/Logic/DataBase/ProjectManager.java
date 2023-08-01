package Project.Logic.DataBase;

import Project.Logic.Board;
import Project.Logic.Project;
import Project.Logic.User;

import java.util.ArrayList;
import java.util.Map;

public class ProjectManager {
    private static ProjectManager instance;
    private final ProjectDatabase projectDatabase = ProjectDatabase.getInstance();

    private ProjectManager() {
    }

    public static ProjectManager getInstance() {
        if (instance == null) {
            instance = new ProjectManager();
        }
        return instance;
    }

    public void createProject(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Project name cannot be null or empty");
        Project project = new Project(name);
        projectDatabase.addProject(project);
    }

    public void createProject(Project project) {
        projectDatabase.addProject(project);
    }

    public ArrayList<Project> getAllProjects() {
        return new ArrayList<>(projectDatabase.getProjects().values());
    }

    public ArrayList<Project> getProjectsByUser(User member) {
        if (member == null) throw new IllegalArgumentException("Member cannot be null");

        ArrayList<Project> projectsByMember = new ArrayList<>();
        for (Map.Entry<Project, ArrayList<User>> entry : projectDatabase.getProjectsMembers().entrySet()) {
            Project project = entry.getKey();
            ArrayList<User> members = entry.getValue();
            if (members.contains(member)) projectsByMember.add(project);
        }
        return projectsByMember;
    }
    public ArrayList<User> getUsersByProject(Project project) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null");

        ArrayList<User> users = new ArrayList<>();
        for (Map.Entry<Project, ArrayList<User>> entry : projectDatabase.getProjectsMembers().entrySet()) {
            if (entry.getKey().equals(project)) {
                users.addAll(entry.getValue());
                break;
            }
        }
        return users;
    }

    public Project getProjectById(int projectId) {
        Project project = projectDatabase.getProjects().get(projectId);
        if (project == null) throw new IllegalArgumentException("Project with ID " + projectId + " not found");
        return project;
    }

    public void addMemberToProject(Project project, User member) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null");

        if (member == null) throw new IllegalArgumentException("User cannot be null");

        ArrayList<User> members = projectDatabase.getMembersByProject(project);
        if (members.contains(member))throw new IllegalArgumentException("Can not add user twice");
        else members.add(member);
    }

    public void removeMemberFromProject(Project project, User member) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null");

        if (member == null) throw new IllegalArgumentException("Member cannot be null");

        ArrayList<User> members = projectDatabase.getMembersByProject(project);
        members.remove(member);
    }

    public void addBoardToProject(Project project, Board board) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null");

        if (board == null) throw new IllegalArgumentException("Board cannot be null");
        projectDatabase.getBoardsByProject(project).add(board);
    }

    public void removeBoardFromProject(Project project, Board board) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null");

        if (board == null) throw new IllegalArgumentException("Board cannot be null");
        projectDatabase.getBoardsByProject(project).remove(board);
    }

    public void editProjectName(Project project, String newName) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null");
        if (newName == null || newName.trim().isEmpty())
            throw new IllegalArgumentException("Project name cannot be null or empty");
        project.setName(newName);
    }

    public void editProjectDescription(Project project, String newDescription) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null");

        project.setDescription(newDescription);
    }

    public void editProjectMembers(Project project, ArrayList<User> newMembers) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null");
        if (newMembers == null) throw new IllegalArgumentException("Members list cannot be null");

        projectDatabase.getMembersByProject(project).clear();
        projectDatabase.getMembersByProject(project).addAll(newMembers);
    }

    public void removeProject(Project project) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null");
        projectDatabase.removeProject(project);
    }


}
