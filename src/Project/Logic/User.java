package Project.Logic;

import java.util.HashMap;

public abstract class User {
    private String Email;
    private int password;
    private String fullName;
    private HashMap<String, Position> projects;
    //you can put Project class instead of String

    public User(String Email, int password, String fullName) {
        this.Email = Email;
        this.password = password;
        this.fullName = fullName;
        this.projects = new HashMap<>();
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public HashMap<String, Position> getProjects() {
        return projects;
    }

    public void setProjects(HashMap<String, Position> projects) {
        this.projects = projects;
    }

    public void addProject(String project,Position position){
        projects.put(project,position);
    }
}
