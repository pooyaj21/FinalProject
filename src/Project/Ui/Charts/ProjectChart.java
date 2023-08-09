package Project.Ui.Charts;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

import java.awt.*;

public class ProjectChart implements ExampleChart<PieChart> {
    private final int todoTask;
    private final int inProgressTask;
    private final int qaTask;
    private final int doneTask;

    public ProjectChart(int todoTask, int inProgressTask, int qaTask, int doneTask) {
        this.todoTask = todoTask;
        this.inProgressTask = inProgressTask;
        this.qaTask = qaTask;
        this.doneTask = doneTask;
    }

    @Override
    public PieChart getChart() {
        PieChart chart = new PieChartBuilder().width(770).height(400).build();

        Color[] sliceColors = new Color[]{Color.RED, Color.BLUE,Color.yellow,Color.GREEN};
        chart.getStyler().setSeriesColors(sliceColors);

        chart.addSeries("ToDo",todoTask);
        chart.addSeries("In Progress", inProgressTask);
        chart.addSeries("QA", qaTask);
        chart.addSeries("Done", doneTask);

        return chart;
    }

    @Override
    public String getExampleChartName() {
        return null;
    }
}
