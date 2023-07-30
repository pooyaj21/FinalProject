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
        addMemberToProject(project,UserDatabase.getInstance().getUsers().get(0));
    }

    public void createProject(Project project) {
        projectDatabase.addProject(project);
    }

    public ArrayList<Project> getAllProjects() {
        return new ArrayList<>(projectDatabase.getProjects().values());
    }

    public ArrayList<Project> getProjectsByMember(User member) {
        if (member == null) throw new IllegalArgumentException("Member cannot be null");

        ArrayList<Project> projectsByMember = new ArrayList<>();
        for (Map.Entry<Project, ArrayList<User>> entry : projectDatabase.getProjectsMembers().entrySet()) {
            Project project = entry.getKey();
            ArrayList<User> members = entry.getValue();
            if (members.contains(member)) projectsByMember.add(project);
        }
        return projectsByMember;
    }

    public Project getProjectById(int projectId) {
        Project project = projectDatabase.getProjects().get(projectId);
        if (project == null) throw new IllegalArgumentException("Project with ID " + projectId + " not found");
        return project;
    }

    public void addMemberToProject(Project project, User member) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null");

        if (member == null) throw new IllegalArgumentException("Member cannot be null");

        ArrayList<User> members = projectDatabase.getMembersByProject(project);
        if (!members.contains(member)) members.add(member);
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

        ArrayList<Board> boards = projectDatabase.getBoardsByProject(project);
        if (!boards.contains(board)) boards.add(board);
    }

    public void removeBoardFromProject(Project project, Board board) {
        if (project == null) throw new IllegalArgumentException("Project cannot be null");

        if (board == null) throw new IllegalArgumentException("Board cannot be null");

        ArrayList<Board> boards = projectDatabase.getBoardsByProject(project);
        boards.remove(board);
    }
}
