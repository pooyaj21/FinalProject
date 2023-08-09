package Project.Ui.Charts;

import Project.Logic.DataBase.SQL.IssueDataBaseSql;
import Project.Logic.Project;
import Project.Logic.Status;
import Project.Logic.User;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.demo.charts.ExampleChart;

import javax.swing.*;
import java.awt.*;

public class ProjectPieChart extends JPanel {
    JLabel errorLabel = new JLabel("'There is no Data here'");

    public ProjectPieChart(Project project) {

        int todoTask = IssueDataBaseSql.getInstance().countIssuesInStatusForProject(project.getId(), Status.TODO.toString());
        int inProgressTask = IssueDataBaseSql.getInstance().countIssuesInStatusForProject(project.getId(), Status.IN_PROGRESS.toString());
        int qaTask = IssueDataBaseSql.getInstance().countIssuesInStatusForProject(project.getId(), Status.QA.toString());
        int doneTask = IssueDataBaseSql.getInstance().countIssuesInStatusForProject(project.getId(), Status.DONE.toString());

        setLayout(null);
        setSize(new Dimension(780, 400));
        setBorder(null);
        ExampleChart<PieChart> exampleChart = new ProjectChart(todoTask,inProgressTask,qaTask,doneTask);
        PieChart chart = exampleChart.getChart();
        JPanel panel= new XChartPanel<>(chart);

        if (todoTask ==0&& inProgressTask ==0&&qaTask ==0&&doneTask ==0){
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
