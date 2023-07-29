package Project.Ui;


import Project.Logic.DataBase.UserDatabase;
import Project.Logic.DataBase.UserManagement;
import Project.Logic.User;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    RoundedButton loginButton = new RoundedButton("Login",15,Color.blue,Color.white,12);
    JTextField userEmailField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JLabel userEmailLabel = new JLabel("Email :");
    JLabel userPasswordLabel = new JLabel("Password:");
    JLabel messageLabel = new JLabel();
    JLabel emailErrorLabel = new JLabel();
    JLabel passwordErrorLabel = new JLabel();

    public LoginPanel() {
        setSize(420,420);
        userEmailLabel.setBounds(50, 100, 75, 25);
        userPasswordLabel.setBounds(50, 150, 75, 25);

        messageLabel.setBounds(125, 250, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 25));

        userEmailField.setBounds(125, 100, 200, 25);
        emailErrorLabel.setBounds(125,125,200,20);
        emailErrorLabel.setFont(new Font(null, Font.ITALIC, 10));

        userPasswordField.setBounds(125, 150, 200, 25);
        passwordErrorLabel.setBounds(125,175,200,20);
        passwordErrorLabel.setFont(new Font(null, Font.ITALIC, 10));

        loginButton.setBounds(125, 200, 100, 25);
        loginButton.setFocusable(false);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabel.setText("");
                emailErrorLabel.setText("");
                passwordErrorLabel.setText("");
                if (GeneralController.getInstance().isEmpty(userEmailField.getText())){
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("Enter Email");
                }else{
                    try{
                        UserManagement.getInstance().checkEmail(userEmailField.getText().toLowerCase());
                    }catch (IllegalArgumentException exception){
                        emailErrorLabel.setForeground(Color.red);
                        emailErrorLabel.setText("Wrong Email");
                    }
                }
                if (GeneralController.getInstance().isEmpty(userPasswordField.getText())){
                    passwordErrorLabel.setForeground(Color.red);
                    passwordErrorLabel.setText("Enter Password");
                    return;
                }else {
                    try {
                        UserManagement.getInstance().checkPassword(userEmailField.getText(), userPasswordField.getText());
                    } catch (Exception exception) {
                        passwordErrorLabel.setForeground(Color.red);
                        passwordErrorLabel.setText("Wrong Password");
                        return;
                    }
                }
                AppFrame.getInstance().loginPanel.setVisible(false);
                User selectedUser = UserManagement.getInstance().getUser(userEmailField.getText().toLowerCase());
                AppFrame.getInstance().updateLoggedInUser(selectedUser);
            }
        });


        add(userEmailLabel);
        add(userPasswordLabel);
        add(messageLabel);
        add(emailErrorLabel);
        add(passwordErrorLabel);
        add(userEmailField);
        add(userPasswordField);
        add(loginButton);
        setLayout(null);
        setVisible(true);
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);
        ActionMap actionMap = getActionMap();

        String enterKey = "enterKey";
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), enterKey);
        actionMap.put(enterKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButton.doClick();
            }
        });

    }
}
