package Project.Ui;

import Project.Logic.Project;
import Project.Util.RoundedButton;

import javax.swing.*;
import java.awt.*;

public class ProjectSettingPanel extends JPanel {
    JLabel nameLabel = new JLabel("Name:");
    JTextField nameField = new JTextField();
    JLabel descriptionLabel = new JLabel("Description:");
    JTextArea descriptionArea = new JTextArea();
    RoundedButton addButton = new RoundedButton("Add+", 15, Color.blue, Color.white, 12);
    JComboBox<String> comboBox = new JComboBox<>();
    DefaultListModel<String> listModel = new DefaultListModel<>();
    JList<String> memberList = new JList<>(listModel);
    JScrollPane listScrollPane = new JScrollPane(memberList);

    public ProjectSettingPanel(Project project) {
        setSize(800, 700);
        setLayout(null);

        nameLabel.setBounds(50, 50, 100, 25);
        nameField.setBounds(150, 50, 200, 25);
        nameField.setText(project.getName());

        descriptionLabel.setBounds(50, 100, 100, 25);
        descriptionArea.setBounds(150, 100, 400, 100);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setText(project.getDescription());

        addButton.setBounds(350, 250, 90, 25);

        comboBox.setBounds(150, 250, 200, 25);
        comboBox.addItem("Option 1");
        comboBox.addItem("Option 2");
        comboBox.addItem("Option 3");


        listScrollPane.setBounds(150, 300, 200, 200);
        listModel.addElement("Member 1");
        listModel.addElement("Member 2");
        listModel.addElement("Member 3");

        //TODO:add submit button and handel adding

        add(nameLabel);
        add(nameField);
        add(descriptionLabel);
        add(descriptionArea);
        add(addButton);
        add(comboBox);
        add(listScrollPane);
    }
}
