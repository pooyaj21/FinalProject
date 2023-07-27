package Project.Ui;

import Project.Logic.DataBase.UserDatabase;
import Project.Logic.DataBase.UserManagement;
import Project.Logic.Role;
import Project.Logic.User;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileUi extends JPanel {
    private UserManagement userManagement = UserManagement.getInstance();

    private JLabel nameLabel = new JLabel("Name:");
    private JLabel emailLabel = new JLabel("Email:");
    private JLabel passwordLabel = new JLabel("Password:");
    private JLabel roleLabel = new JLabel("Role:");
    private JLabel nameField = new JLabel();
    private JTextField emailLField = new JTextField();
    private JTextField passwordField = new JTextField();
    private JLabel roleLabelField = new JLabel();
    private RoundedButton submitButton = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    private JLabel emailErrorLabel = new JLabel();
    private JLabel passwordErrorLabel = new JLabel();
    private JLabel changeSuccessfulLabel = new JLabel("Change Successful");
    private RoundedButton logoutButton = new RoundedButton("Log Out", 12, Color.red, Color.white, 12);

    private String roleName = "";

    public ProfileUi(User user) {
        setLayout(null);

        int centerX = (1000 - 400) / 2;
        int centerY = (600 - 300) / 2;

        nameLabel.setBounds(centerX, centerY - 50, 100, 25);
        emailLabel.setBounds(centerX, centerY + 50, 100, 25);
        emailErrorLabel.setBounds(centerX + 100, centerY + 75, 200, 25);
        emailErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        passwordLabel.setBounds(centerX, centerY + 150, 100, 25);
        passwordErrorLabel.setBounds(centerX + 100, centerY + 175, 200, 25);
        passwordErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        roleLabel.setBounds(centerX, centerY + 250, 100, 25);
        changeSuccessfulLabel.setBounds(centerX + 400, centerY + 330, 150, 25);
        changeSuccessfulLabel.setForeground(Color.green);
        changeSuccessfulLabel.setFont(new Font(null, Font.ITALIC, 10));
        logoutButton.setBounds(900, 20, 80, 30);

        nameField.setBounds(centerX + 100, centerY - 50, 200, 25);
        emailLField.setBounds(centerX + 100, centerY + 50, 200, 25);
        passwordField.setBounds(centerX + 100, centerY + 150, 200, 25);
        roleLabelField.setBounds(centerX + 100, centerY + 250, 200, 25);

        submitButton.setBounds(centerX + 400, centerY + 300, 100, 30);

        nameField.setText(user.getFullName());
        emailLField.setText(user.getEmail());
        passwordField.setText(user.getPassword());
        changeSuccessfulLabel.setVisible(false);

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
                emailErrorLabel.setText("");
                passwordErrorLabel.setText("");
                boolean isEmailFine = false;
                boolean isPasswordFine = false;

                if (GeneralController.getInstance().isEmpty(emailLField.getText())) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("Enter Email");
                } else if (!UserManagement.getInstance().emailAuthentication(emailLField.getText().toLowerCase())) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("Enter a Correct Email");
                } else if (UserManagement.getInstance().isEmailExist(emailLField.getText().toLowerCase())) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("This Email already Exist");
                } else isEmailFine = true;
                if (GeneralController.getInstance().isEmpty(passwordField.getText())) {
                    passwordErrorLabel.setForeground(Color.red);
                    passwordErrorLabel.setText("Enter Password");
                } else isPasswordFine = true;
                if (isEmailFine && isPasswordFine) {
                    if (!user.getEmail().equals(emailLField.getText())) {
                        userManagement.editUserEmail(user, emailLField.getText());
                    }
                    userManagement.editUserPassword(user, passwordField.getText());
                    changeSuccessfulLabel.setVisible(true);
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("loged out");
                System.exit(0);
                //TODO:add logout logic
            }
        });

        add(submitButton);
        add(logoutButton);

        add(nameLabel);
        add(emailLabel);
        add(emailErrorLabel);
        add(passwordLabel);
        add(passwordErrorLabel);
        add(roleLabel);
        add(changeSuccessfulLabel);

        add(nameField);
        add(emailLField);
        add(passwordField);
        add(roleLabelField);
    }
}
