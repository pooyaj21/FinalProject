package Project.Test;

import Project.Logic.Role;
import Project.Logic.User;
import Project.Ui.TopPanel;
import Project.Ui.UserManagementPanel;

import javax.swing.*;

public class TopPanelTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Top panel");
        User user = new User("p@p1.com", "p", "pooya1", Role.DEVELOPER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,100);
        frame.setLocationRelativeTo(null);
        TopPanel topPanel = new TopPanel(user);
        frame.add(topPanel);
        frame.setVisible(true);

    }
}
