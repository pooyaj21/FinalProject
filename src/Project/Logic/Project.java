package Project.Logic;

import Project.Util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private String description;
    private final long addDate;
    private ArrayList<Board> boards;
    private ArrayList<User> members;
    private final int id;
    public Project(int id, String name) {
        this.id = id;
        this.name = name;
        this.addDate = DateUtil.timeOfNow();
        this.members = new ArrayList<>();
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

    public List<User> getMembers() {
        return members;
    }

    public void addMember(User user) {
        if (!members.contains(user)) {
            members.add(user);
        }
    }
    public void removeMember(User user) {
        members.remove(user);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
