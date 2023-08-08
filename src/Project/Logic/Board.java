package Project.Logic;

public class Board {

    private int id;
    private int projectId;
    private String name;

    public Board(String title) {
        this.name = title;
    }

    public Board(int id, int projectId, String name) {
        this.id = id;
        this.projectId = projectId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
