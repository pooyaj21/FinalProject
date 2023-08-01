package Project.Ui;

import Project.Logic.DataBase.ProjectManager;
import Project.Logic.Project;

import Project.Util.GeneralController;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateProjectPanel extends JPanel {
    JLabel nameLabel = new JLabel("Name:");
    JLabel descriptionLabel = new JLabel("Description:");
    JTextField nameField = new JTextField();
    JTextArea descriptionArea = new JTextArea();
    RoundedButton submit = new RoundedButton("Submit", 15, Color.blue, Color.white, 12);
    JLabel nameErrorLabel = new JLabel();
    ProjectManager projectManager = ProjectManager.getInstance();
    public CreateProjectPanel(ProjectManagementPanel projectManagementPanel) {
       setSize(600, 700);
        setLayout(null);
        setFocusable(true);
        nameLabel.setBounds(75, 130, 75, 25);
        descriptionLabel.setBounds(75, 250, 100, 25);

        nameErrorLabel.setBounds(170, 155, 200, 25);
        nameErrorLabel.setFont(new Font(null, Font.ITALIC, 10));

        nameField.setBounds(170, 130, 200, 25);
        descriptionArea.setBounds(170, 250, 400, 150);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

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


        submit.setBounds(400, 500, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setText("");
                if (GeneralController.getInstance().isEmpty(nameField.getText())){
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setText("Enter a Name");
                }else{
                    Project project = new Project(nameField.getText());
                    project.setDescription(descriptionArea.getText());
                    projectManager.createProject(project);
                    projectManagementPanel.repaint();
                    projectManagementPanel.drawProjects();
                    nameField.setText("");
                    descriptionArea.setText("");
                }
            }
        });


        add(nameLabel);
        add(descriptionLabel);

        add(nameErrorLabel);


        add(nameField);
        add(descriptionArea);

        add(submit);
    }
    public CreateProjectPanel(UserPanel userPanel) {
        setSize(600, 700);
        setLayout(null);
        setFocusable(true);
        nameLabel.setBounds(75, 130, 75, 25);
        descriptionLabel.setBounds(75, 250, 100, 25);

        nameErrorLabel.setBounds(170, 155, 200, 25);
        nameErrorLabel.setFont(new Font(null, Font.ITALIC, 10));

        nameField.setBounds(170, 130, 200, 25);
        descriptionArea.setBounds(170, 250, 400, 150);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

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


        submit.setBounds(400, 500, 100, 25);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameErrorLabel.setText("");
                if (GeneralController.getInstance().isEmpty(nameField.getText())){
                    nameErrorLabel.setForeground(Color.red);
                    nameErrorLabel.setText("Enter a Name");
                }else{
                    Project project = new Project(nameField.getText());
                    project.setDescription(descriptionArea.getText());
                    projectManager.createProject(project);
                    projectManager.addMemberToProject(project,userPanel.getUser());
                    userPanel.repaint();
                    userPanel.drawProjects();
                    nameField.setText("");
                    descriptionArea.setText("");
                }
            }
        });


        add(nameLabel);
        add(descriptionLabel);

        add(nameErrorLabel);


        add(nameField);
        add(descriptionArea);

        add(submit);
    }
}
