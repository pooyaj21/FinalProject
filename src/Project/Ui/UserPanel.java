package Project.Ui;

import Project.Logic.DataBase.ProjectManager;
import Project.Logic.FeatureAccess;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Util.GeneralController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel {
    JPanel projectPanelMaker = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < projectManager.getProjectsByUser(user).size(); i++) {
                g.drawLine(0, (100 * (i + 1)), 200, (100 * (i + 1)));
            }
        }
    };
    CreateProjectPanel createProjectPanel;
     JButton projectButton;
    int selectedProjectIndex = -1;
    JScrollPane projectScrollPane;
    ProjectManager projectManager = ProjectManager.getInstance();
    private User user;
    ProjectPanel projectPanel;

    public UserPanel(User user) {
        this.user = user;
        setSize(1000, 700);
        setLayout(null);

        TopPanel topPanel = new TopPanel(user);
        setBounds(0,0,getWidth(),getHeight());
        add(topPanel);

        createProjectPanel=new CreateProjectPanel(this);
        createProjectPanel.setBounds(200,100,getWidth(),getHeight());
        createProjectPanel.setVisible(false);
        add(createProjectPanel);

        projectPanelMaker.setBounds(0, 150, 200, getHeight());
        projectPanelMaker.setLayout(null);
        add(projectPanelMaker);

        JButton addButton = new JButton();
        if (user.getRole().hasAccess(FeatureAccess.CREATE_PROJECT)){
            addButton.setText("Add+");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedProjectIndex=-1;
                    drawProjects();
                    createProjectPanel.setVisible(true);
                }
            });
        }
        else addButton.setText("Your Projects");
        addButton.setBounds(0, 100, 200, 50);
        addButton.setContentAreaFilled(false);
        addButton.setBorder(null);
        add(addButton);

        projectScrollPane = new JScrollPane(projectPanelMaker);
        projectScrollPane.setBounds(0, 150, 200, getHeight() - 50);
        projectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        projectScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(projectScrollPane);

        projectPanel=new ProjectPanel(this);
        projectPanel.setBounds(200,100,800,700);
        projectPanel.setVisible(false);
        add(projectPanel);


        drawProjects();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(200, 100, 200, getHeight());
        g.drawLine(0, 100, getWidth(), 100);
        g.drawLine(0, 150, 200, 150);
    }

    private void drawProjectButton(Project project, int index) {
        projectButton = new JButton();
        projectButton.setBounds(0, (100 * index), 200, 100);
        projectButton.setText(project.getName());
        projectButton.setContentAreaFilled(false);
        projectButton.setBorder(null);
        projectButton.setToolTipText(GeneralController.addLineBreaksHTML(project.getDescription()));

        if (index == selectedProjectIndex) {
            projectButton.setContentAreaFilled(true);
            projectButton.setBackground(Color.GRAY);
            projectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                }
            });
        } else {
            projectButton.setBackground(null);
            projectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedProjectIndex = index;
                    drawProjects();

                    createProjectPanel.setVisible(false);
                    projectPanel.projectSettingPanel.setVisible(false);
                    //TODO update this
                    projectPanel.setProject(project);
                    projectPanel.setUser(user);
                    projectPanel.setVisible(true);
                    projectPanel.update();
                }
            });
        }


        projectPanelMaker.add(projectButton);
    }

    public void drawProjects() {
        projectPanelMaker.removeAll();

        for (int i = 0; i < projectManager.getProjectsByUser(user).size(); i++) {
            Project project = projectManager.getProjectsByUser(user).get(i);
            drawProjectButton(project, i);
        }

        projectPanelMaker.revalidate();
        projectPanelMaker.repaint();
        projectPanelMaker.setPreferredSize(new Dimension(200, projectManager.getProjectsByUser(user).size() * 100));
        if (projectManager.getProjectsByUser(user).size() > 6) {
            projectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            projectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
        projectScrollPane.revalidate();
    }

    public User getUser() {
        return user;
    }
}
