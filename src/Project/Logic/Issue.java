package Project.Logic;

import Project.Util.DateUtil;

public class Issue {
    private final long addTime;
    private String description;
    private long LastUpdateTime;
    private Type type;
    private Priority priority;
    private Status status;

    public Issue( String description) {
        this.addTime = DateUtil.timeOfNow();
        this.description = description;
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
}
