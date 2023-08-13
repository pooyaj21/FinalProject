package Project.Ui;

import Project.Logic.DataBase.SQL.UserDataBaseSQL;
import Project.Logic.Role;
import Project.Logic.User;
import Project.Ui.User.UserPanel;

import javax.swing.*;

public class AppFrame extends JFrame {
    private static AppFrame instance;
    LoginPanel loginPanel = new LoginPanel();
    JLabel backgroundLabel;
    ProfileUi profileUi;
    SuperAdminPanel superAdminPanel;
    UserPanel userPanel;
    User user;

    int x = (1000 - 420) / 2;
    int y = (800 - 420) / 2;

    private AppFrame() {
        setTitle("App");
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        ImageIcon backgroundImage = new ImageIcon("Assets/loginBackground.jpeg");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        add(backgroundLabel);

        loginPanel.setBounds(x, y, loginPanel.getWidth(), loginPanel.getHeight());
        backgroundLabel.add(loginPanel);


    }

    public static AppFrame getInstance() {
        if (instance == null) {
            instance = new AppFrame();
        }
        return instance;
    }

    public void updateLoggedInUser(int loggedInUser) {
        this.user = UserDataBaseSQL.getInstance().getUserFromId(loggedInUser);
        hiddenEveryThing();
        if (user.getRole() == Role.SUPER_ADMIN) {
            superAdminPanel = new SuperAdminPanel();
            superAdminPanel.setBounds(0, 0, getWidth(), getHeight());
            add(superAdminPanel);
            repaint();
        } else {
            userPanel = new UserPanel(user);
            userPanel.setBounds(0, 0, getWidth(), getHeight());
            add(userPanel);
            repaint();
        }
    }

    public void profile() {
        profileUi = new ProfileUi(user);
        profileUi.setOpaque(false);
        profileUi.setBounds(0, 0, 1000, 800);
        profileUi.setVisible(true);
        add(profileUi);
        repaint();
    }

    public void hiddenEveryThing() {
        if (backgroundLabel != null) backgroundLabel.setVisible(false);
        if (profileUi != null) profileUi.setVisible(false);
        if (superAdminPanel != null) superAdminPanel.setVisible(false);
        if (userPanel != null) userPanel.setVisible(false);
    }


    public void logOut() {
        profileUi.setVisible(false);
        backgroundLabel.setVisible(false);
        loginPanel.userEmailField.setText("");
        loginPanel.userPasswordField.setText("");
        loginPanel.setVisible(true);
        backgroundLabel.setVisible(true);
        repaint();
    }
}
