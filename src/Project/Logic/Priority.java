package Project.Logic;

import Project.Ui.KanbanBoard.IssuesPriority;
import Project.Ui.KanbanBoard.IssuesTypes;

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
