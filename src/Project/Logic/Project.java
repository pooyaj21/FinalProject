package Project.Logic;

import Project.Util.DateUtil;

public class Project {
    private String name;
    private String description;
    private final long addDate;
    private final int id;
    private static int counter = 0;

    public Project(String name) {
        counter++;
        this.id = counter;
        this.name = name;
        this.addDate = DateUtil.timeOfNow();
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
