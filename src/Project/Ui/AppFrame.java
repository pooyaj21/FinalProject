package Project.Ui;

import Project.Logic.DataBase.ProjectDatabase;
import Project.Logic.Role;
import Project.Logic.User;

import javax.swing.*;

public class AppFrame extends JFrame {
    private static AppFrame instance;
    LoginPanel loginPanel = new LoginPanel();
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
        this.user = loggedInUser;
        hiddenEveryThing();
        if (loggedInUser.getRole()== Role.SUPER_ADMIN){
            superAdminPanel = new SuperAdminPanel();
            superAdminPanel.setBounds(0,0,getWidth(),getHeight());
            add(superAdminPanel);
            repaint();
        }else {
            userPanel = new UserPanel(user);
            userPanel.setBounds(0,0,getWidth(),getHeight());
            add(userPanel);
            repaint();
        }
    }

    public void profile(){
        profileUi = new ProfileUi(user);
        profileUi.setBounds(0, 0, 1000, 800);
        profileUi.setVisible(true);
        add(profileUi);
        repaint();
    }

    public void hiddenEveryThing(){
       if (loginPanel!=null)loginPanel.setVisible(false);
       if (profileUi!=null)profileUi.setVisible(false);
       if (superAdminPanel!=null)superAdminPanel.setVisible(false);
       if (userPanel!=null)userPanel.setVisible(false);
    }

    public void backFun(){
        hiddenEveryThing();
        if (user.getRole()==Role.SUPER_ADMIN){
            superAdminPanel = new SuperAdminPanel();
            superAdminPanel.setBounds(0,0,getWidth(),getHeight());
            add(superAdminPanel);
            repaint();
        }else {
            userPanel = new UserPanel(user);
            userPanel.setBounds(0,0,getWidth(),getHeight());
            add(userPanel);
            repaint();
        }
    }



    public void logOut(){
        profileUi.setVisible(false);
        loginPanel.userEmailField.setText("");
        loginPanel.userPasswordField.setText("");
        loginPanel.setVisible(true);
        repaint();
    }
}
