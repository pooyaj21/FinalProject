package Project.Ui;

import Project.Logic.*;
import Project.Logic.DataBase.SQL.BoardDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.BoardIssuesDataBaseSql;
import Project.Logic.DataBase.SQL.CrossTabel.UserProjectDataBaseSql;
import Project.Logic.DataBase.SQL.IssueDataBaseSql;
import Project.Logic.DataBase.SQL.ProjectDatabaseSQL;
import Project.Logic.DataBase.SQL.UserDataBaseSQL;
import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditProjectPanel extends JPanel {
    int projectId;
    JLabel nameLabel = new JLabel("Name:");
    JTextField nameField = new JTextField();
    JLabel descriptionLabel = new JLabel("Description:");
    JLabel usersLabel = new JLabel("Users:");
    JTextArea descriptionArea = new JTextArea();
    RoundedButton addButton = new RoundedButton("Add+", 15, Color.blue, Color.white, 12);
    RoundedButton removeButton = new RoundedButton("Remove-", 15, Color.red, Color.white, 12);
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    RoundedButton delete = new RoundedButton("Delete", 15, Color.red, Color.white, 12);
    JComboBox<String> addComboBox = new JComboBox<>();
    JComboBox<String> removeComboBox = new JComboBox<>();
    DefaultListModel<String> UserListModel = new DefaultListModel<>();
    JList<String> userList = new JList<>(UserListModel);
    JScrollPane listScrollPane = new JScrollPane(userList);
    ArrayList<Integer> availableUsers = new ArrayList<>();
    ArrayList<Integer> userInList = new ArrayList<>();
    JLabel nameErrorLabel = new JLabel();
    JLabel addErrorLabel = new JLabel();
    JLabel removeErrorLabel = new JLabel();
    JButton viewButton = new JButton();

    public EditProjectPanel(ProjectManagementPanel projectManagementPanel) {
        setSize(600, 700);
        setLayout(null);

        ImageIcon eyeIcon = new ImageIcon("Assets/eyeIcon.png");

        viewButton.setIcon(GeneralController.getInstance().resizeIcon(eyeIcon, 30, 30));
        viewButton.setBounds(500, 20, 50, 50);
        viewButton.setContentAreaFilled(false);
        viewButton.setBorder(null);
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                projectManagementPanel.getSuperAdminPanel().showProjectPanel(ProjectDatabaseSQL.getInstance().getProjectById(projectId));
            }
        });

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
                if (addComboBox.getSelectedItem()==null) {
                    addErrorLabel.setForeground(Color.red);
                    addErrorLabel.setText("First select a Project");
                } else {
                    UserProjectDataBaseSql.getInstance().addUserToProject(projectId,availableUsers.get(addComboBox.getSelectedIndex()-1));
                    listUpdate();
                }
            }
        });

        addComboBox.setBounds(150, 250, 200, 25);
        usersLabel.setBounds(50, 250, 200, 25);

        removeButton.setBounds(350, 300, 90, 25);
        removeErrorLabel.setBounds(350, 325, 200, 25);
        removeErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeErrorLabel.setText("");
                if (removeComboBox.getSelectedItem()==null) {
                    removeErrorLabel.setForeground(Color.red);
                    removeErrorLabel.setText("First select a Project");
                } else {
                    UserProjectDataBaseSql.getInstance().removeUserFromProject(projectId,userInList.get(removeComboBox.getSelectedIndex()-1));
                    listUpdate();
                }
            }
        });

        removeComboBox.setBounds(150, 300, 200, 25);

        listScrollPane.setBounds(150, 350, 200, 200);


        submit.setBounds(490, 550, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setText("");
                if (GeneralController.getInstance().isEmpty(nameField.getText())) {
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setText("Enter a name");
                } else {
                    ProjectDatabaseSQL.getInstance().editProject(projectId, nameField.getText(), descriptionArea.getText());
                    update();
                    projectManagementPanel.drawProjects();
                }
            }
        });


        delete.setBounds(375, 550, 100, 25);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectDatabaseSQL.getInstance().deleteProject(projectId);
                projectManagementPanel.selectedProjectIndex = -1;
                projectManagementPanel.drawProjects();
                projectManagementPanel.projectPanel.setVisible(false);
                projectManagementPanel.superAdminPanel.userManagementButton.doClick();
                projectManagementPanel.superAdminPanel.projectManagementButton.doClick();
                setVisible(false);
            }
        });


        add(nameLabel);
        add(nameField);
        add(descriptionLabel);
        add(descriptionArea);
        add(addButton);
        add(removeButton);
        add(addComboBox);
        add(removeComboBox);
        add(listScrollPane);
        add(submit);
        add(delete);
        add(nameErrorLabel);
        add(addErrorLabel);
        add(removeErrorLabel);
        add(usersLabel);
        add(viewButton);
        listUpdate();

    }

    public void listUpdate() {
        userInList.clear();
        UserListModel.clear();
        for (User user : UserProjectDataBaseSql.getInstance().getAllUsersOfProject(projectId)) {
            UserListModel.addElement(user.getFullName());
            userInList.add(user.getId());
        }

        addComboBox.removeAllItems();
        availableUsers.clear();
        addComboBox.addItem(null);
        for (User user : UserDataBaseSQL.getInstance().getAllUsers()) {
            if (!userInList.contains(user.getId()) &&
                    user.getRole() != Role.SUPER_ADMIN) {
                addComboBox.addItem(user.getFullName());
                availableUsers.add(user.getId());
            }
        }

        removeComboBox.removeAllItems();
        removeComboBox.addItem(null);
        for (User user : UserProjectDataBaseSql.getInstance().getAllUsersOfProject(projectId)) {
            removeComboBox.addItem(user.getFullName());
        }
    }


    public void update() {
        Project project = ProjectDatabaseSQL.getInstance().getProjectById(projectId);
        nameField.setText(project.getName());
        descriptionArea.setText(project.getDescription());
        listUpdate();
    }

    public void setProject(int projectId) {
        this.projectId = projectId;
        update();
    }
}
