package Project.Logic.DataBase;

import Project.Logic.Project;
import Project.Logic.User;

import java.util.ArrayList;

public class ProjectManager {
    private static ProjectManager instance;
    private ProjectDatabase projectDatabase = ProjectDatabase.getInstance();

    private ProjectManager() {
    }

    public static ProjectManager getInstance() {
        if (instance == null) {
            instance = new ProjectManager();
        }
        return instance;
    }

    public void createProject(int id, String name) {
        Project project = new Project(id, name);
        projectDatabase.addProject(project);
    }

    public void addMemberToProject(Project project, User member) {
        if (project != null) {
            project.addMember(member);
        } else {
            throw new IllegalArgumentException("Project cannot be null.");
        }
    }

    public void removeMemberFromProject(Project project, User member) {
        if (project != null) {
            project.removeMember(member);
        } else {
            throw new IllegalArgumentException("Project cannot be null.");
        }
    }

    public ArrayList<Project> getAllProjects(){
      return projectDatabase.getAllProjects();
    }

    public ArrayList<Project> getProjectsByMember(User member) {
        ArrayList<Project> projectsByMember = new ArrayList<>();
        ArrayList<Project> allProjects = projectDatabase.getAllProjects();

        for (Project project : allProjects) {
            if (project.getMembers().contains(member)) {
                projectsByMember.add(project);
            }
        }

        return projectsByMember;
    }
}
