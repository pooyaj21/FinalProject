package Project.Test;

import Project.Ui.SuperAdminPanel;

import javax.swing.*;

public class SuperAdminTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Super Admin Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);

            SuperAdminPanel superAdminPanel = new SuperAdminPanel();
            frame.add(superAdminPanel);

            frame.setVisible(true);
        });
    }
}
