package Project.Ui;

import Project.Logic.DataBase.SQL.UserDataBaseSQL;
import Project.Logic.Role;
import Project.Logic.User;
import Project.Util.CircleLabel;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileUi extends JPanel {

    private JLabel nameLabel = new JLabel("Name:");
    private JLabel emailLabel = new JLabel("Email:");
    private JLabel passwordLabel = new JLabel("Password:");
    private JLabel roleLabel = new JLabel("Role:");
    private JTextField nameField = new JTextField();
    private JLabel emailLField = new JLabel();
    private JTextField passwordField = new JTextField();
    private JLabel roleLabelField = new JLabel();
    private RoundedButton submitButton = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    private JLabel nameErrorLabel = new JLabel();
    private JLabel passwordErrorLabel = new JLabel();
    private JLabel changeSuccessfulLabel = new JLabel();
    private RoundedButton logoutButton = new RoundedButton("Log Out", 12, Color.red, Color.white, 12);
    private RoundedButton backButton = new RoundedButton("⬅",30,Color.red, Color.white, 12);

    private String roleName = "";
    public ProfileUi(User user) {
        setLayout(null);
        setSize(1000,800);

        int centerX = (1000 - 400) / 2;
        int centerY = ((800 - 300) / 2)+100;
        TopPanel topPanel = new TopPanel(user);
        topPanel.setBounds(0, 0, 1000, 100);
        topPanel.setVisible(true);
        add(topPanel);

        CircleLabel profile = new CircleLabel(user.getFullName());
        profile.setBounds(centerX+150, centerY-200, 50, 50);
        nameLabel.setBounds(centerX, centerY - 50, 100, 25);
        emailLabel.setBounds(centerX, centerY + 50, 100, 25);
        nameErrorLabel.setBounds(centerX + 100, centerY -25, 200, 25);
        nameErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        passwordLabel.setBounds(centerX, centerY + 150, 100, 25);
        passwordErrorLabel.setBounds(centerX + 100, centerY + 175, 200, 25);
        passwordErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        roleLabel.setBounds(centerX, centerY + 250, 100, 25);
        changeSuccessfulLabel.setBounds(centerX + 400, centerY + 330, 150, 25);
        changeSuccessfulLabel.setFont(new Font(null, Font.ITALIC, 10));
        logoutButton.setBounds(centerX+500, centerY -190, 80, 25);
        logoutButton.setFont(new Font(null, Font.PLAIN, 15));
        backButton.setBounds(centerX-170, centerY -180, 20, 20);
        backButton.setFont(new Font(null, Font.PLAIN, 15));

        nameField.setBounds(centerX + 100, centerY - 50, 200, 25);
        emailLField.setBounds(centerX + 100, centerY + 50, 200, 25);
        passwordField.setBounds(centerX + 100, centerY + 150, 200, 25);
        roleLabelField.setBounds(centerX + 100, centerY + 250, 200, 25);

        submitButton.setBounds(centerX + 400, centerY + 300, 100, 30);

        nameField.setText(user.getFullName());
        emailLField.setText(user.getEmail());
        passwordField.setText(user.getPassword());

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
        roleLabelField.setText(roleName);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setText("");
                passwordErrorLabel.setText("");
                changeSuccessfulLabel.setText("");
                boolean isNameFine = false;
                boolean isPasswordFine = false;

                boolean isNameChange = false;
                boolean isPasswordChange = false;

                String newName;
                String newPassword;

                if (GeneralController.getInstance().isEmpty(nameField.getText())) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setText("Enter Name");
                } else isNameFine = true;
                if (GeneralController.getInstance().isEmpty(passwordField.getText())) {
                    passwordErrorLabel.setForeground(Color.red);
                    passwordErrorLabel.setText("Enter Password");
                } else isPasswordFine = true;
                if (isNameFine && isPasswordFine) {
                    newName= nameField.getText();
                    newPassword= passwordField.getText();
                    if (!user.getFullName().equalsIgnoreCase(nameField.getText())) {
                        isNameChange=true;
                    }
                    if (!user.getPassword().equalsIgnoreCase(passwordField.getText())) {
                        isPasswordChange=true;
                    }
                    if (isNameChange||isPasswordChange) {
                        UserDataBaseSQL.getInstance().editUser(user.getId(),newName, user.getEmail(), newPassword,user.getRole());
                        changeSuccessfulLabel.setForeground(Color.green);
                        changeSuccessfulLabel.setText("Change Successful");
                        topPanel.nameLabel.setText(newName);
                    }else{
                        changeSuccessfulLabel.setForeground(Color.red);
                        changeSuccessfulLabel.setText("There is no Changes");
                    }
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getInstance().logOut();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppFrame.getInstance().hiddenEveryThing();
                AppFrame.getInstance().updateLoggedInUser(user.getId());
            }
        });

        add(submitButton);
        add(logoutButton);

        add(profile);
        add(nameLabel);
        add(emailLabel);
        add(nameErrorLabel);
        add(passwordLabel);
        add(passwordErrorLabel);
        add(roleLabel);
        add(changeSuccessfulLabel);
        add(backButton);

        add(nameField);
        add(emailLField);
        add(passwordField);
        add(roleLabelField);

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();
        String enterKey = "enterKey";
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), enterKey);
        actionMap.put(enterKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitButton.doClick();
            }
        });
    }
}
