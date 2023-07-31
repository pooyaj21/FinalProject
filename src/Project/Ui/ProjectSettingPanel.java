package Project.Ui;

import Project.Logic.DataBase.ProjectManager;
import Project.Logic.DataBase.UserDatabase;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProjectSettingPanel extends JPanel {
    Project project;
    JLabel nameLabel = new JLabel("Name:");
    JTextField nameField = new JTextField();
    JLabel descriptionLabel = new JLabel("Description:");
    JTextArea descriptionArea = new JTextArea();
    RoundedButton addButton = new RoundedButton("Add+", 15, Color.blue, Color.white, 12);
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    RoundedButton delete = new RoundedButton("Delete", 15, Color.red, Color.white, 12);
    JComboBox<String> comboBox = new JComboBox<>();
    DefaultListModel<String> UserListModel = new DefaultListModel<>();
    JList<String> userList = new JList<>(UserListModel);
    JScrollPane listScrollPane = new JScrollPane(userList);
    ProjectManager projectManager = ProjectManager.getInstance();
    UserDatabase userDatabase = UserDatabase.getInstance();
    ArrayList<User> allUsers = new ArrayList<>();
    ArrayList<User> availableUsers = new ArrayList<>();
    ArrayList<User> userInList = new ArrayList<>();
    JLabel nameErrorLabel = new JLabel();
    JLabel addErrorLabel = new JLabel();

    public ProjectSettingPanel(ProjectManagementPanel projectManagementPanel) {
        setSize(600, 700);
        setLayout(null);

        nameLabel.setBounds(50, 50, 100, 25);
        nameField.setBounds(150, 50, 200, 25);
        nameErrorLabel.setBounds(150, 75, 200, 25);
        nameErrorLabel.setFont(new Font(null, Font.ITALIC, 10));

        descriptionLabel.setBounds(50, 100, 100, 25);
        descriptionArea.setBounds(150, 100, 400, 100);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);


        addButton.setBounds(350, 250, 90, 25);
        addErrorLabel.setBounds(350, 275, 200, 25);
        addErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addErrorLabel.setText("");
                if (comboBox.getSelectedIndex() == 0) {
                    addErrorLabel.setForeground(Color.red);
                    addErrorLabel.setText("First select a Project");
                } else {
                    userInList.add(availableUsers.get(comboBox.getSelectedIndex() - 1));
                    listUpdate();
                }
            }
        });

        comboBox.setBounds(150, 250, 200, 25);


        listScrollPane.setBounds(150, 300, 200, 200);


        submit.setBounds(490, 550, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setText("");
                if (GeneralController.getInstance().isEmpty(nameField.getText())) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setText("Enter a name");
                } else {
                    projectManager.editProjectName(project, nameField.getText());
                    projectManager.editProjectDescription(project, descriptionArea.getText());
                    for (User user : userInList) {
                        projectManager.addMemberToProject(project, user);
                    }
                    update();
                    projectManagementPanel.drawProjects();
                }
            }
        });


        delete.setBounds(375, 550, 100, 25);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectManager.removeProject(project);
                projectManagementPanel.selectedProjectIndex = -1;
                projectManagementPanel.drawProjects();
                setVisible(false);
            }
        });


        add(nameLabel);
        add(nameField);
        add(descriptionLabel);
        add(descriptionArea);
        add(addButton);
        add(comboBox);
        add(listScrollPane);
        add(submit);
        add(delete);
        add(nameErrorLabel);
        add(addErrorLabel);

    }

    public void listUpdate() {
        comboBox.removeAllItems();
        availableUsers.clear();
        comboBox.addItem(null);
        for (User user : allUsers) {
            if (!userInList.contains(user) && !projectManager.getUsersByProject(project).contains(user)) {
                comboBox.addItem(user.getFullName());
                availableUsers.add(user);
            }
        }

        UserListModel.clear();
        for (User user : projectManager.getUsersByProject(project)) {
            UserListModel.addElement(user.getFullName());
        }
        for (User user : userInList) {
            UserListModel.addElement(user.getFullName());
        }
    }


    public void update() {
        nameField.setText(project.getName());
        descriptionArea.setText(project.getDescription());
        comboBox.removeAllItems();
        for (int i = 1; i < userDatabase.getUsers().size(); i++) {
            allUsers.add(userDatabase.getUsers().get(i));
        }
        userInList = new ArrayList<>();
        availableUsers = new ArrayList<>();
        comboBox.addItem("");
        for (User user : allUsers) {
            if (!userInList.contains(user) && !projectManager.getUsersByProject(project).contains(user)) {
                comboBox.addItem(user.getFullName());
                availableUsers.add(user);
            }
        }
        UserListModel.removeAllElements();
        for (User user : projectManager.getUsersByProject(project)) {
            UserListModel.addElement(user.getFullName());
        }
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
