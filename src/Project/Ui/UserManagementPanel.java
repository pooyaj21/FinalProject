package Project.Ui;

import Project.Logic.DataBase.ProjectDatabase;
import Project.Logic.DataBase.ProjectManager;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Logic.DataBase.UserDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserManagementPanel extends JPanel {
    private static UserManagementPanel instance;
    CreateUserPanel createUserPanel;
    EditUserPanel editUserPanel;
    UserDatabase userDataBase = UserDatabase.getInstance();
    int selectedUserIndex = -1;
    JPanel userPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < userDataBase.getUsers().size() - 1; i++) {
                g.drawLine(0, (100 * (i + 1)), 200, (100 * (i + 1)));
            }
        }
    };
    JScrollPane userScrollPane;
    JButton userButton;

    private UserManagementPanel(int x, int y) {
        setBounds(x, y, 800, 600);
        setLayout(null);


        userPanel.setBounds(0,50,199,getHeight());
        userPanel.setLayout(null);
        userPanel.setPreferredSize(new Dimension(199, 600));
        add(userPanel);

        userScrollPane = new JScrollPane(userPanel);
        userScrollPane.setBounds(0, 50, 199, getHeight());
        userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(userScrollPane, BorderLayout.CENTER);

        createUserPanel = new CreateUserPanel(200, 0);
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


    public static UserManagementPanel getInstance(int x, int y) {
        if (instance == null) {
            instance = new UserManagementPanel(x, y);
        }
        return instance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(200, 0, 200, getHeight());
        g.drawLine(0, 50, 200, 50);
    }

    @Override
    public Dimension getPreferredSize() {
        // Calculate the preferred size based on the width and number of users
        int preferredHeight = Math.max(getHeight(), userDataBase.getUsers().size() * 100 + 50);
        return new Dimension(800, preferredHeight);
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
            userButton.setBackground(null); // Set background to null to show the default color
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
        userPanel.removeAll(); // Remove all existing buttons before adding new ones

        for (int i = 1; i < userDataBase.getUsers().size(); i++) {
            User user = userDataBase.getUsers().get(i);
            drawUserButton(user, i-1);
        }

        // Repaint the userPanel and update the scroll pane
        userPanel.revalidate();
        userPanel.repaint();
        userPanel.setPreferredSize(new Dimension(199, userDataBase.getUsers().size() * 100));
        if (userDataBase.getUsers().size() > 6) {
            userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
        userScrollPane.revalidate();
    }

}
