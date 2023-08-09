package Project.Ui.Charts;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

import java.awt.*;

public class TheChart implements ExampleChart<PieChart> {
    private int completedTasks;
    private int rejectedTasks;

    public TheChart(int completedTasks, int rejectedTasks) {
        this.completedTasks = completedTasks;
        this.rejectedTasks = rejectedTasks;
    }

    @Override
    public PieChart getChart() {
        PieChart chart = new PieChartBuilder().width(770).height(400).build();

        Color[] sliceColors = new Color[]{Color.GREEN, Color.RED};
        chart.getStyler().setSeriesColors(sliceColors);

        chart.addSeries("Completed",completedTasks);
        chart.addSeries("Rejected", rejectedTasks);

        return chart;
    }

    @Override
    public String getExampleChartName() {
        return null;
    }
}
