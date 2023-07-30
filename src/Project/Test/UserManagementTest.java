package Project.Test;


import Project.Logic.DataBase.ProjectManager;
import Project.Logic.Role;
import Project.Logic.User;
import Project.Logic.DataBase.UserManagement;
import Project.Ui.UserManagementPanel;

import javax.swing.*;

public class UserManagementTest {
    public static void main(String[] args) {
        UserManagement userManagement = UserManagement.getInstance();
        userManagement.makeAccount(new User("p@p1.com", "p", "pooya1", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p2.com", "p", "pooya2", Role.PROJECT_OWNER));
        userManagement.makeAccount(new User("p@p3.com", "p", "pooya3", Role.QA));
        userManagement.makeAccount(new User("p@p4.com", "p", "pooya4", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p5.com", "p", "pooya5", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p6.com", "p", "pooya6", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p7.com", "p", "pooya7", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p8.com", "p", "pooya8", Role.DEVELOPER));

        ProjectManager projectManager =ProjectManager.getInstance();
        projectManager.createProject("a");
        projectManager.createProject("b");
        projectManager.createProject("c");
        projectManager.createProject("d");
        projectManager.createProject("e");


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("User Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,700);
        frame.setLocationRelativeTo(null);
        UserManagementPanel userManagementPanel = new UserManagementPanel();
        frame.add(userManagementPanel);
        frame.setVisible(true);

    }
}
