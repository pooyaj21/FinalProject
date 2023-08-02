package Project.Ui;

import Project.Logic.*;
import Project.Logic.DataBase.BoardDatabase;
import Project.Logic.DataBase.BoardManager;
import Project.Logic.DataBase.ProjectManager;
import Project.Ui.KanbanBoard.KanbanBoardPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectPanel extends JPanel {
    Project project;
    User user;
    ProjectSettingPanel projectSettingPanel;
    JTabbedPane tabbedPane = new JTabbedPane();
    JButton settingButton = new JButton("⋮");
    JLabel nameProjectLabel = new JLabel();
    KanbanBoardPanel kanbanBoardPanel;
    IssueTrackerPanel issueTrackerPanel;

    public ProjectPanel(UserPanel userPanel) {
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
        tabbedPane.setBounds(10, 25, 780, 650);


        nameProjectLabel.setFont(new Font(null, Font.PLAIN, 20));
        nameProjectLabel.setBounds(250, 5, 200, 30);
        nameProjectLabel.setHorizontalAlignment(JLabel.CENTER);


        add(nameProjectLabel);
        add(settingButton);
        add(projectSettingPanel);
        add(tabbedPane);
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
        tabbedPane.removeAll();
        Board board = new Board("aa");
        BoardDatabase.getInstance().addBoard(board);

        for (Issue issue : ProjectManager.getInstance().getIssuesByProject(project)) {
            BoardManager.getInstance().addIssueToBoard(board, issue);
        }

        kanbanBoardPanel = new KanbanBoardPanel(board, user);
        issueTrackerPanel = new IssueTrackerPanel(project, user);
        tabbedPane.addTab("Boards", kanbanBoardPanel);
        tabbedPane.addTab("Issues", issueTrackerPanel);
        tabbedPane.addTab("Reports", null);
    }
}
