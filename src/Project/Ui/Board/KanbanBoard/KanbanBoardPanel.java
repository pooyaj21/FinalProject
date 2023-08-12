package Project.Ui.Board.KanbanBoard;

import Project.Logic.*;
import Project.Logic.DataBase.SQL.CrossTabel.BoardIssuesDataBaseSql;
import Project.Util.GeneralController;

import javax.jws.soap.SOAPBinding;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

public class KanbanBoardPanel extends JPanel {
    final CategoryPanel toDo = new CategoryPanel(Status.TODO);
    final DesignedColumn toDoD = new DesignedColumn("To Do", Color.RED);
    final CategoryPanel inProgress = new CategoryPanel(Status.IN_PROGRESS);
    final DesignedColumn inProgressD = new DesignedColumn("In Progress", Color.BLUE);
    final CategoryPanel qa = new CategoryPanel(Status.QA);
    final DesignedColumn qaD = new DesignedColumn("QA", Color.YELLOW);
    final CategoryPanel done = new CategoryPanel(Status.DONE);
    final DesignedColumn doneD = new DesignedColumn("Done", Color.GREEN);
    int panelWidth = 190;
    ArrayList<Issue> issues;
    User user;
    Project project;

    public KanbanBoardPanel(ArrayList<Issue> issues,User user, Project project,String name) {
        this.issues =issues;
        this.user = user;
        this.project = project;
        setLayout(null);
        setPreferredSize(new Dimension(770, 600));
        setBackground(new Color(0xf7f7f7));
        int panelHeight = getHeight();

        toDo.setBounds(0, 40, panelWidth, panelHeight);
        inProgress.setBounds(panelWidth, 40, panelWidth, panelHeight);
        qa.setBounds(panelWidth * 2, 40, panelWidth, panelHeight);
        done.setBounds(panelWidth * 3, 40, panelWidth, panelHeight);

        toDoD.setBounds(0, 10, panelWidth, 40);
        inProgressD.setBounds(panelWidth, 10, panelWidth, 40);
        qaD.setBounds(panelWidth * 2, 10, panelWidth, 40);
        doneD.setBounds(panelWidth * 3, 10, panelWidth, 40);

        JLabel label = new JLabel(name);
        label.setBounds(10, 0, 150, 30); // Specify position and size of the label
        add(label); // Add label to the panel


        add(toDoD);
        add(inProgressD);
        add(qaD);
        add(doneD);

        add(toDo);
        add(inProgress);
        add(qa);
        add(done);



        drawFirstTime();
        reset();
    }

    public void drawFirstTime() {
            if (issues != null) {
                for (Issue issue : issues) {
                    CategoryPanel categoryPanel = getCategoryPanelForStatus(issue.getStatus());
                    if (categoryPanel != null) {
                        IssuesPanel issuesPanel = new IssuesPanel(categoryPanel, this, issue);
                        issuesPanel.setTitle(issue.getDescription());
                        issuesPanel.setIssue(issue);
                        issuesPanel.setUser(user);
                        categoryPanel.addTask(issuesPanel);
                        add(issuesPanel);
                    }
                }
            }

        toDo.showTasks();
        inProgress.showTasks();
        qa.showTasks();
        done.showTasks();
    }

    public void reset() {
        toDo.showTasks();
        toDoD.numberTask.setText(String.valueOf(toDo.tasks.size()));
        inProgress.showTasks();
        inProgressD.numberTask.setText(String.valueOf(inProgress.tasks.size()));
        qa.showTasks();
        qaD.numberTask.setText(String.valueOf(qa.tasks.size()));
        done.showTasks();
        doneD.numberTask.setText(String.valueOf(done.tasks.size()));
        int highest = GeneralController.findHighest(new int[]{toDo.tasks.size(), inProgress.tasks.size(), qa.tasks.size(), done.tasks.size()});
        setPreferredSize(new Dimension(getWidth(), highest * 140 + 60));
        revalidate();
        repaint();
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

    public void addTask(IssuesPanel task, CategoryPanel panel) {
        panel.addTask(task);
        reset();
    }

    public void removeTask(IssuesPanel task, CategoryPanel panel) {
        panel.removeTask(task);
        reset();
    }

    public Project getProject() {
        return project;
    }
}
