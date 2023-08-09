package Project.Logic;

public class IssuesTransition {
    private final int transitionId;
    private final int userId;
    private final Status previousStatus;
    private final Status newStatus;
    private final Long transitionTime;

    public IssuesTransition(int transitionId, int userId, Status previousStatus, Status newStatus, Long transitionTime) {
        this.transitionId = transitionId;
        this.userId = userId;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.transitionTime = transitionTime;
    }

    public int getTransitionId() {
        return transitionId;
    }

    public int getUserId() {
        return userId;
    }

    public Status getPreviousStatus() {
        return previousStatus;
    }

    public Status getNewStatus() {
        return newStatus;
    }

    public Long getTransitionTime() {
        return transitionTime;
    }
}