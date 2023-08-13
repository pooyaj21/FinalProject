package Project.Ui.User;

import Project.Logic.DataBase.SQL.CrossTabel.UserProjectDataBaseSql;
import Project.Logic.FeatureAccess;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Ui.Project.ProjectPanel;
import Project.Ui.TopPanel;
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
            for (int i = 0; i < UserProjectDataBaseSql.getInstance().getAllProjectsOfUser(user.getId()).size(); i++) {
                g.drawLine(0, (100 * (i + 1)), 200, (100 * (i + 1)));
            }
        }
    };
     JButton projectButton;
    int selectedProjectIndex = -1;
    JScrollPane projectScrollPane;
    private User user;
    TopPanel topPanel;
    ProjectPanel projectPanel;

    public UserPanel(User user) {
        this.user = user;
        setOpaque(false);
        setSize(1000, 700);
        setLayout(null);

        topPanel = new TopPanel(user);
        setBounds(0,0,getWidth(),getHeight());
        add(topPanel);

        projectPanelMaker.setBounds(0, 150, 200, getHeight());
        projectPanelMaker.setOpaque(false);
        projectPanelMaker.setLayout(null);

        JButton addButton = new JButton();
        if (user.getRole().hasAccess(FeatureAccess.CREATE_PROJECT))addButton.setText("Add+");
        else addButton.setText("Your Projects");
        addButton.setBounds(0, 100, 200, 50);
        addButton.setContentAreaFilled(false);
        addButton.setBorder(null);
        add(addButton);

        projectScrollPane = new JScrollPane(projectPanelMaker);
        projectScrollPane.setBounds(0, 150, 200, getHeight() - 50);
        projectScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        projectScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        projectScrollPane.setOpaque(false);
        projectScrollPane.getViewport().setOpaque(false);
        add(projectScrollPane);



        drawProjects();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = new ImageIcon("Assets/mainBackground.jpeg").getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
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
            projectButton.setBackground(new Color(0x003d9e));
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
                    if (projectPanel!=null){
                        projectPanel.setVisible(false);
                    }
                    projectPanel=new ProjectPanel(UserPanel.this,project,user);
                    projectPanel.setOpaque(false);
                    projectPanel.setVisible(true);
                    projectPanel.setBounds(200,100,800,700);
                    add(projectPanel);
                    projectPanel.update();
                    projectPanel.kanbanButton.doClick();
                }
            });
        }


        projectPanelMaker.add(projectButton);
    }

    public void drawProjects() {
        projectPanelMaker.removeAll();

        for (int i = 0; i < UserProjectDataBaseSql.getInstance().getAllProjectsOfUser(user.getId()).size(); i++) {
            Project project = UserProjectDataBaseSql.getInstance().getAllProjectsOfUser(user.getId()).get(i);
            drawProjectButton(project, i);
        }

        projectPanelMaker.revalidate();
        projectPanelMaker.repaint();
        projectPanelMaker.setPreferredSize(new Dimension(200, UserProjectDataBaseSql.getInstance().getAllProjectsOfUser(user.getId()).size() * 100));
        if (UserProjectDataBaseSql.getInstance().getAllProjectsOfUser(user.getId()).size() > 6) {
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
