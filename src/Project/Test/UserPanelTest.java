package Project.Test;

import Project.Logic.DataBase.ProjectDatabase;
import Project.Logic.DataBase.ProjectManager;
import Project.Logic.DataBase.UserDatabase;
import Project.Logic.DataBase.UserManagement;
import Project.Logic.Role;
import Project.Logic.User;
import Project.Ui.UserPanel;

import javax.swing.*;

public class UserPanelTest {
    public static void main(String[] args) {
        UserManagement userManagement = UserManagement.getInstance();
        userManagement.makeAccount(new User("p@p1.com", "p", "pooya1", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p2.com", "p", "pooya2", Role.PROJECT_OWNER));
        userManagement.makeAccount(new User("p@p3.com", "p", "pooya3", Role.QA));
        userManagement.makeAccount(new User("p@p4.com", "p", "pooya4", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p5.com", "p", "pooya5", Role.PROJECT_OWNER));
        userManagement.makeAccount(new User("p@p6.com", "p", "pooya6", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p7.com", "p", "pooya7", Role.PROJECT_OWNER));
        userManagement.makeAccount(new User("p@p8.com", "p", "pooya8", Role.DEVELOPER));

        ProjectManager projectManager =ProjectManager.getInstance();
        projectManager.createProject("a");
        projectManager.createProject("b");
        projectManager.createProject("c");
        projectManager.createProject("d");
        projectManager.createProject("e");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("User Panel");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ProjectManager.getInstance().addMemberToProject(ProjectDatabase.getInstance().getProjects().get(2)
                        ,UserDatabase.getInstance().getUsers().get(1));
                ProjectManager.getInstance().addMemberToProject(ProjectDatabase.getInstance().getProjects().get(3)
                        ,UserDatabase.getInstance().getUsers().get(1));

                UserPanel userPanel = new UserPanel(UserDatabase.getInstance().getUsers().get(1));
                frame.add(userPanel);

                frame.setSize(1000, 800);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}