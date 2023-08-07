package Project.Ui.KanbanBoard;

import Project.Logic.*;
import Project.Logic.DataBase.ProjectManager;
import Project.Util.GeneralController;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class SwimLanePanel extends JPanel {
    final CategoryPanel toDo = new CategoryPanel(Status.TODO);
    final CategoryPanel inProgress = new CategoryPanel(Status.IN_PROGRESS);
    final CategoryPanel qa = new CategoryPanel(Status.QA);
    final CategoryPanel done = new CategoryPanel(Status.DONE);
    int panelWidth = 190;
    ProjectManager projectManager = ProjectManager.getInstance();
    User user;
    Project project;
    KanbanBoardPanel kanbanBoardPanel;

    public SwimLanePanel(User user, Project project, KanbanBoardPanel kanbanBoardPanel) {
        this.user = user;
        this.project = project;
        this.kanbanBoardPanel = kanbanBoardPanel;
        setLayout(null);
        setPreferredSize(new Dimension(770, 140));

        JLabel label = new JLabel(user.getFullName());
        label.setBounds(10, 0, 25, 200);
        label.setOpaque(false);
        label.setFont(label.getFont().deriveFont(AffineTransform.getRotateInstance(Math.PI / 2)));

        add(label);
        drawFirstTimeForUser();
    }

    public void drawFirstTimeForUser() {
        ArrayList<Issue> issues = projectManager.getIssuesByProject(project);
        if (issues != null) {
            for (Issue issue : issues) {
                CategoryPanel categoryPanel = getCategoryPanelForStatus(issue.getStatus());
                if (categoryPanel != null && issue.getUser() == user) {
                    IssuesPanel taskPanel = new IssuesPanel(categoryPanel, kanbanBoardPanel, issue);
                    taskPanel.setTitle(issue.getDescription());
                    taskPanel.setIssue(issue);
                    taskPanel.setUser(user);
                    categoryPanel.addTask(taskPanel);
                    add(taskPanel);
                    repaint();
                    revalidate();
                }
            }
        }
        reset();
    }

    public void reset() {
        toDo.showTasks();
        inProgress.showTasks();
        qa.showTasks();
        done.showTasks();
    }

    private CategoryPanel getCategoryPanelForStatus(Status status) {
        CategoryPanel categoryPanel = null;
        if (status != null) {
            switch (status) {
                case TODO:
                    categoryPanel = toDo;
                    break;
                case IN_PROGRESS:
                    categoryPanel = inProgress;
                    break;
                case QA:
                    categoryPanel = qa;
                    break;
                case DONE:
                    categoryPanel = done;
                    break;
            }
        }
        return categoryPanel;
    }
}
