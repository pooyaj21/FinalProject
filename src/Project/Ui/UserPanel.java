package Project.Ui;

import Project.Logic.DataBase.ProjectManager;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Util.GeneralController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel {
    JPanel projectPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < projectManager.getProjectsByMember(user).size(); i++) {
                g.drawLine(0, (100 * (i + 1)), 200, (100 * (i + 1)));
            }
        }
    };
    private JButton projectButton;
    int selectedProjectIndex = -1;
    JScrollPane projectScrollPane;
    ProjectManager projectManager = ProjectManager.getInstance();
    private User user;

    public UserPanel(User user) {
        this.user = user;
        setSize(1000, 700);
        setLayout(null);

        TopPanel topPanel = new TopPanel(user);
        setBounds(0,0,getWidth(),getHeight());
        add(topPanel);

        projectPanel.setBounds(0, 150, 200, getHeight());
        projectPanel.setLayout(null);
        add(projectPanel);

        JButton addButton = new JButton();
        if (user.getRole().getLevelOfAccess()<2)addButton.setText("Add+");
        else addButton.setText("Your Projects");
        addButton.setBounds(0, 100, 200, 50);
        addButton.setContentAreaFilled(false);
        addButton.setBorder(null);
        add(addButton);

        projectScrollPane = new JScrollPane(projectPanel);
        projectScrollPane.setBounds(0, 150, 200, getHeight() - 50);
        projectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        projectScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(projectScrollPane);
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
        } else {
            projectButton.setBackground(null);
        }

        projectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedProjectIndex = index;
                drawProjects();
                if (user.getRole().getLevelOfAccess()<2){
                    //TODO
                }else{
                    //TODO
                }
            }
        });
        projectPanel.add(projectButton);
    }

    public void drawProjects() {
        projectPanel.removeAll();

        for (int i = 0; i < projectManager.getProjectsByMember(user).size(); i++) {
            Project project = projectManager.getProjectsByMember(user).get(i);
            drawProjectButton(project, i);
        }

        // Repaint the projectPanel and update the scroll pane
        projectPanel.revalidate();
        projectPanel.repaint();
        projectPanel.setPreferredSize(new Dimension(200, projectManager.getProjectsByMember(user).size() * 100));
        if (projectManager.getProjectsByMember(user).size() > 6) {
            projectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            projectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
        projectScrollPane.revalidate();
    }
}
