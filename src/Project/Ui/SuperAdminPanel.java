package Project.Ui;

import Project.Logic.DataBase.UserDatabase;

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

    public SuperAdminPanel() {
        setLayout(null);
        setSize(1000,700);



        TopPanel topPanel =new TopPanel(UserDatabase.getInstance().getUsers().get(0));
        topPanel.setBounds(0,0,getWidth(),100);


        userManagementButton.setBounds(0,100,200,100);
        projectManagementButton.setBounds(0,200,200,100);

        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isUserManagementPanelClicked) {
                    isUserManagementPanelClicked=true;
                    isProjectManagementPanelClicked=false;
                    if (projectManagementPanel != null) projectManagementPanel.setVisible(false);
                    userManagementPanel = new UserManagementPanel();
                    userManagementPanel.setBounds(200, 100, getWidth(), getHeight());
                    add(userManagementPanel);
                    userManagementPanel.setVisible(true);
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
                    projectManagementPanel = new ProjectManagementPanel();
                    projectManagementPanel.setBounds(200, 100, getWidth(), getHeight());
                    add(projectManagementPanel);
                    projectManagementPanel.setVisible(true);
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
        g.drawLine(0, 200, 200, 200);
        g.drawLine(0, 300, 200, 300);
    }
}
