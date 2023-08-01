package Project.Ui.KanbanBoard;

import Project.Logic.Board;
import Project.Logic.DataBase.BoardDatabase;
import Project.Logic.Issue;
import Project.Logic.Status;
import Project.Logic.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class KanbanBoardPanel extends JPanel {
    final CategoryPanel toDo = new CategoryPanel(Status.TODO);
    final DesignedColumn toDoD = new DesignedColumn("To Do", toDo, this, Color.RED);
    final CategoryPanel inProgress = new CategoryPanel(Status.IN_PROGRESS);
    final DesignedColumn inProgressD = new DesignedColumn("In Progress", inProgress, this, Color.BLUE);
    final CategoryPanel needReview = new CategoryPanel(Status.QA);
    final DesignedColumn needReviewD = new DesignedColumn("Need Review", needReview, this, Color.YELLOW);
    final CategoryPanel done = new CategoryPanel(Status.DONE);
    final DesignedColumn doneD = new DesignedColumn("Done", done, this, Color.GREEN);
    int panelWidth = 200;
    Board board;
    User user;

    public KanbanBoardPanel(Board board, User user) {
        this.board=board;
        this.user=user;
        setLayout(null);
        setPreferredSize(new Dimension(800,600));
        setBackground(new Color(0xf7f7f7));
        int panelHeight = getHeight();

        toDo.setBounds(0, 75, panelWidth, panelHeight);
        inProgress.setBounds(panelWidth, 75, panelWidth, panelHeight);
        needReview.setBounds(panelWidth * 2, 75, panelWidth, panelHeight);
        done.setBounds(panelWidth * 3, 75, panelWidth, panelHeight);

        toDoD.setBounds(0, 0, panelWidth, 80);
        inProgressD.setBounds(panelWidth, 0, panelWidth, 80);
        needReviewD.setBounds(panelWidth * 2, 0, panelWidth, 80);
        doneD.setBounds(panelWidth * 3, 0, panelWidth, 80);

        add(toDo);
        add(inProgress);
        add(needReview);
        add(done);

        add(toDoD);
        add(inProgressD);
        add(needReviewD);
        add(doneD);
        reset();
        drawFirstTime();
    }

    public void drawFirstTime(){
        if (board != null) {
            Map<Board, ArrayList<Issue>> boardIssues = BoardDatabase.getInstance().getBoardIssues();
            ArrayList<Issue> issues = boardIssues.get(board);
            if (issues != null) {
                for (Issue issue : issues) {
                    CategoryPanel categoryPanel = getCategoryPanelForStatus(issue.getStatus());
                    if (categoryPanel != null) {
                        TaskPanel taskPanel = new TaskPanel(categoryPanel,this,issue);
                        taskPanel.setTitle(issue.getDescription());
                        taskPanel.setIssue(issue);
                        taskPanel.setUser(user);
                        categoryPanel.addTask(taskPanel);
                        add(taskPanel);
                    }
                }
            }
        }

        toDo.showTasks();
        inProgress.showTasks();
        needReview.showTasks();
        done.showTasks();
    }

    public void reset() {
        toDo.showTasks();
        toDoD.numberTask.setText(String.valueOf(toDo.tasks.size()));
        inProgress.showTasks();
        inProgressD.numberTask.setText(String.valueOf(inProgress.tasks.size()));
        needReview.showTasks();
        needReviewD.numberTask.setText(String.valueOf(needReview.tasks.size()));
        done.showTasks();
        doneD.numberTask.setText(String.valueOf(done.tasks.size()));
    }

    private CategoryPanel getCategoryPanelForStatus(Status status) {
        CategoryPanel categoryPanel = null;
        if (status != null) {
            switch (status) {
                case TODO:
                    categoryPanel= toDo;
                    break;
                case IN_PROGRESS:
                    categoryPanel= inProgress;
                    break;
                case QA:
                    categoryPanel= needReview;
                    break;
                case DONE:
                    categoryPanel= done;
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
