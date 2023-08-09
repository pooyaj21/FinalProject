package Project.Ui.Charts;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

import java.awt.*;

public class UserChart implements ExampleChart<PieChart> {
    private final int completedTasks;
    private final int rejectedTasks;

    public UserChart(int completedTasks, int rejectedTasks) {
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
