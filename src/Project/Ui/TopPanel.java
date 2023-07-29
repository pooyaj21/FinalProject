package Project.Ui;

import Project.Logic.Role;
import Project.Logic.User;
import Project.Util.CircleLabel;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {
    public TopPanel(User user) {
        setSize(1000, 100); // Set the height to 100 instead of 200
        setLayout(null);
        setBackground(new Color(0xBEBEBE));

        JLabel nameLabel = new JLabel(user.getFullName());
        nameLabel.setBounds(70, 15, 100, 25); // Adjust the y-position to 15
        String roleName = "";
        for (Role role : Role.values()) {
            switch (user.getRole()) {
                case SUPER_ADMIN:
                    roleName = "Super Admin";
                    break;
                case PROJECT_OWNER:
                    roleName = "Project Owner";
                    break;
                case DEVELOPER:
                    roleName = "Developer";
                    break;
                case QA:
                    roleName = "QA";
                    break;
            }
        }
        JLabel roleLabel = new JLabel(roleName);
        roleLabel.setBounds(70, 40, 100, 25); // Adjust the y-position to 40

        CircleLabel profile = new CircleLabel(user.getFullName());
        profile.setBounds(10, 20, 50, 50); // Adjust the y-position to 20

        JButton settingButton = new JButton("⋮");
        settingButton.setFont(new Font(null, Font.PLAIN, 30));
        settingButton.setContentAreaFilled(false);
        settingButton.setBorder(null);
        settingButton.setBounds(900, 20, 40, 40); // Adjust the y-position to 20

        add(profile);
        add(nameLabel);
        add(roleLabel);
        add(settingButton);
    }
}
