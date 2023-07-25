package Project.Logic;

import java.util.HashMap;

public class User {
    private String Email;
    private String password;
    private String fullName;
    private HashMap<String, Role> projects;
    //you can put Project class instead of String
    private Role role;

    public User(String Email, String password, String fullName, Role role) {
        this.Email = Email;
        this.password = password;
        this.fullName = fullName;
        this.role=role;
        this.projects = new HashMap<>();
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public HashMap<String, Role> getProjects() {
        return projects;
    }

    public void setProjects(HashMap<String, Role> projects) {
        this.projects = projects;
    }

    public void addProject(String project, Role position){
        projects.put(project,position);
    }
}
