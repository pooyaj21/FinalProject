package Project.Logic.DataBase;

import Project.Logic.Board;
import Project.Logic.Issue;
import Project.Logic.User;

import java.util.ArrayList;

public class BoardManager {
    private static BoardManager instance;

    private BoardManager() {
    }

    public static BoardManager getInstance() {
        if (instance == null) {
            instance = new BoardManager();
        }
        return instance;
    }

    BoardDatabase boardDatabase = BoardDatabase.getInstance();

    public ArrayList<Board> getAllBoards() {
        return new ArrayList<>(boardDatabase.getBoards().values());
    }

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

    public void editBoardName(Board board, String newName) {
        if (board != null && newName != null) {
            board.setName(newName);
        }
    }

    public void addUserToBoard(Board board, User user) {
        ArrayList<User> boardMembersList = boardDatabase.getBoardMembers().get(board);
        if (boardMembersList != null && user != null) {
            boardMembersList.add(user);
        }
    }

    public ArrayList<User> getMembersOfBoard(Board board) {
        return boardDatabase.getBoardMembers().getOrDefault(board, new ArrayList<>());
    }

}
