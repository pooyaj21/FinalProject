package Project.Ui;

import Project.Logic.DataBase.UserDatabase;
import Project.Logic.DataBase.UserManagement;
import Project.Logic.Project;
import Project.Util.GeneralController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuperAdminPanel extends JPanel {
    JButton userManagementButton = new JButton("Users Management");
    JButton projectManagementButton = new JButton("Projects Management");
    UserManagementPanel userManagementPanel;
    ProjectManagementPanel projectManagementPanel;
    boolean isUserManagementPanelClicked = false;
    boolean isProjectManagementPanelClicked= false;
    ProjectPanel projectPanel;

    public SuperAdminPanel() {
        setLayout(null);
        setSize(1000,700);



        TopPanel topPanel =new TopPanel(UserDatabase.getInstance().getUsers().get(0));
        topPanel.setBounds(0,0,getWidth(),100);

        ImageIcon userIcon = new ImageIcon("Assets/Pfp.png");
        ImageIcon projectIcon = new ImageIcon("Assets/FolderPic.png");

        userManagementButton.setIcon(GeneralController.getInstance().resizeIcon(userIcon, 30, 30));
        userManagementButton.setBounds(0,100,200,50);
        userManagementButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        userManagementButton.setHorizontalAlignment(SwingConstants.LEFT);

        projectManagementButton.setIcon(GeneralController.getInstance().resizeIcon(projectIcon, 30, 30));
        projectManagementButton.setBounds(0,150,200,50);
        projectManagementButton.setHorizontalTextPosition(SwingConstants.RIGHT);
        projectManagementButton.setHorizontalAlignment(SwingConstants.LEFT);

        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isUserManagementPanelClicked) {
                    isUserManagementPanelClicked=true;
                    isProjectManagementPanelClicked=false;
                    if (projectManagementPanel != null) projectManagementPanel.setVisible(false);
                    if (projectPanel != null) projectPanel.setVisible(false);
                    userManagementPanel = new UserManagementPanel();
                    userManagementPanel.setBounds(201, 100, getWidth(), getHeight());
                    add(userManagementPanel);
                    userManagementPanel.setVisible(true);
                    projectManagementButton.setBackground(null);
                    userManagementButton.setContentAreaFilled(true);
                    userManagementButton.setBackground(Color.gray);
                    repaint();
                }
            }
        });
        userManagementButton.setContentAreaFilled(false);
        userManagementButton.setBorder(null);

        projectManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isProjectManagementPanelClicked) {
                    isUserManagementPanelClicked=false;
                    isProjectManagementPanelClicked=true;
                    if (userManagementPanel != null) userManagementPanel.setVisible(false);
                    if (projectPanel != null) projectPanel.setVisible(false);
                    projectManagementPanel = new ProjectManagementPanel(SuperAdminPanel.this);
                    projectManagementPanel.setBounds(201, 100, getWidth(), getHeight());
                    add(projectManagementPanel);
                    projectManagementPanel.setVisible(true);
                    userManagementButton.setBackground(null);
                    projectManagementButton.setContentAreaFilled(true);
                    projectManagementButton.setBackground(Color.gray);
                    repaint();
                }
            }
        });
        projectManagementButton.setContentAreaFilled(false);
        projectManagementButton.setBorder(null);




        add(topPanel);

        add(userManagementButton);
        add(projectManagementButton);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(200, 99, 200, getHeight());
        g.drawLine(0, 99, getWidth(), 100);
        g.drawLine(0, 150, 200, 150);
        g.drawLine(0, 200, 200, 200);
    }

    public void showProjectPanel(Project project){
        projectManagementPanel.setVisible(false);
        projectPanel=new ProjectPanel(project, UserDatabase.getInstance().getUsers().get(0));
        projectPanel.setBounds(201, 100, getWidth(), getHeight());
        projectPanel.kanbanButton.doClick();
        add(projectPanel);
        isProjectManagementPanelClicked=false;
    }
}
