package Project;

import Project.Ui.LoginPanel;

import javax.swing.*;

public class LoginTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Login UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLocationRelativeTo(null);
        JPanel login = new LoginPanel();
        login.setVisible(true);
        login.setBounds(0,0,420,420);
        frame.add(login);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
