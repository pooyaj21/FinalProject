package Project.Ui;

import Project.Logic.DataBase.SQL.CrossTabel.UserProjectDataBaseSql;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Ui.Charts.MultiUserPieChart;
import Project.Ui.Charts.ProjectPieChart;
import Project.Ui.Charts.UserPieChart;
import Project.Util.DateUtil;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class ReportPanel extends JPanel {
    JPanel pieChart;
    ArrayList<User> users = new ArrayList<>();
    JLabel selectErrorLabel = new JLabel("select an option first");
    JLabel timeSelectErrorLabel = new JLabel("select a correct date");
    JXDatePicker startDatePicker = new JXDatePicker();
    JXDatePicker endDatePicker = new JXDatePicker();

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
        selectComboBox.addItem("All Users");
        for (User user : UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId())) {
            selectComboBox.addItem(user.getFullName());
            users.add(user);
        }
        selectComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectComboBox.getSelectedIndex() > 2) {
                    startDatePicker.setVisible(true);
                    endDatePicker.setVisible(true);
                } else {
                    startDatePicker.setVisible(false);
                    endDatePicker.setVisible(false);
                }
            }
        });


        startDatePicker.setDate(new Date());
        startDatePicker.setBounds(180, 87, 150, 25);
        timeSelectErrorLabel.setBounds(180, 112, 150, 25);
        timeSelectErrorLabel.setFont(new Font(null, Font.ITALIC, 10));


        endDatePicker.setDate(new Date());
        endDatePicker.setBounds(350, 87, 150, 25);

        startDatePicker.setVisible(false);
        endDatePicker.setVisible(false);
        timeSelectErrorLabel.setVisible(false);

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
                timeSelectErrorLabel.setVisible(false);
                if (pieChart != null) remove(pieChart);
                if (selectComboBox.getSelectedIndex() == 1) {
                    pieChart = new ProjectPieChart(project);
                    pieChart.setOpaque(false);
                    pieChart.setBounds(0, 0, 780, 400);
                    pieChart.setVisible(true);
                    pieChart.revalidate();
                    pieChart.repaint();
                    add(pieChart);
                } else if (selectComboBox.getSelectedIndex() == 2) {
                    pieChart = new MultiUserPieChart(project);
                    pieChart.setOpaque(false);
                    pieChart.setBounds(0, 0, 780, 400);
                    pieChart.setVisible(true);
                    pieChart.revalidate();
                    pieChart.repaint();
                    add(pieChart);
                } else if (selectComboBox.getSelectedIndex() > 2) {
                    if (endDatePicker.getDate().getTime()>=startDatePicker.getDate().getTime()) {
                        long endTime = endDatePicker.getDate().getTime();
                        if (DateUtil.formatDate(endDatePicker.getDate().getTime()).equals(DateUtil.formatDate(new Date().getTime()))) {
                            endTime = DateUtil.timeOfNow();
                        }
                        pieChart = new UserPieChart(project, users.get(selectComboBox.getSelectedIndex() - 3), startDatePicker.getDate().getTime(), endTime);
                        pieChart.setOpaque(false);
                        pieChart.setBounds(0, 0, 780, 400);
                        pieChart.setVisible(true);
                        pieChart.revalidate();
                        pieChart.repaint();
                        add(pieChart);
                    } else {
                        timeSelectErrorLabel.setVisible(true);
                        timeSelectErrorLabel.setForeground(Color.red);
                    }
                } else selectErrorLabel.setVisible(true);

                revalidate();
                repaint();
            }
        });

        optionPanel.add(selectComboBox);
        optionPanel.add(startDatePicker);
        optionPanel.add(endDatePicker);
        optionPanel.add(submit);
        optionPanel.add(selectErrorLabel);
        optionPanel.add(timeSelectErrorLabel);
        optionPanel.setBackground(new Color(0x003d9e));

        add(optionPanel);
    }
}
