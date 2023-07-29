package Project.Ui;

import Project.Logic.Project;
import Project.Logic.DataBase.ProjectManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectManagementPanel extends JPanel {
    private static ProjectManagementPanel instance;
    CreateProjectPanel createProjectPanel;
    ProjectManager projectManager = ProjectManager.getInstance();
    int selectedProjectIndex = -1;
    JPanel projectPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < projectManager.getAllProjects().size() - 1; i++) {
                g.drawLine(0, (100 * (i + 1)), 200, (100 * (i + 1)));
            }
        }
    };
    JScrollPane projectScrollPane;
    JButton projectButton;

    private ProjectManagementPanel(int x, int y) {
        setBounds(x, y, 800, 600);
        setLayout(null);

        projectPanel.setBounds(0, 50, 199, getHeight());
        projectPanel.setLayout(null);
        projectPanel.setPreferredSize(new Dimension(199, 600));
        add(projectPanel);

        projectScrollPane = new JScrollPane(projectPanel);
        projectScrollPane.setBounds(0, 50, 199, getHeight());
        projectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        projectScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(projectScrollPane, BorderLayout.CENTER);

        createProjectPanel = new CreateProjectPanel(200, 0);
        createProjectPanel.setVisible(false);
        add(createProjectPanel);


        JButton button = new JButton("Add+");
        button.setBounds(0, 0, 200, 50);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedProjectIndex = -1;
                drawProjects();
                createProjectPanel.setVisible(true);
            }
        });
        add(button, BorderLayout.NORTH); // Add the "Add+" button in the north

        drawProjects(); // Draw the project buttons

        setFocusable(true);
        requestFocusInWindow();
    }

    public static ProjectManagementPanel getInstance(int x, int y) {
        if (instance == null) {
            instance = new ProjectManagementPanel(x, y);
        }
        return instance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(199, 0, 199, getHeight());
        g.drawLine(0, 50, 200, 50);
    }

    @Override
    public Dimension getPreferredSize() {
        // Calculate the preferred size based on the width and number of projects
        int preferredHeight = Math.max(getHeight(), projectManager.getAllProjects().size() * 100 + 50);
        return new Dimension(800, preferredHeight);
    }

    private void drawProjectButton(Project project, int index) {
        projectButton = new JButton();
        projectButton.setBounds(0, (100 * index), 200, 100);
        projectButton.setText(project.getName());
        projectButton.setContentAreaFilled(false);
        projectButton.setBorder(null);

        if (index == selectedProjectIndex) {
            projectButton.setContentAreaFilled(true);
            projectButton.setBackground(Color.GRAY);
        } else {
            projectButton.setBackground(null);
        }

        projectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProjectPanel.setVisible(false);
                selectedProjectIndex = index;
                drawProjects();
            }
        });
        projectPanel.add(projectButton);
    }

    public void drawProjects() {
        projectPanel.removeAll();

        for (int i = 1; i < projectManager.getAllProjects().size(); i++) {
            Project project = projectManager.getAllProjects().get(i);
            drawProjectButton(project, i - 1);
        }

        // Repaint the projectPanel and update the scroll pane
        projectPanel.revalidate();
        projectPanel.repaint();
        projectPanel.setPreferredSize(new Dimension(199, projectManager.getAllProjects().size() * 100));
        if (projectManager.getAllProjects().size() > 6) {
            projectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            projectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
        projectScrollPane.revalidate();
    }
}
