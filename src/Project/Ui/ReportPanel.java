package Project.Ui;

import Project.Logic.DataBase.SQL.CrossTabel.UserProjectDataBaseSql;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Ui.Charts.ProjectPieChart;
import Project.Ui.Charts.UserPieChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReportPanel extends JPanel {
    JPanel pieChart;
    ArrayList<User> users = new ArrayList<>();
    JLabel selectErrorLabel = new JLabel("select a option first");

    public ReportPanel(Project project) {
        setLayout(null);
        setSize(new Dimension(780, 600));
        setBorder(null);

        JPanel optionPanel = new JPanel(null);
        optionPanel.setBounds(0, 400, 780, 200);

        JComboBox<String> selectComboBox = new JComboBox<>();
        selectComboBox.setBounds(10, 75, 150, 50);
        selectComboBox.addItem(null);
        selectComboBox.addItem(project.getName());
        for (User user : UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId())) {
            selectComboBox.addItem(user.getFullName());
            users.add(user);
        }

        selectErrorLabel.setBounds(10, 100, 150, 25);
        selectErrorLabel.setFont(new Font(null, Font.ITALIC, 10));
        selectErrorLabel.setForeground(Color.red);
        selectErrorLabel.setVisible(false);
        optionPanel.add(selectErrorLabel);

        JButton submit = new JButton("Submit");
        submit.setBounds(620, 75, 150, 50);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectErrorLabel.setVisible(false);
                if (pieChart != null) remove(pieChart);
                if(selectComboBox.getSelectedIndex() ==1){
                    pieChart = new ProjectPieChart(project);
                    pieChart.setBounds(0, 0, 780, 400);
                    pieChart.setVisible(true);
                    pieChart.revalidate();
                    pieChart.repaint();
                    add(pieChart);
                }
                else if (selectComboBox.getSelectedIndex() > 1) {
                    pieChart = new UserPieChart(project, users.get(selectComboBox.getSelectedIndex() - 2));
                    pieChart.setBounds(0, 0, 780, 400);
                    pieChart.setVisible(true);
                    pieChart.revalidate();
                    pieChart.repaint();
                    add(pieChart);
                } else selectErrorLabel.setVisible(true);
                revalidate();
                repaint();
            }
        });

        optionPanel.add(selectComboBox);
        optionPanel.add(submit);
        optionPanel.setBackground(Color.GRAY);//change this

        add(optionPanel);
    }
}
