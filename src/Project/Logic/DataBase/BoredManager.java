package Project.Logic.DataBase;

import Project.Logic.Board;
import Project.Logic.Issue;
import Project.Logic.User;

import java.util.ArrayList;

public class BoredManager {
    private static BoredManager instance;

    private BoredManager() {
    }

    public static BoredManager getInstance() {
        if (instance == null) {
            instance = new BoredManager();
        }
        return instance;
    }

    BoardDatabase boardDatabase = BoardDatabase.getInstance();

    public void addMemberToBoard(Board board, User member) {
        ArrayList<User> boardMembersList = boardDatabase.getBoardMembers().get(board);
        if (boardMembersList != null) {
            boardDatabase.getBoardMembers().get(board).add(member);
        }
    }

    public void removeMemberFromBoard(Board board, User member) {
        ArrayList<User> boardMembersList = boardDatabase.getBoardMembers().get(board);
        if (boardMembersList != null) {
            boardDatabase.getBoardMembers().get(board).remove(member);
        }
    }

    public void addIssueToBoard(Board board, Issue issue) {
        ArrayList<Issue> boardIssuesList = boardDatabase.getBoardIssues().get(board);
        if (boardIssuesList != null) {
            boardDatabase.getBoardIssues().get(board).add(issue);
        }
    }

    public void removeIssueFromBoard(Board board, Issue issue) {
        ArrayList<Issue> boardIssuesList = boardDatabase.getBoardIssues().get(board);
        if (boardIssuesList != null) {
            boardDatabase.getBoardIssues().get(board).remove(issue);
        }
    }
}
