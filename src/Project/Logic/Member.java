package Project.Logic;

import java.util.HashMap;

public abstract class Member {
    private String id;
    private int password;
    private String fullName;
    private HashMap<String, Position> projects;
    //you can put Project class instead of String

    public Member(String id, int password, String fullName) {
        this.id = id;
        this.password = password;
        this.fullName = fullName;
        this.projects = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
