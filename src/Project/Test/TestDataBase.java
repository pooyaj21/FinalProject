package Project.Test;

import Project.Logic.DataBase.SQL.ProjectDatabaseSQL;
import Project.Logic.DataBase.SQL.UserDataBaseSQL;
import Project.Logic.Role;
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

        UserDataBaseSQL.getInstance().addUser("The Almighty Admin","admin@a.com","admin", Role.SUPER_ADMIN);


    }
}
