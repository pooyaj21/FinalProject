package Project.Ui;

import Project.Logic.Board;
import Project.Logic.FeatureAccess;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Ui.KanbanBoard.KanbanBoardPanel;
import Project.Util.DateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectPanel extends JPanel {
    Project project;
    User user;
    JButton kanbanButton = new JButton("Kanban Board");
    JButton issueButton = new JButton("Issue Tracker");
    JButton reportButton = new JButton("Reports");
    JLabel nameProjectLabel = new JLabel();
    JLabel dateProjectLabel = new JLabel();
    BoardsPanel boardsPanel;
    IssueTrackerPanel issueTrackerPanel;
    KanbanBoardPanel kanbanBoardPanel;
    ReportPanel reportPanel;
    AddBoredPanel addBoredPanel;
    EditBoardPanel editBoardPanel;
    Board board;
    UserPanel userPanel;

    public ProjectPanel(UserPanel userPanel, Project project, User user) {
        this.userPanel = userPanel;
        this.project = project;
        this.user = user;
        setLayout(null);

        setSize(800, 700);



        kanbanButton.setBounds(50, 30, 200, 30);
        issueButton.setBounds(260, 30, 200, 30);
        reportButton.setBounds(470, 30, 200, 30);

        nameProjectLabel.setFont(new Font(null, Font.PLAIN, 20));
        nameProjectLabel.setBounds(250, 5, 200, 30);
        nameProjectLabel.setHorizontalAlignment(JLabel.CENTER);

        dateProjectLabel.setFont(new Font(null, Font.PLAIN, 12));
        dateProjectLabel.setBounds(600, 5, 200, 30);
        dateProjectLabel.setHorizontalAlignment(JLabel.CENTER);
        dateProjectLabel.setText(DateUtil.formatDate(project.getAddDate()));

        add(nameProjectLabel);
        add(dateProjectLabel);
        add(kanbanButton);
        add(issueButton);
        add(reportButton);

        kanbanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBoardsPanel();
            }
        });

        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showIssueTrackerPanel();
            }
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReportsPanel();
            }
        });
    }

    public ProjectPanel(Project project, User user) {
        this.project = project;
        this.user = user;
        setLayout(null);

        setSize(800, 700);

        kanbanButton.setBounds(50, 30, 200, 30);
        issueButton.setBounds(260, 30, 200, 30);
        reportButton.setBounds(470, 30, 200, 30);

        nameProjectLabel.setFont(new Font(null, Font.PLAIN, 20));
        nameProjectLabel.setBounds(250, 5, 200, 30);
        nameProjectLabel.setHorizontalAlignment(JLabel.CENTER);

        dateProjectLabel.setFont(new Font(null, Font.PLAIN, 12));
        dateProjectLabel.setBounds(600, 5, 200, 30);
        dateProjectLabel.setHorizontalAlignment(JLabel.CENTER);
        dateProjectLabel.setText(DateUtil.formatDate(project.getAddDate()));


        add(nameProjectLabel);
        add(dateProjectLabel);
        add(kanbanButton);
        add(issueButton);
        add(reportButton);

        kanbanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBoardsPanel();
            }
        });

        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showIssueTrackerPanel();
            }
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReportsPanel();
            }
        });
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void update() {
        nameProjectLabel.setText(project.getName());

        boardsPanel = new BoardsPanel(project, user, this);
        issueTrackerPanel = new IssueTrackerPanel(project, user);
        kanbanBoardPanel = new KanbanBoardPanel(board, user, project);
        editBoardPanel = new EditBoardPanel(board, project,this);
        addBoredPanel = new AddBoredPanel(project, this);
    }

    private void showBoardsPanel() {
        removeAllPanels();
        update();
        boardsPanel = new BoardsPanel(project, user, this);
        boardsPanel.setBounds(10, 60, 770, 600);
        boardsPanel.setVisible(true);
        add(boardsPanel);
        revalidate();
        repaint();
    }

    public void showKanBanBoardsPanel(Board board) {
        removeAllPanels();
        update();
        kanbanBoardPanel = new KanbanBoardPanel(board, user, project);
        kanbanBoardPanel.setBounds(10, 60, 770, 600);
        kanbanBoardPanel.setVisible(true);
        add(kanbanBoardPanel);
        revalidate();
        repaint();
    }

    public void showAddBoardsPAnel() {
        removeAllPanels();
        update();
        addBoredPanel = new AddBoredPanel(project, this);
        addBoredPanel.setBounds(10, 60, 770, 600);
        addBoredPanel.setVisible(true);
        add(addBoredPanel);
        revalidate();
        repaint();
    }

    public void showEditBoardsPanel(Board board) {
        removeAllPanels();
        update();
        editBoardPanel = new EditBoardPanel(board,project,this);
        editBoardPanel.setBounds(10, 60, 770, 600);
        editBoardPanel.setProject(project);
        editBoardPanel.update();
        editBoardPanel.setVisible(true);
        add(editBoardPanel);
        revalidate();
        repaint();
    }

    private void showIssueTrackerPanel() {
        removeAllPanels();
        update();
        issueTrackerPanel = new IssueTrackerPanel(project, user);
        issueTrackerPanel.setBounds(10, 60, 770, 600);
        issueTrackerPanel.setVisible(true);
        add(issueTrackerPanel);
        revalidate();
        repaint();
    }

    private void showReportsPanel() {
        removeAllPanels();
        update();
        reportPanel = new ReportPanel(project);
        reportPanel.setBounds(10, 60, 770, 600);
        reportPanel.setVisible(true);
        add(reportPanel);
        revalidate();
        repaint();
    }

    void removeAllPanels() {
        if (issueTrackerPanel != null) issueTrackerPanel.setVisible(false);
        if (boardsPanel != null) boardsPanel.setVisible(false);
        if (kanbanBoardPanel != null) kanbanBoardPanel.setVisible(false);
        if (editBoardPanel != null) editBoardPanel.setVisible(false);
        if (addBoredPanel != null) addBoredPanel.setVisible(false);
        if (reportPanel != null) reportPanel.setVisible(false);
    }

    public User getUser() {
        return user;
    }
}
