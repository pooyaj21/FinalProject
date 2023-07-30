package Project.Ui;

import Project.Logic.Project;
import Project.Logic.DataBase.ProjectManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectManagementPanel extends JPanel {
    CreateProjectPanel createProjectPanel;
    ProjectManager projectManager = ProjectManager.getInstance();
    int selectedProjectIndex = -1;
    JPanel projectPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < projectManager.getAllProjects().size(); i++) {
                g.drawLine(0, (100 * i ), 200, (100 * i));
            }
        }
    };
    JScrollPane projectScrollPane;
    JButton projectButton;

    public ProjectManagementPanel() {
        setSize( 800, 700);
        setLayout(null);

        projectPanel.setBounds(0, 50, 199, getHeight());
        projectPanel.setLayout(null);
        projectPanel.setSize(200,700);
        add(projectPanel);

        projectScrollPane = new JScrollPane(projectPanel);
        projectScrollPane.setBounds(0, 50, 200, getHeight()-50);
        projectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        projectScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(projectScrollPane, BorderLayout.CENTER);

        createProjectPanel = new CreateProjectPanel(this);
        createProjectPanel.setBounds(200,0,getWidth(),getHeight());
        createProjectPanel.setVisible(false);
        add(createProjectPanel);


        JButton addButton = new JButton("Add+");
        addButton.setBounds(0, 0, 200, 50);
        addButton.setContentAreaFilled(false);
        addButton.setBorder(null);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedProjectIndex = -1;
                drawProjects();
                createProjectPanel.setVisible(true);
            }
        });
        add(addButton);

        drawProjects();

        setFocusable(true);
        requestFocusInWindow();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(199, 0, 199, getHeight());
        g.drawLine(0, 50, 200, 50);
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
