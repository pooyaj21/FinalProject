package Project.Ui;

import Project.Logic.Role;
import Project.Logic.User;
import Project.Util.CircleLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopPanel extends JPanel {
    JLabel nameLabel;
    public TopPanel(User user) {
        setSize(1000, 100);
        setLayout(null);
        setBackground(new Color(0xBEBEBE));
        nameLabel= new JLabel(user.getFullName());
        nameLabel.setBounds(70, 15, 600, 25);
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
        roleLabel.setBounds(70, 40, 100, 25);

        CircleLabel profile = new CircleLabel(user.getFullName());
        profile.setBounds(10, 15, 50, 50);

        JButton settingButton = new JButton("⋮");
        settingButton.setFont(new Font(null, Font.PLAIN, 30));
        settingButton.setContentAreaFilled(false);
        settingButton.setBorder(null);
        settingButton.setBounds(900, 20, 40, 40);
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getInstance().hiddenEveryThing();
                AppFrame.getInstance().profile();
                AppFrame.getInstance().repaint();
            }
        });
        ImageIcon backgroundImage = new ImageIcon("Assets/topPanel.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());

        backgroundLabel.add(profile);
        backgroundLabel.add(nameLabel);
        backgroundLabel.add(roleLabel);
        backgroundLabel.add(settingButton);
        add(backgroundLabel);
    }

}
