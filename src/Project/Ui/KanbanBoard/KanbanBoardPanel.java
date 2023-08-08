package Project.Ui.KanbanBoard;

import Project.Logic.*;
import Project.Logic.DataBase.SQL.BoardDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.BoardIssuesDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.ProjectIssuesDataBaseSql;
import Project.Logic.DataBase.SQL.IssueDataBaseSql;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    final JPanel addIssuePanel = new JPanel();
    private final RoundedButton addIssueButton = new RoundedButton("+", 14, Color.white, Color.BLACK, 20);
    int panelWidth = 190;
    Board board;
    User user;
    Project project;
    JPanel extraPanel;
    ArrayList<Issue> availableIssues;

    public KanbanBoardPanel(Board board, User user, Project project) {
        this.board = board;
        this.user = user;
        this.project = project;
        availableIssues = new ArrayList<>();
        setLayout(null);
        setPreferredSize(new Dimension(770, 600));
        setBackground(new Color(0xf7f7f7));
        int panelHeight = getHeight();
      if (board!=null && board.getId() != BoardDataBaseSql.getInstance().getAllBoardsOfProject(project.getId()).get(0).getId()) {
            addIssuePanel.setBounds(25, 25, 200, 55);
            addIssuePanel.setLayout(new GridLayout(2,1));
            JComboBox<String> issuesComboBox = new JComboBox<>();
            addIssueButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addIssuePanel.setVisible(true);
                }
            });

            for (Issue issue : IssueDataBaseSql.getInstance().getAllIssuesOfProject(project.getId())) {
              if (board!=null){
                  if (!BoardIssuesDataBaseSql.getInstance().getAllIssuesOfBoard(board.getId()).contains(issue)){
                      issuesComboBox.addItem(issue.getDescription());
                      availableIssues.add(issue);
                  }
              }
            }
            RoundedButton addButton = new RoundedButton("Add+", 15, Color.blue, Color.white, 12);
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (issuesComboBox.getSelectedItem() != null) {
                        Issue newIssue = availableIssues.get(issuesComboBox.getSelectedIndex());
                        BoardIssuesDataBaseSql.getInstance().assignIssueToBoard(board.getId(),newIssue.getId());
                        CategoryPanel categoryPanel = getCategoryPanelForStatus(newIssue.getStatus());
                        if (categoryPanel != null) {
                            IssuesPanel taskPanel = new IssuesPanel(categoryPanel, KanbanBoardPanel.this, newIssue);
                            taskPanel.setTitle(newIssue.getDescription());
                            taskPanel.setIssue(newIssue);
                            taskPanel.setUser(user);
                            categoryPanel.addTask(taskPanel);
                            extraPanel.add(taskPanel);
                        }
                        issuesComboBox.removeItem(issuesComboBox.getSelectedItem());
                        KanbanBoardPanel.this.reset();
                        addIssuePanel.setVisible(false);
                    }else addIssuePanel.setVisible(false);
                }
            });

            RoundedButton cancelButton = new RoundedButton("Cancel", 15, Color.RED, Color.white, 12);
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addIssuePanel.setVisible(false);
                }
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1,2));
            buttonPanel.add(addButton);
            buttonPanel.add(cancelButton);

            addIssuePanel.add(issuesComboBox);
            addIssuePanel.add(buttonPanel);
            addIssueButton.setBounds(0, 0, 25, 25);
            addIssuePanel.setVisible(false);
            add(addIssueButton);
            add(addIssuePanel);
        }

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
            ArrayList<Issue> issues = BoardIssuesDataBaseSql.getInstance().getAllIssuesOfBoard(board.getId());
            if (issues != null) {
                for (Issue issue : issues) {
                    CategoryPanel categoryPanel = getCategoryPanelForStatus(issue.getStatus());
                    if (categoryPanel != null) {
                        IssuesPanel taskPanel = new IssuesPanel(categoryPanel, this, issue);
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

    public void addTask(IssuesPanel task, CategoryPanel panel) {
        panel.addTask(task);
        reset();
    }

    public void removeTask(IssuesPanel task, CategoryPanel panel) {
        panel.removeTask(task);
        reset();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Project getProject() {
        return project;
    }
}
