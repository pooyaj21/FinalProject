package Project.Ui.Board.KanbanBoard;

import Project.Logic.Board;
import Project.Logic.DataBase.SQL.CrossTabel.BoardIssuesDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.UserProjectDataBaseSql;
import Project.Logic.Issue;
import Project.Logic.Project;
import Project.Logic.User;

import javax.swing.*;
import java.util.ArrayList;

public class SwimlaneBoardPanel extends JPanel {
    private final JScrollPane scrollPane;
    private final JPanel contentPanel;
    Board board;
    Project project;
    User user;

    public SwimlaneBoardPanel(Board board, Project project, User user) {
        this.board = board;
        this.project = project;
        this.user = user;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(this);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Vertical arrangement
        scrollPane.setViewportView(contentPanel); // Set content for the scroll pane

        if (BoardIssuesDataBaseSql.getInstance().getAllIssuesOfBoard(board.getId()).size() == 0) {
            addKanbanBoard(new ArrayList<>(), user, project, "");
        } else {
            for (int i = 0; i < UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId()).size() + 1; i++) {
                boolean doseItHaveIssues = false;
                if (i == UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId()).size()) {
                    ArrayList<Issue> issues = new ArrayList<>();
                    for (Issue issue : BoardIssuesDataBaseSql.getInstance().getAllIssuesOfBoard(board.getId())) {
                        if (issue.getUser() == null) {
                            issues.add(issue);
                            doseItHaveIssues = true;
                        }
                    }
                    if (doseItHaveIssues) addKanbanBoard(issues, user, project, "Not Assigned Issues");
                } else {
                    ArrayList<Issue> issues = new ArrayList<>();
                    for (Issue issue : BoardIssuesDataBaseSql.getInstance().getAllIssuesOfBoard(board.getId())) {
                        if (issue.getUser() != null) {
                            if (issue.getUser().getId() == UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId()).get(i).getId()) {
                                issues.add(issue);
                                doseItHaveIssues = true;
                            }
                        }
                    }
                    if (doseItHaveIssues)
                        addKanbanBoard(issues, user, project, UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId()).get(i).getFullName());
                }
            }
        }
        add(scrollPane);
    }

    public void addKanbanBoard(ArrayList<Issue> issues, User user, Project project, String name) {
        KanbanBoardPanel kanbanBoardPanel = new KanbanBoardPanel(issues, user, project, name);
        contentPanel.add(kanbanBoardPanel); // Add to the content wrapping panel
        contentPanel.revalidate(); // Revalidate the content panel
    }
}
