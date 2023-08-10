package Project.Ui.Charts;

import Project.Logic.DataBase.SQL.ProjectDatabaseSQL;
import Project.Logic.Project;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.demo.charts.ExampleChart;

import javax.swing.*;
import java.awt.*;

public class MultiUserPieChart extends JPanel {

    public MultiUserPieChart(Project project) {
        setLayout(null);
        setSize(new Dimension(780, 400));
        setBorder(null);
        ExampleChart<PieChart> exampleChart =new MultiUserChart(project);
        PieChart chart = exampleChart.getChart();
        JPanel panel= new XChartPanel<>(chart);
        panel.setBounds(0,0,780,400);
        add(panel);
    }
}
