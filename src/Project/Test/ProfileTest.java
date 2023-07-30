package Project.Test;

import Project.Logic.DataBase.UserDatabase;
import Project.Logic.DataBase.UserManagement;
import Project.Logic.Role;
import Project.Logic.User;
import Project.Ui.ProfileUi;


import javax.swing.*;

public class ProfileTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

    }
    private static void createAndShowGUI() {
        UserDatabase userDatabase = UserDatabase.getInstance();
        UserManagement userManagement = UserManagement.getInstance();
        User user = new User("p@p1.com", "p", "pooya1", Role.DEVELOPER);
        userManagement.makeAccount(user);
        ProfileUi profilePanel = new ProfileUi(UserDatabase.getInstance().getUsers().get(0));
        JFrame frame = new JFrame("User Profile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.add(profilePanel);
        frame.setVisible(true);

    }
}
