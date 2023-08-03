package Project.Ui.KanbanBoard;

import Project.Logic.Board;
import Project.Logic.DataBase.BoardDatabase;
import Project.Logic.Issue;
import Project.Logic.Status;
import Project.Logic.User;
import Project.Util.GeneralController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

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
    Board board;
    User user;
    private final JPanel extraPanel;

    public KanbanBoardPanel(Board board, User user) {
        this.board = board;
        this.user = user;
        setLayout(null);
        setPreferredSize(new Dimension(770, 600));
        setBackground(new Color(0xf7f7f7));
        int panelHeight = getHeight();

        toDo.setBounds(0, 40, panelWidth, panelHeight);
        inProgress.setBounds(panelWidth, 40, panelWidth, panelHeight);
        qa.setBounds(panelWidth * 2, 40, panelWidth, panelHeight);
        done.setBounds(panelWidth * 3, 40, panelWidth, panelHeight);

        toDoD.setBounds(0, 0, panelWidth, 40);
        inProgressD.setBounds(panelWidth, 0, panelWidth, 40);
        qaD.setBounds(panelWidth * 2, 0, panelWidth, 40);
        doneD.setBounds(panelWidth * 3, 0, panelWidth, 40);


        add(toDoD);
        add(inProgressD);
        add(qaD);
        add(doneD);

        add(toDo);
        add(inProgress);
        add(qa);
        add(done);


        extraPanel = new JPanel();
        extraPanel.setBackground(null);
        extraPanel.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(extraPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10, 60, 770, 600);
        scrollPane.getViewport().setBackground(new Color(0xf7f7f7));
        scrollPane.setBackground(null);
        scrollPane.setBorder(null);

        add(scrollPane);

        drawFirstTime();
        reset();
    }

    public void drawFirstTime() {
        if (board != null) {
            Map<Board, ArrayList<Issue>> boardIssues = BoardDatabase.getInstance().getBoardIssues();
            ArrayList<Issue> issues = boardIssues.get(board);
            if (issues != null) {
                for (Issue issue : issues) {
                    CategoryPanel categoryPanel = getCategoryPanelForStatus(issue.getStatus());
                    if (categoryPanel != null) {
                        TaskPanel taskPanel = new TaskPanel(categoryPanel, this, issue);
                        taskPanel.setTitle(issue.getDescription());
                        taskPanel.setIssue(issue);
                        taskPanel.setUser(user);
                        categoryPanel.addTask(taskPanel);
                        extraPanel.add(taskPanel);
                    }
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
        extraPanel.setPreferredSize(new Dimension(getWidth(), (highest * 140)));
        extraPanel.revalidate();
        extraPanel.repaint();
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

    public void addTask(TaskPanel task, CategoryPanel panel) {
        panel.addTask(task);
        reset();
    }

    public void removeTask(TaskPanel task, CategoryPanel panel) {
        panel.removeTask(task);
        reset();
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
