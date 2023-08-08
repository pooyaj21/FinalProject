package Project.Logic;

import Project.Util.DateUtil;

public class Project {
    private int id;
    private String name;
    private String description;
    private final long addDate;

    public Project(String name) {
        this.name = name;
        this.addDate = DateUtil.timeOfNow();
    }

    public Project(int id, String name, String description, long addDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.addDate = addDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAddDate() {
        return addDate;
    }

    public int getId() {
        return id;
    }
}
