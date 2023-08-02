package Project.Test;

import Project.Logic.*;
import Project.Logic.DataBase.BoardDatabase;
import Project.Logic.DataBase.BoardManager;
import Project.Logic.DataBase.UserDatabase;
import Project.Logic.DataBase.UserManagement;
import Project.Ui.KanbanBoard.KanbanBoardPanel;

import javax.swing.*;

public class KanbanBoardTest {
    UserManagement userManagement = UserManagement.getInstance();
    public static void main(String[] args) {
        UserManagement userManagement = UserManagement.getInstance();
        userManagement.makeAccount(new User("p@p1.com", "p", "pooya1", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p2.com", "p", "pooya2", Role.PROJECT_OWNER));
        userManagement.makeAccount(new User("p@p3.com", "p", "pooya3", Role.QA));
        userManagement.makeAccount(new User("p@p4.com", "p", "pooya4", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p5.com", "p", "pooya5", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p6.com", "p", "pooya6", Role.PROJECT_OWNER));
        userManagement.makeAccount(new User("p@p7.com", "p", "pooya7", Role.DEVELOPER));
        userManagement.makeAccount(new User("p@p8.com", "p", "pooya8", Role.DEVELOPER));
        // Create a new board
        Board board = new Board( "Sample Board");
        BoardDatabase.getInstance().addBoard(board);

        // Add some sample issues
        Issue issue1 = new Issue("Implement Feature A");
        issue1.setStatus(Status.TODO);
        Issue issue2 = new Issue("Fix Bug 101");
        issue2.setStatus(Status.IN_PROGRESS);
        Issue issue3 = new Issue( "Review Code");
        issue3.setStatus(Status.QA);
        Issue issue4 = new Issue("Document Feature B");
        issue4.setStatus(Status.DONE);

        BoardManager.getInstance().addIssueToBoard(board, issue1);
        BoardManager.getInstance().addIssueToBoard(board, issue2);
        BoardManager.getInstance().addIssueToBoard(board, issue3);
        BoardManager.getInstance().addIssueToBoard(board, issue4);

        // Create the Kanban board panel and set the board

        KanbanBoardPanel kanbanBoardPanel = new KanbanBoardPanel(board, UserDatabase.getInstance().getUsers().get(1));
        kanbanBoardPanel.setBoard(board);

        // Create the main frame and add the Kanban board panel to it
        JFrame frame = new JFrame("Kanban Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(kanbanBoardPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }
}
