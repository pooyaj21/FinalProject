package Project.Logic;

import Project.Util.DateUtil;

public class Issue {
    private int id;
    private int projectId;
    private int boardId;
    private User user;
    private String description;
    private final long addTime;
    private long LastUpdateTime;
    private Type type;
    private Priority priority;
    private Status status;

    public Issue( String description) {
        this.addTime = DateUtil.timeOfNow();
        this.description = description;
    }

    public Issue(int id, int projectId, int boardId, int userId, String description, long addTime, long lastUpdateTime, String type, String priority, String status) {
        this.id = id;
        this.projectId = projectId;
        this.boardId = boardId;
        this.user = userId;
        this.description = description;
        this.addTime = addTime;
        LastUpdateTime = lastUpdateTime;
        this.type = type;
        this.priority = priority;
        this.status = status;
    }

    public long getAddTime() {
        return addTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLastUpdateTime() {
        return LastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        LastUpdateTime = lastUpdateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
