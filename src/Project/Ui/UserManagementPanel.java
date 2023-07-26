package Project.Ui;

import Project.Logic.User;
import Project.Logic.UserDataBase;
import Project.Logic.UserManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class UserManagementPanel extends JPanel {
    CreateUserPanel createUserPanel;
    EditUserPanel editUserPanel;
    UserDataBase userDataBase = UserDataBase.getInstance();
    JPanel userPanel= new JPanel(){
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < userDataBase.getUsers().size()-1; i++) {
                g.drawLine(0, (100*(i+1)), 200, (100*(i+1)));
            }
        }};
    JScrollPane userScrollPane;

    private static UserManagementPanel instance;
    private UserManagementPanel(int x, int y) {
        setBounds(x, y, 800, 600);
        setLayout(null);


        userPanel.setBounds(0,50,199,getHeight());
        userPanel.setLayout(null);
        add(userPanel);

        userScrollPane = new JScrollPane(userPanel);
        userScrollPane.setBounds(0, 50, 199, getHeight());
        userScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(userScrollPane);

        createUserPanel = new CreateUserPanel(200,0);
        createUserPanel.setVisible(false);
        add(createUserPanel);

        editUserPanel = new EditUserPanel(200,0);
        editUserPanel.setVisible(false);
        add(editUserPanel);

        JButton button = new JButton("Add+");
        button.setBounds(0, 0, 200, 50);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserPanel.setVisible(true);
                editUserPanel.setVisible(false);
            }
        });

        add(button);

        drawUsers();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(200, 0, 200, getHeight());
        g.drawLine(0, 50, 200, 50);
    }
    public static UserManagementPanel getInstance(int x, int y) {
        if (instance == null) {
            instance = new UserManagementPanel(x,y);
        }
        return instance;
    }



    private void drawUserButton(User user, int index) {
        JButton userButton = new JButton();
        userButton.setBounds(0, (100 * index), 200, 100);
        userButton.setText(user.getFullName());
        userButton.setContentAreaFilled(false);
        userButton.setBorder(null);
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserPanel.setVisible(false);
                editUserPanel.setUser(user);
                editUserPanel.update();
                editUserPanel.setVisible(true);
            }
        });
        userPanel.add(userButton);
    }

    public void drawUsers() {
        userPanel.removeAll(); // Remove all existing buttons before adding new ones
        userPanel.setBounds(getX(),getY(),getWidth(),getHeight()+100);

        for (int i = 0; i < userDataBase.getUsers().size() - 1; i++) {
            User user = userDataBase.getUsers().get(i + 1);
            drawUserButton(user, i);
        }

        // Repaint the userPanel and update the scroll pane
        userPanel.revalidate();
        userPanel.repaint();
    }
}
