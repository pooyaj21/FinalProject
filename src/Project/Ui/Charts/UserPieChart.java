package Project.Ui.Charts;

import Project.Logic.DataBase.SQL.TestDataBase;
import Project.Logic.Project;
import Project.Logic.User;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.demo.charts.ExampleChart;

import javax.swing.*;
import java.awt.*;

public class UserPieChart extends JPanel {
    private final Project project;
    private final User user;
    private int completedTasks;
    private int rejectedTasks;
    JLabel errorLabel = new JLabel("'There is no Data here'");
    public UserPieChart(Project project, User user) {
        this.project = project;
        this.user = user;

        completedTasks=ChartController.findAmountOFCompletedIssues(project.getId(), user.getId());
        rejectedTasks=ChartController.findAmountOFRejectedIssues(project.getId(), user.getId());
        System.out.println(completedTasks);
        System.out.println(rejectedTasks);

        setLayout(null);
        setSize(new Dimension(780, 400));
        setBorder(null);
        ExampleChart<PieChart> exampleChart = new TheChart(completedTasks,rejectedTasks);
        PieChart chart = exampleChart.getChart();
        JPanel panel= new XChartPanel<>(chart);

        if (completedTasks==0&&rejectedTasks==0){
            panel=new JPanel();
            errorLabel.setBounds(150,100,400,200);
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
