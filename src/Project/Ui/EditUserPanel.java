package Project.Ui;

import Project.Logic.DataBase.SQL.CrossTabel.UserProjectDataBaseSql;
import Project.Logic.DataBase.SQL.ProjectDatabaseSQL;
import Project.Logic.DataBase.SQL.UserDataBaseSQL;
import Project.Logic.Project;
import Project.Logic.Role;
import Project.Logic.User;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditUserPanel extends JPanel {
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
    RoundedButton addProject = new RoundedButton("Add+", 15, Color.blue, Color.white, 12);
    JLabel emailErrorLabel = new JLabel();
    JLabel nameErrorLabel = new JLabel();
    JLabel passwordErrorLabel = new JLabel();
    JLabel roleErrorLabel = new JLabel();
    JLabel projectErrorLabel = new JLabel();
    User user;
    UserManagementPanel userManagementPanel;
    DefaultListModel<String> projectListModel = new DefaultListModel<>();
    JList<String> userList = new JList<>(projectListModel);
    JScrollPane listScrollPane = new JScrollPane(userList);
    ArrayList<Integer> projectsInList = new ArrayList<>();
    ArrayList<Integer> projectAvailable = new ArrayList<>();

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


        userList.setBounds(150, 330, 200, 100);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(userList);
        add(listScrollPane);


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
                } else if (!GeneralController.emailAuthentication(user.getEmail().toLowerCase())) {
                    emailErrorLabel.setForeground(Color.red);
                    emailErrorLabel.setText("Enter a Correct Email");
                } else if (!user.getEmail().equalsIgnoreCase(emailField.getText()) && UserDataBaseSQL.getInstance().isEmailExist(emailField.getText().toLowerCase())) {
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
                    UserDataBaseSQL.getInstance().editUser(user.getId(), nameField.getText(), emailField.getText(), passwordField.getText(), Role.values()[roleComboBox.getSelectedIndex()]);
                    userManagementPanel.drawUsers();
                }
            }
        });

        delete.setBounds(285, 500, 100, 25);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDataBaseSQL.getInstance().deleteUser(user.getId());
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
                if (projectsComboBox.getSelectedIndex() == 0) {
                    projectErrorLabel.setForeground(Color.red);
                    projectErrorLabel.setText("First select a Project");
                } else {
                    UserProjectDataBaseSql.getInstance().addUserToProject(projectAvailable.get(projectsComboBox.getSelectedIndex()-1),user.getId());
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
        listUpdate();
    }

    public void listUpdate() {
        projectsInList.clear();
        projectListModel.clear();
        for (Project project : UserProjectDataBaseSql.getInstance().getAllProjectsOfUser(user.getId())) {
            projectListModel.addElement(project.getName());
            projectsInList.add(project.getId());
        }

        projectsComboBox.removeAllItems();
        projectAvailable.clear();
        projectsComboBox.addItem(null);
        for (Project Project : ProjectDatabaseSQL.getInstance().getAllProjects()) {
            if (!projectsInList.contains(Project.getId())) {
                projectsComboBox.addItem(Project.getName());
                projectAvailable.add(Project.getId());
            }
        }

//        removeComboBox.removeAllItems();
//        removeComboBox.addItem(null);
//        for (User user : UserProjectDataBaseSql.getInstance().getAllUsersOfProject(projectId)) {
//            removeComboBox.addItem(user.getFullName());
//        }

        //TODO:add remove combo
    }

}
