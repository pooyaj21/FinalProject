package Project.Test;

import Project.Logic.DataBase.ProjectManager;
import Project.Logic.DataBase.UserDatabase;
import Project.Logic.DataBase.UserManagement;
import Project.Logic.Issue;
import Project.Logic.Priority;
import Project.Logic.Status;
import Project.Logic.Type;
import Project.Ui.IssueTrackerPanel;

import javax.swing.*;

public class IssuesTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Issue Tracker App");
            frame.setSize(780, 650);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ProjectManager projectManager =ProjectManager.getInstance();
            projectManager.createProject("a");

            Issue issue1 = new Issue("A");
            issue1.setStatus(Status.TODO);
            issue1.setType(Type.BUG);
            issue1.setPriority(Priority.LOW);

            Issue issue2 = new Issue("B");
            issue2.setStatus(Status.DONE);
            issue2.setType(Type.TASK);
            issue2.setPriority(Priority.HIGH);

            projectManager.createIssue(ProjectManager.getInstance().getAllProjects().get(0),issue1);
            projectManager.createIssue(ProjectManager.getInstance().getAllProjects().get(0),issue2);
            IssueTrackerPanel issueTrackerPanel = new IssueTrackerPanel(ProjectManager.getInstance().getAllProjects().get(0), UserDatabase.getInstance().getUsers().get(0));
            frame.add(issueTrackerPanel);

            frame.setVisible(true);
        });
    }
}
