package Project.Ui;

import Project.Logic.*;
import Project.Ui.KanbanBoard.KanbanBoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectPanel extends JPanel {
    Project project;
    User user;
    ProjectSettingPanel projectSettingPanel;
    JButton kanbanButton = new JButton("Kanban Board");
    JButton issueButton = new JButton("Issue Tracker");
    JButton reportButton = new JButton("Reports");
    JButton settingButton = new JButton("⋮");
    JLabel nameProjectLabel = new JLabel();
    BoardsPanel boardsPanel;
    IssueTrackerPanel issueTrackerPanel;
    KanbanBoardPanel kanbanBoardPanel;
    AddBoredPanel addBoredPanel;
    EditBoardPanel editBoardPanel;
    Board board;
    UserPanel userPanel;

    public ProjectPanel(UserPanel userPanel,Project project,User user) {
        this.userPanel = userPanel;
        this.project = project;
        this.user = user;
        setLayout(null);

        setSize(800, 700);

        projectSettingPanel = new ProjectSettingPanel(userPanel);
        projectSettingPanel.setBounds(0, 0, getWidth(), getHeight());
        projectSettingPanel.setVisible(false);

        settingButton.setFont(new Font(null, Font.PLAIN, 30));
        settingButton.setContentAreaFilled(false);
        settingButton.setBorder(null);
        settingButton.setBounds(750, 2, 40, 40);
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectSettingPanel.setProject(project);
                projectSettingPanel.setVisible(true);
            }
        });

        kanbanButton.setBounds(50, 30, 200, 30);
        issueButton.setBounds(260, 30, 200, 30);
        reportButton.setBounds(470, 30, 200, 30);

        nameProjectLabel.setFont(new Font(null, Font.PLAIN, 20));
        nameProjectLabel.setBounds(250, 5, 200, 30);
        nameProjectLabel.setHorizontalAlignment(JLabel.CENTER);


        add(nameProjectLabel);
        add(settingButton);
        add(projectSettingPanel);
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
        settingButton.setVisible(user.getRole().hasAccess(FeatureAccess.PROJECT_SETTING));
        nameProjectLabel.setText(project.getName());

        boardsPanel = new BoardsPanel(project,user,this);
        issueTrackerPanel = new IssueTrackerPanel(project, user);
        kanbanBoardPanel = new KanbanBoardPanel(board,user,project);
        editBoardPanel = new EditBoardPanel(board,this);
        addBoredPanel = new AddBoredPanel(project,this);
    }

    private void showBoardsPanel() {
        removeAllPanels();
        update();
        boardsPanel = new BoardsPanel(project,user,this);
        boardsPanel.setBounds(10, 60, 770, 600);
        boardsPanel.setVisible(true);
        add(boardsPanel);
        revalidate();
        repaint();
    }
    public void showKanBanBoardsPanel(Board board) {
        removeAllPanels();
        update();
        kanbanBoardPanel = new KanbanBoardPanel(board,user,project);
        kanbanBoardPanel.setBounds(10, 60, 770, 600);
        kanbanBoardPanel.setVisible(true);
        add(kanbanBoardPanel);
        revalidate();
        repaint();
    }

    public void showAddBoardsPAnel() {
        removeAllPanels();
        update();
        addBoredPanel = new AddBoredPanel(project,this);
        addBoredPanel.setBounds(10, 60, 770, 600);
        addBoredPanel.setVisible(true);
        add(addBoredPanel);
        revalidate();
        repaint();
    }

    public void showEditBoardsPanel(Board board) {
        removeAllPanels();
        update();
        editBoardPanel = new EditBoardPanel(board,this);
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
        // You can implement the Reports panel or handle the logic accordingly.
    }

    void removeAllPanels() {
        issueTrackerPanel.setVisible(false);
        boardsPanel.setVisible(false);
        kanbanBoardPanel.setVisible(false);
        editBoardPanel.setVisible(false);
        addBoredPanel.setVisible(false);
        // Remove other panels as you add them in the future.
    }

    public void showKanbanBoardsPanel(){

    }
}
