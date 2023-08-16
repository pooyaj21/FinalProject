package Project.Ui.Project;

import Project.Logic.Board;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Ui.Board.AddBoredPanel;
import Project.Ui.Board.BoardsPanel;
import Project.Ui.Board.EditBoardPanel;
import Project.Ui.Board.KanbanBoard.SwimlaneBoardPanel;
import Project.Ui.IssueTrackerPanel;
import Project.Ui.ReportPanel;
import Project.Ui.User.UserPanel;
import Project.Util.DateUtil;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectPanel extends JPanel {
    public RoundedButton kanbanButton = new RoundedButton("Kanban Board",15,new Color(0x247ef0),Color.white,12);
    Project project;
    User user;
    RoundedButton issueButton = new RoundedButton("Issue Tracker",15,new Color(0x247ef0),Color.white,12);
    RoundedButton reportButton = new RoundedButton("Reports",15,new Color(0x247ef0),Color.white,12);
    JLabel nameProjectLabel = new JLabel();
    JLabel dateProjectLabel = new JLabel();
    BoardsPanel boardsPanel;
    IssueTrackerPanel issueTrackerPanel;
    SwimlaneBoardPanel swimlaneBoardPanel;
    ReportPanel reportPanel;
    AddBoredPanel addBoredPanel;
    EditBoardPanel editBoardPanel;
    Board board;
    UserPanel userPanel;

    public ProjectPanel(UserPanel userPanel, Project project, User user) {
        this.userPanel = userPanel;
        this.project = project;
        this.user = user;
        setOpaque(false);
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

    public void update() {
        nameProjectLabel.setText(project.getName());

        boardsPanel = new BoardsPanel(project, user, this);
        issueTrackerPanel = new IssueTrackerPanel(project, user);
        editBoardPanel = new EditBoardPanel(board, this);
        addBoredPanel = new AddBoredPanel(project, this);
    }

    private void showBoardsPanel() {
        removeAllPanels();
        update();
        boardsPanel = new BoardsPanel(project, user, this);
        boardsPanel.setOpaque(false);
        boardsPanel.setBounds(10, 60, 770, 600);
        boardsPanel.setVisible(true);
        add(boardsPanel);
        revalidate();
        repaint();
    }

    public void showKanBanBoardsPanel(Board board) {
        removeAllPanels();
        update();
        swimlaneBoardPanel = new SwimlaneBoardPanel(board, project, user);
        swimlaneBoardPanel.setOpaque(false);
        swimlaneBoardPanel.setBounds(10, 60, 770, 600);
        swimlaneBoardPanel.setVisible(true);
        add(swimlaneBoardPanel);
        revalidate();
        repaint();
    }

    public void showAddBoardsPAnel() {
        removeAllPanels();
        update();
        addBoredPanel = new AddBoredPanel(project, this);
        addBoredPanel.setBounds(10, 60, 770, 600);
        addBoredPanel.setOpaque(false);
        addBoredPanel.setVisible(true);
        add(addBoredPanel);
        revalidate();
        repaint();
    }

    public void showEditBoardsPanel(Board board) {
        removeAllPanels();
        update();
        editBoardPanel = new EditBoardPanel(board, this);
        editBoardPanel.setBounds(10, 60, 770, 600);
        editBoardPanel.setOpaque(false);
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
        issueTrackerPanel.setOpaque(false);
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
        reportPanel.setOpaque(false);
        reportPanel.setBounds(10, 60, 770, 600);
        reportPanel.setVisible(true);
        add(reportPanel);
        revalidate();
        repaint();
    }

    void removeAllPanels() {
        if (issueTrackerPanel != null) issueTrackerPanel.setVisible(false);
        if (boardsPanel != null) boardsPanel.setVisible(false);
        if (swimlaneBoardPanel != null) swimlaneBoardPanel.setVisible(false);
        if (editBoardPanel != null) editBoardPanel.setVisible(false);
        if (addBoredPanel != null) addBoredPanel.setVisible(false);
        if (reportPanel != null) reportPanel.setVisible(false);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
