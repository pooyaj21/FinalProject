package Project.Ui;

import Project.Logic.User;

import javax.swing.*;

public class AppFrame extends JFrame {
    private static AppFrame instance;
    LoginPanel loginPanel = new LoginPanel();
    ProfileUi profileUi;
    TopPanel topPanel;

    int x = (1000 - 420) / 2;
    int y = (800 - 420) / 2;

    private AppFrame() {
        setTitle("App");
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);


        loginPanel.setBounds(x, y, loginPanel.getWidth(), loginPanel.getHeight());
        add(loginPanel);


    }

    public static AppFrame getInstance() {
        if (instance == null) {
            instance = new AppFrame();
        }
        return instance;
    }

    public void updateLoggedInUser(User loggedInUser) {
        profileUi = new ProfileUi(loggedInUser);
        profileUi.setBounds(0, 100, 1000, 700);
        profileUi.setVisible(true);
        add(profileUi);
        topPanel = new TopPanel(loggedInUser);
        topPanel.setBounds(0, 0, 1000, 100);
        topPanel.setVisible(true);
        add(topPanel);
        repaint();
    }

    public void logOut(){
        profileUi.setVisible(false);
        topPanel.setVisible(false);
        loginPanel.userEmailField.setText("");
        loginPanel.userPasswordField.setText("");
        loginPanel.setVisible(true);
        repaint();
    }
}
