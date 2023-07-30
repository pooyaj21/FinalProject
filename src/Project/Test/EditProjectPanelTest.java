package Project.Test;

import Project.Logic.Project;
import Project.Ui.ProjectSettingPanel;

import javax.swing.*;

public class EditProjectPanelTest {

    public static void main(String[] args) {
        // Create a new Project
        Project project = new Project("Sample Project");

        // Create the JFrame
        JFrame frame = new JFrame("Project Setting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(null);

        // Create the ProjectSettingPanel and add it to the JFrame
        ProjectSettingPanel settingPanel = new ProjectSettingPanel(project);
        frame.add(settingPanel);

        // Set the JFrame visible
        frame.setVisible(true);
    }
}
