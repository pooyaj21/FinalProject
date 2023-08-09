package Project.Logic.DataBase.SQL;

import Project.Ui.Charts.ChartController;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.demo.charts.ExampleChart;

import javax.swing.*;
import java.awt.*;

public class TestDataBase implements ExampleChart<PieChart> {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ExampleChart<PieChart> exampleChart = new TestDataBase();
            PieChart chart = exampleChart.getChart();

            JPanel panel = new XChartPanel<>(chart);

            JFrame frame = new JFrame("Pie Chart Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setVisible(true);
        });


    }

    @Override
    public PieChart getChart() {
        // Create Chart
        PieChart chart = new PieChartBuilder().width(780).height(400).build();

        // Customize Chart
        Color[] sliceColors = new Color[] { Color.GREEN, Color.RED};
        chart.getStyler().setSeriesColors(sliceColors);

        // Series
//        chart.addSeries("Completed", ChartController.findAmountOFCompletedIssues(13,22));
//        chart.addSeries("Rejected", ChartController.findAmountOFRejectedIssues(13,22));

        return chart;
    }

    @Override
    public String getExampleChartName() {
        return null;
    }

}
