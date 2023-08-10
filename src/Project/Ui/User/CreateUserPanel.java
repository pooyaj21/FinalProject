package Project.Ui.User;

import Project.Logic.DataBase.SQL.UserDataBaseSQL;
import Project.Logic.Role;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
    JLabel roleErrorLabel = new JLabel();

    public CreateUserPanel(UserManagementPanel userManagementPanel) {
        setSize( 600, 600);
        setLayout(null);
        setFocusable(true);
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
        roleErrorLabel.setBounds(150, 425, 200, 25);
        roleErrorLabel.setFont(new Font(null, Font.ITALIC, 10));


        emailField.setBounds(150, 100, 200, 25);
        nameField.setBounds(150, 200, 200, 25);
        passwordField.setBounds(150, 300, 200, 25);
        roleComboBox.setBounds(150, 400, 200, 25);

        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);
        ActionMap actionMap = getActionMap();

        String enterKey = "enterKey";
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), enterKey);
        actionMap.put(enterKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit.doClick();
            }
        });

        for (Role role:Role.values()) {
            String roleName = "";
            switch (role) {
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
            roleComboBox.addItem(roleName);
        }

        submit.setBounds(400, 500, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emailErrorLabel.setText("");
                nameErrorLabel.setText("");
                passwordErrorLabel.setText("");
                roleErrorLabel.setText("");
                boolean isEmailFine = false;
                boolean isNameFine = false;
                boolean isPasswordFine = false;
                boolean isRoleFine = false;

                if (GeneralController.getInstance().isEmpty(emailField.getText())) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("Enter Email");
                } else if (!GeneralController.emailAuthentication(emailField.getText().toLowerCase())) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("Enter a Correct Email");
                } else if (UserDataBaseSQL.getInstance().isEmailExist(emailField.getText().toLowerCase())) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("This Email already Exist");
                } else isEmailFine = true;
                if (GeneralController.getInstance().isEmpty(nameField.getText())) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setText("Enter Name");
                } else isNameFine = true;
                if (GeneralController.getInstance().isEmpty(passwordField.getText())) {
                    passwordErrorLabel.setForeground(Color.red);
                    passwordErrorLabel.setText("Enter Password");
                } else isPasswordFine = true;
                if(GeneralController.getInstance().isEmpty(roleComboBox.getSelectedItem().toString())){
                    roleErrorLabel.setForeground(Color.red);
                    roleErrorLabel.setText("Select a role");
                }else isRoleFine=true;
                if (isEmailFine && isNameFine && isPasswordFine&&isRoleFine) {
                    UserDataBaseSQL.getInstance().addUser(nameField.getText(),emailField.getText()
                            ,passwordField.getText(),Role.values()[roleComboBox.getSelectedIndex()]);
                    userManagementPanel.repaint();
                    userManagementPanel.drawUsers();
                    emailField.setText("");
                    passwordField.setText("");
                    nameField.setText("");
                    roleComboBox.setSelectedIndex(0);
                }
            }
        });


        add(emailLabel);
        add(nameLabel);
        add(passwordLabel);
        add(roleLabel);

        add(emailErrorLabel);
        add(nameErrorLabel);
        add(passwordErrorLabel);
        add(roleErrorLabel);


        add(emailField);
        add(nameField);
        add(passwordField);
        add(roleComboBox);

        add(submit);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submit.doClick();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
}
