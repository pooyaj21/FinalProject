package Project;


import Project.Logic.Role;
import Project.Logic.User;
import Project.Logic.UserDataBase;
import Project.Logic.UserManagement;
import Project.Ui.UserManagementPanel;

import javax.swing.*;

public class UserManagementTest {
    public static void main(String[] args) {
        UserManagement.getInstance().makeAccount(new User("p@p1.com","p","pooya1", Role.DEVELOPER));
        UserManagement.getInstance().makeAccount(new User("p@p2.com","p","pooya2", Role.PROJECT_OWNER));
        UserManagement.getInstance().makeAccount(new User("p@p3.com","p","pooya3", Role.QA));
        UserManagement.getInstance().makeAccount(new User("p@p4.com","p","pooya4", Role.DEVELOPER));
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("user Management UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        UserManagementPanel userManagement = UserManagementPanel.getInstance(0,0);
        userManagement.setVisible(true);
        frame.add(userManagement);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
