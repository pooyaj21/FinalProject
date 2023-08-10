package Project.Test;

import Project.Logic.DataBase.SQL.ProjectDatabaseSQL;
import Project.Ui.Charts.MultiUserChart;
import Project.Ui.Charts.ProjectChart;
import Project.Util.DateUtil;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.demo.charts.ExampleChart;

import javax.swing.*;
import java.awt.*;

public class TestDataBase {

    public static void main(String[] args) {

        // Create and configure the frame
        JFrame frame = new JFrame("Multi-User Pie Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Add the chart to the frame
        ExampleChart<PieChart> exampleChart =new MultiUserChart(ProjectDatabaseSQL.getInstance().getProjectById(13));
        PieChart chart = exampleChart.getChart();
        JPanel panel= new XChartPanel<>(chart);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        // Display the frame
        frame.setVisible(true);
    }
}
