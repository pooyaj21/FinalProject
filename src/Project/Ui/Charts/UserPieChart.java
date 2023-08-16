package Project.Ui.Charts;

import Project.Logic.Project;
import Project.Logic.User;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.demo.charts.ExampleChart;

import javax.swing.*;
import java.awt.*;

public class UserPieChart extends JPanel {
    JLabel errorLabel = new JLabel("'There is no Data here'");
    public UserPieChart(Project project, User user,long startTime,long endTime) {

        int completedTasks = ChartController.findAmountOFCompletedIssues(project.getId(), user.getId(),startTime,endTime);
        int rejectedTasks = ChartController.findAmountOFRejectedIssues(project.getId(), user.getId(),startTime,endTime);

        setLayout(null);
        setSize(new Dimension(780, 400));
        setBorder(null);
        ExampleChart<PieChart> exampleChart = new UserChart(completedTasks, rejectedTasks);
        PieChart chart = exampleChart.getChart();
        JPanel panel= new XChartPanel<>(chart);
        panel.setOpaque(false);

        if (completedTasks ==0&& rejectedTasks ==0){
            panel=new JPanel();
            panel.setOpaque(false);
            errorLabel.setBounds(150,100,400,200);
            errorLabel.setOpaque(false);
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            errorLabel.setVerticalAlignment(SwingConstants.CENTER);
            errorLabel.setFont(new Font(null,Font.BOLD,30));
            errorLabel.setVisible(true);
        }else {
            errorLabel.setVisible(false);

        }
        panel.add(errorLabel);
        panel.setLayout(null);
        panel.setBorder(null);
        panel.setBounds(0,0,780,400);
        add(panel);
    }
}
