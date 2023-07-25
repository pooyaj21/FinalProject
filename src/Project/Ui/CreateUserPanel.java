package Project.Ui;

import Project.Logic.Role;
import Project.Logic.User;
import Project.Logic.UserDataBase;
import Project.Logic.UserManagement;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUserPanel extends JPanel {
    JLabel emailLabel = new JLabel("Email:");
    JLabel nameLabel = new JLabel("Name:");
    JLabel passwordLabel = new JLabel("Password:");
    JLabel roleLabel = new JLabel("Role:");
    JTextField emailField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField passwordField = new JTextField();
    JComboBox<String> roleComboBox = new JComboBox<>();
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    JLabel emailErrorLabel = new JLabel();
    JLabel nameErrorLabel = new JLabel();
    JLabel passwordErrorLabel = new JLabel();


    public CreateUserPanel(int x, int y) {
        setBounds(x, y, 600, 600);
        setLayout(null);
        emailLabel.setBounds(75, 100, 75, 25);
        nameLabel.setBounds(75, 200, 75, 25);
        passwordLabel.setBounds(75, 300, 75, 25);
        roleLabel.setBounds(75, 400, 75, 25);

        emailErrorLabel.setBounds(150, 125, 200, 25);
        emailErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        nameErrorLabel.setBounds(150, 225, 200, 25);
        nameErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        passwordErrorLabel.setBounds(150, 325, 200, 25);
        passwordErrorLabel.setFont(new Font(null, Font.ITALIC, 10));

        emailField.setBounds(150, 100, 200, 25);
        nameField.setBounds(150, 200, 200, 25);
        passwordField.setBounds(150, 300, 200, 25);
        roleComboBox.setBounds(150, 400, 200, 25);
        for (int i = 1; i < Role.values().length; i++) {
            String name = null;
            switch (Role.values()[i]) {
                case PROJECT_OWNER:
                    name = "Project Owner";
                    break;
                case DEVELOPER:
                    name = "Developer";
                    break;
                case QA:
                    name = "QA";
                    break;
            }
            roleComboBox.addItem(name);
        }

        submit.setBounds(400, 500, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emailErrorLabel.setText("");
                nameErrorLabel.setText("");
                passwordErrorLabel.setText("");

                if (emailField.getText().isEmpty()) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("Enter Email");
                } else if (!emailField.getText().toLowerCase().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("Enter a Correct Email");
                } else if (UserManagement.getInstance().isEmailExist(emailField.getText().toLowerCase())) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("This Email already Exist");
                }
                if (nameField.getText().isEmpty()) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setText("Enter Name");
                }
                if (passwordField.getText().isEmpty()) {
                    passwordErrorLabel.setForeground(Color.red);
                    passwordErrorLabel.setText("Enter Password");
                }
                //TODO:handel all situations
                UserManagement.getInstance().makeAccount(new User(emailField.getText().toLowerCase(),passwordField.getText()
                ,nameField.getText(),Role.values()[roleComboBox.getSelectedIndex()+1]));
                System.out.println(UserDataBase.getInstance().getLoginInfo());
            }
        });


        add(emailLabel);
        add(nameLabel);
        add(passwordLabel);
        add(roleLabel);

        add(emailErrorLabel);
        add(nameErrorLabel);
        add(passwordErrorLabel);

        add(emailField);
        add(nameField);
        add(passwordField);
        add(roleComboBox);

        add(submit);
    }
}
