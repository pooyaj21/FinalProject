package Project;


import Project.Ui.UserManagementPanel;

import javax.swing.*;

public class UserManagementTest {
    public static void main(String[] args) {
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
        UserManagementPanel userManagement = new UserManagementPanel(0,0);
        userManagement.setVisible(true);
        frame.add(userManagement);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
