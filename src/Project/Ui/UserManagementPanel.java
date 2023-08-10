package Project.Ui;


import Project.Logic.DataBase.SQL.UserDataBaseSQL;
import Project.Logic.Role;
import Project.Logic.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserManagementPanel extends JPanel {
    CreateUserPanel createUserPanel;
    EditUserPanel editUserPanel;
    int selectedUserIndex = -1;
    JPanel userPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < UserDataBaseSQL.getInstance().getAllUsers().size() - 1; i++) {
                g.drawLine(0, (100 * (i + 1)), 200, (100 * (i + 1)));
            }
        }
    };
    JScrollPane userScrollPane;
    JButton userButton;

    public UserManagementPanel() {
        setSize( 800, 700);
        setLayout(null);


        userPanel.setBounds(0,50,199,getHeight());
        userPanel.setLayout(null);
        userPanel.setPreferredSize(new Dimension(199, getHeight()));
        add(userPanel);

        userScrollPane = new JScrollPane(userPanel);
        userScrollPane.setBounds(0, 50, 199, getHeight()-50);
        userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(userScrollPane, BorderLayout.CENTER);

        createUserPanel = new CreateUserPanel(this);
        createUserPanel.setBounds(200,0,getWidth(),getHeight());
        createUserPanel.setVisible(false);
        add(createUserPanel);

        editUserPanel = new EditUserPanel(200, 0,this);
        editUserPanel.setVisible(false);
        add(editUserPanel);

        JButton button = new JButton("Add+");
        button.setBounds(0, 0, 200, 50);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedUserIndex=-1;
                drawUsers();
                createUserPanel.setVisible(true);
                editUserPanel.setVisible(false);
            }
        });
        add(button, BorderLayout.NORTH); // Add the "Add+" button in the north

        drawUsers(); // Draw the user buttons

        setFocusable(true);
        requestFocusInWindow();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(199, 0, 199, getHeight());
        g.drawLine(0, 50, 200, 50);
    }
    private void drawUserButton(User user, int index) {
        userButton = new JButton();
        userButton.setBounds(0, (100 * index), 200, 100);
        userButton.setText(user.getFullName());
        userButton.setContentAreaFilled(false);
        userButton.setBorder(null);

        if (index == selectedUserIndex) {
            userButton.setContentAreaFilled(true);
            userButton.setBackground(Color.GRAY);
        } else {
            userButton.setBackground(null);
        }

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserPanel.setVisible(false);
                editUserPanel.setUser(user);
                editUserPanel.update();
                editUserPanel.setVisible(true);

                // Update the selected user index when a button is clicked
                selectedUserIndex = index;

                // Redraw the userPanel to update the button colors
                drawUsers();
            }
        });
        userPanel.add(userButton);
    }

    public void drawUsers() {
        userPanel.removeAll();
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < UserDataBaseSQL.getInstance().getAllUsers().size(); i++) {
            User user = UserDataBaseSQL.getInstance().getAllUsers().get(i);
            if (user.getRole()!= Role.SUPER_ADMIN) users.add(user);
        }
        for (int i = 0; i < users.size(); i++) drawUserButton(users.get(i),i);
        // Repaint the userPanel and update the scroll pane
        userPanel.revalidate();
        userPanel.repaint();
        userPanel.setPreferredSize(new Dimension(199, UserDataBaseSQL.getInstance().getAllUsers().size() * 100));
        if (UserDataBaseSQL.getInstance().getAllUsers().size() > 6) {
            userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
        userScrollPane.revalidate();
    }

}
