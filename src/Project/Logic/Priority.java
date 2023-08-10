package Project.Logic;

import Project.Ui.Board.KanbanBoard.IssuesPriority;

public enum Priority {
    LOW(IssuesPriority.LOW),
    MEDIUM(IssuesPriority.MEDIUM),
    HIGH(IssuesPriority.HIGH);

    private final IssuesPriority issuesPriority;

    Priority(IssuesPriority issuesPriority) {
        this.issuesPriority=issuesPriority;
    }

    public IssuesPriority getIssuesPriority() {
        return issuesPriority;
    }
}
