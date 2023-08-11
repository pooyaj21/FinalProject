package Project.Ui.Charts;

import Project.Logic.DataBase.SQL.CrossTabel.UserProjectDataBaseSql;
import Project.Logic.Project;
import Project.Logic.User;
import Project.Util.GeneralController;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;

import java.awt.*;
import java.util.ArrayList;

public class MultiUserChart implements ExampleChart<PieChart> {
    private final Project  project;

    public MultiUserChart(Project project) {
        this.project = project;
    }

    @Override
    public PieChart getChart() {
        PieChart chart = new PieChartBuilder().width(770).height(400).build();
        ArrayList<Color> colors = new ArrayList<>();
        for (User user : UserProjectDataBaseSql.getInstance().getAllUsersOfProject(project.getId())) {
            colors.add(GeneralController.generateRandomColor());
            chart.addSeries(user.getFullName(), ChartController.findAmountOFCompletedIssues(project.getId(),user.getId()));
        }
        chart.getStyler().setSeriesColors(colors.toArray(new Color[colors.size()]));

        return chart;
    }

    @Override
    public String getExampleChartName() {
        return null;
    }
}
