package Project.Ui;

import Project.Logic.UserDataBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class UserManagementPanel extends JPanel {
    CreateUserPanel createUserPanel;
    public UserManagementPanel(int x, int y) {
        setBounds(x, y, 800, 600);
        setLayout(null);
        createUserPanel = new CreateUserPanel(200,0);
        createUserPanel.setVisible(false);
        add(createUserPanel);

        JButton button = new JButton("Add+");
        button.setBounds(0, 0, 200, 50);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserPanel.setVisible(true);
            }
        });

        add(button);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(200, 0, 200, getHeight());
        g.drawLine(0, 50, 200, 50);
        for (int i = 0; i < UserDataBase.getInstance().getLoginInfo().size()-1; i++) {
            g.drawLine(0, 50+(100*(i+1)), 200, 50+(100*(i+1)));
        }
    }
}
