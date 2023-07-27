package Project.Ui;

import Project.Logic.DataBase.ProjectManager;
import Project.Logic.Project;
import Project.Logic.Role;
import Project.Logic.User;
import Project.Logic.DataBase.UserManagement;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class EditUserPanel extends JPanel {
    UserManagement userManagement = UserManagement.getInstance();
    ProjectManager projectManager = ProjectManager.getInstance();
    JLabel emailLabel = new JLabel("Email:");
    JLabel nameLabel = new JLabel("Name:");
    JLabel passwordLabel = new JLabel("Password:");
    JLabel roleLabel = new JLabel("Role:");
    JLabel projectsLabel = new JLabel("Projects:");
    JTextField emailField = new JTextField();
    JTextField nameField = new JTextField();
    JTextField passwordField = new JTextField();
    JComboBox<String> roleComboBox = new JComboBox<>();
    JComboBox<String> projectsComboBox = new JComboBox<>();
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    RoundedButton delete = new RoundedButton("Delete", 15, Color.red, Color.white, 12);
    RoundedButton addProject = new RoundedButton("Add+",15, Color.blue, Color.white, 12);
    JLabel emailErrorLabel = new JLabel();
    JLabel nameErrorLabel = new JLabel();
    JLabel passwordErrorLabel = new JLabel();
    JLabel roleErrorLabel = new JLabel();
    JLabel projectErrorLabel = new JLabel();
    User user;
    UserManagementPanel userManagementPanel;
    DefaultListModel<String> projectListModel = new DefaultListModel<>();
    ArrayList<Project> projectsInList = new ArrayList<>();
    ArrayList<Project> projectAvailable = new ArrayList<>();

    public EditUserPanel(int x, int y, UserManagementPanel userManagementPanel) {
        this.userManagementPanel = userManagementPanel;
        setBounds(x, y, 600, 600);
        setLayout(null);
        setFocusable(true);
        emailLabel.setBounds(75, 100, 75, 25);
        nameLabel.setBounds(75, 150, 75, 25);
        passwordLabel.setBounds(75, 200, 75, 25);
        roleLabel.setBounds(75, 250, 75, 25);
        projectsLabel.setBounds(75, 300, 75, 25);

        emailErrorLabel.setBounds(150, 125, 200, 25);
        emailErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        nameErrorLabel.setBounds(150, 175, 200, 25);
        nameErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        passwordErrorLabel.setBounds(150, 225, 200, 25);
        passwordErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        roleErrorLabel.setBounds(150, 275, 200, 25);
        roleErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        projectErrorLabel.setBounds(360, 320, 200, 25);
        projectErrorLabel.setFont(new Font(null, Font.ITALIC, 10));

        emailField.setBounds(150, 100, 200, 25);
        nameField.setBounds(150, 150, 200, 25);
        passwordField.setBounds(150, 200, 200, 25);
        roleComboBox.setBounds(150, 250, 200, 25);
        projectsComboBox.setBounds(150, 300, 200, 25);


        JList<String> projectList = new JList<>(projectListModel);
        projectList.setBounds(150, 330, 200, 100);
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(projectList);


        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        String enterKey = "enterKey";
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), enterKey);
        actionMap.put(enterKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit.doClick();
            }
        });

        for (Role role : Role.values()) {
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
                projectErrorLabel.setText("");
                boolean isEmailFine = false;
                boolean isNameFine = false;
                boolean isPasswordFine = false;
                boolean isRoleFine = false;

                if (GeneralController.getInstance().isEmpty(emailField.getText())) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("Enter Email");
                } else if (!userManagement.emailAuthentication(emailField.getText().toLowerCase())) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("Enter a Correct Email");
                } else if (!user.getEmail().equalsIgnoreCase(emailField.getText()) && userManagement.isEmailExist(emailField.getText().toLowerCase())) {
                    // Check if the new email is different from the current email and if it already exists in the database
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("This Email already exists");
                } else isEmailFine = true;
                if (GeneralController.getInstance().isEmpty(nameField.getText())) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setText("Enter Name");
                } else isNameFine = true;
                if (GeneralController.getInstance().isEmpty(passwordField.getText())) {
                    passwordErrorLabel.setForeground(Color.red);
                    passwordErrorLabel.setText("Enter Password");
                } else isPasswordFine = true;
                if (GeneralController.getInstance().isEmpty(roleComboBox.getSelectedItem().toString())) {
                    roleErrorLabel.setForeground(Color.red);
                    roleErrorLabel.setText("Select a role");
                } else isRoleFine = true;
                if (isEmailFine && isNameFine && isPasswordFine && isRoleFine) {
                    if (!user.getEmail().equals(emailField.getText())) {
                        userManagement.editUserEmail(user, emailField.getText());
                    }
                    for (Project project : projectsInList) {
                        projectManager.addMemberToProject(project,user);
                    }
                    userManagement.editUserName(user, nameField.getText());
                    userManagement.editUserPassword(user, passwordField.getText());
                    userManagement.editUserRole(user, Role.values()[roleComboBox.getSelectedIndex()]);
                    userManagementPanel.drawUsers();
                }
            }
        });

        delete.setBounds(285, 500, 100, 25);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userManagement.deleteUser(user);
                userManagementPanel.selectedUserIndex = -1;
                userManagementPanel.drawUsers();
                setVisible(false);
            }
        });


        addProject.setBounds(360, 300, 90, 25);
        addProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectErrorLabel.setText("");
                if (projectsComboBox.getSelectedIndex()==0) {
                    projectErrorLabel.setForeground(Color.red);
                    projectErrorLabel.setText("First select a Project");
                }else{
                    projectsInList.add(projectAvailable.get(projectsComboBox.getSelectedIndex() - 1));
                    listUpdate();
                }
            }
        });
        add(addProject);


        add(emailLabel);
        add(nameLabel);
        add(passwordLabel);
        add(roleLabel);
        add(projectsLabel);

        add(emailErrorLabel);
        add(nameErrorLabel);
        add(passwordErrorLabel);
        add(roleErrorLabel);
        add(projectErrorLabel);

        add(emailField);
        add(nameField);
        add(passwordField);
        add(roleComboBox);
        add(projectsComboBox);

        add(submit);
        add(delete);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submit.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void update() {
        emailField.setText(user.getEmail());
        nameField.setText(user.getFullName());
        passwordField.setText(user.getPassword());
        for (Role role : Role.values()) {
            int index = 0;
            switch (user.getRole()) {
                case PROJECT_OWNER:
                    index = 1;
                    break;
                case DEVELOPER:
                    index = 2;
                    break;
                case QA:
                    index = 3;
                    break;
            }
            roleComboBox.setSelectedIndex(index);
        }
        projectsComboBox.removeAllItems();
        ArrayList<Project> allProjects = projectManager.getAllProjects();
        projectAvailable =new ArrayList<>();
        projectsInList= new ArrayList<>();
        projectsComboBox.addItem("");
        for (Project project : allProjects) {
            if(!projectManager.getProjectsByMember(user).contains(project)) {
                projectsComboBox.addItem(project.getName());
                projectAvailable.add(project);
            }
        }
        projectListModel.removeAllElements();
        for (Project project : projectManager.getProjectsByMember(user)) {
            projectListModel.addElement(project.getName());
        }
    }

    public void listUpdate(){
        projectsComboBox.removeAllItems();
        ArrayList<Project> allProjects = projectManager.getAllProjects();
        projectAvailable =new ArrayList<>();
        projectsComboBox.addItem("");
        for (Project project : allProjects) {
            if (!projectsInList.contains(project)) {
                projectsComboBox.addItem(project.getName());
                projectAvailable.add(project);
            }
        }

        projectListModel.removeAllElements();
        for (Project project : projectsInList) {
            projectListModel.addElement(project.getName());
        }
    }
}
