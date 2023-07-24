package Project.Logic;

import Project.Util.DateUtil;

import java.util.ArrayList;


public class Project {
    private String name;
    private String description;
    private final long addDate;
    private ArrayList<Board> boards;

    public Project(String name) {
        this.name = name;
        this.addDate= DateUtil.timeOfNow();
        this.boards=new ArrayList<>();
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

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }
}
