package Project.Ui;


import Project.Logic.DataBase.SQL.UserDataBaseSQL;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;
import Project.Util.RoundedTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class LoginPanel extends JPanel {
    JLabel loginLabel = new JLabel("Log in");
    RoundedButton loginButton = new RoundedButton("Login",15,new Color(0x247ef0),Color.white,12);
    RoundedTextField  userEmailField = new RoundedTextField(100,15,Color.white,Color.BLACK,12);
    JPasswordField userPasswordField = new JPasswordField() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            Shape shape = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, 15, 15);

            g2.setColor(Color.white);
            g2.fill(shape);
            g2.setColor(Color.white);
            g2.draw(shape);

            super.paintComponent(g2);
            g2.dispose();
        }
    };
    JLabel userEmailLabel = new JLabel("Email :");
    JLabel userPasswordLabel = new JLabel("Password:");
    JLabel messageLabel = new JLabel();
    JLabel emailErrorLabel = new JLabel();
    JLabel passwordErrorLabel = new JLabel();

    public LoginPanel() {
        setSize(420,420);

        loginLabel.setBounds(50,50,100,100);
        loginLabel.setFont(new Font(null, Font.PLAIN, 30));

        userEmailLabel.setBounds(50, 175, 75, 25);
        userPasswordLabel.setBounds(50, 225, 75, 25);

        messageLabel.setBounds(125, 325, 250, 35);
        messageLabel.setFont(new Font(null, Font.ITALIC, 25));

        userEmailField.setBounds(125, 175, 200, 25);
        emailErrorLabel.setBounds(125,200,200,20);
        emailErrorLabel.setFont(new Font(null, Font.ITALIC, 10));

        userPasswordField.setBounds(125, 225, 200, 25);
        userPasswordField.setOpaque(false);
        userPasswordField.setForeground(Color.BLACK);
        userPasswordField.setCaretColor(Color.BLACK);
        userPasswordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        passwordErrorLabel.setBounds(125,250,200,20);
        passwordErrorLabel.setFont(new Font(null, Font.ITALIC, 10));

        loginButton.setBounds(225, 275, 100, 25);
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
                        UserDataBaseSQL.getInstance().getUserIdByEmail(userEmailField.getText().toLowerCase());
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
                   if (!UserDataBaseSQL.getInstance().doesEmailAndPasswordMatch(userEmailField.getText().toLowerCase(),userPasswordField.getText())) {
                       passwordErrorLabel.setForeground(Color.red);
                       passwordErrorLabel.setText("Wrong Password");
                       return;
                   }
                }
                AppFrame.getInstance().loginPanel.setVisible(false);
                int selectedUserId = UserDataBaseSQL.getInstance().getUserIdByEmail(userEmailField.getText().toLowerCase());
                AppFrame.getInstance().updateLoggedInUser(selectedUserId);
            }
        });

        setOpaque(false);

        add(loginLabel);
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
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(20, 20);
        int width = getWidth();
        int height = getHeight();

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw background
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

        // Draw border
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }
}
