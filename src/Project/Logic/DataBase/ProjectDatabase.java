//package Project.Logic.DataBase;
//
//import Project.Logic.Board;
//import Project.Logic.Issue;
//import Project.Logic.Project;
//import Project.Logic.User;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ProjectDatabase {
//    private static ProjectDatabase instance;
//    private final Map<Integer, Project> projects;
//    private final Map<Project, ArrayList<Board>> projectsBoards;
//    private final Map<Project, ArrayList<User>> projectsMembers;
//    private final Map<Project, ArrayList<Issue>> projectsIssues;
//
//    private ProjectDatabase() {
//        projects = new HashMap<>();
//        projectsBoards = new HashMap<>();
//        projectsMembers = new HashMap<>();
//        projectsIssues = new HashMap<>();
//    }
//
//    public static ProjectDatabase getInstance() {
//        if (instance == null) {
//            instance = new ProjectDatabase();
//        }
//        return instance;
//    }
//
//    public void addProject(Project project) {
//        projects.put(project.getId(), project);
//        projectsBoards.put(project, new ArrayList<>());
//        projectsMembers.put(project, new ArrayList<>());
//        projectsIssues.put(project, new ArrayList<>());
//    }
//
//    public void removeProject(Project project) {
//        projects.remove(project.getId());
//        projectsBoards.remove(project);
//        projectsMembers.remove(project);
//    }
//
//    public Map<Integer, Project> getProjects() {
//        return projects;
//    }
//
//    public Map<Project, ArrayList<Board>> getProjectsBoards() {
//        return projectsBoards;
//    }
//
//    public Map<Project, ArrayList<User>> getProjectsMembers() {
//        return projectsMembers;
//    }
//
//    public Map<Project, ArrayList<Issue>> getProjectsIssues() {
//        return projectsIssues;
//    }
//
//
//    public ArrayList<User> getMembersByProject(Project project) {
//        return projectsMembers.get(project);
//    }
//
//    public ArrayList<Board> getBoardsByProject(Project project) {
//        return projectsBoards.get(project);
//    }
//
//    public ArrayList<Issue> getIssuesByProject(Project project) {
//        return projectsIssues.get(project);
//    }
//
//}
