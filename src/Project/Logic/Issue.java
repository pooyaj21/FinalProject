package Project.Logic;

import Project.Util.DateUtil;

public class Issue {
    private final long addTime;
    private String description;
    private long LastUpdateTime;
    private Type type;
    private Priority priority;
    private Status status;
    private final int id;
    private static int counter;

    private User user;
    public Issue( String description) {
        counter++;
        this.addTime = DateUtil.timeOfNow();
        this.description = description;
        id=counter;
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
