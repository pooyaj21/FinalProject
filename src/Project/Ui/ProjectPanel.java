package Project.Ui;

import Project.Logic.Project;
import Project.Logic.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectPanel extends JPanel {
    public ProjectPanel(User user ,Project project) {
        setSize(800,700);
        setLayout(null);

        JButton settingButton = new JButton("⋮");
        settingButton.setFont(new Font(null, Font.PLAIN, 30));
        settingButton.setContentAreaFilled(false);
        settingButton.setBorder(null);
        settingButton.setBounds(700, 20, 40, 40);
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        settingButton.setVisible(user.getRole().getLevelOfAccess() > 2);
        add(settingButton);
    }
}
