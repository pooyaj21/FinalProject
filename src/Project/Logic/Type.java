package Project.Logic;

import Project.Ui.KanbanBoard.IssuesTypes;

import java.awt.*;

public enum Type {
    STORY(IssuesTypes.STORY),
    TASK(IssuesTypes.TASK),
    BUG(IssuesTypes.BUG);
    private final IssuesTypes issuesTypes;
    Type(IssuesTypes issuesTypes) {
        this.issuesTypes = issuesTypes;
    }

    public IssuesTypes getIssuesTypes() {
        return issuesTypes;
    }
}
