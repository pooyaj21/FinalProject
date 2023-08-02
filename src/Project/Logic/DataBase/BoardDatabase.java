package Project.Logic.DataBase;

import Project.Logic.Board;
import Project.Logic.Issue;
import Project.Logic.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BoardDatabase {
    private static BoardDatabase instance;
    private final Map<Integer, Board> boards;
    private final Map<Board, ArrayList<Issue>> boardIssues;
    private final Map<Board, ArrayList<User>> boardMembers;

    private BoardDatabase() {
        boards = new HashMap<>();
        boardIssues = new HashMap<>();
        boardMembers = new HashMap<>();
    }

    public static BoardDatabase getInstance() {
        if (instance == null) {
            instance = new BoardDatabase();
        }
        return instance;
    }

    public void addBoard(Board board) {
        boards.put(board.getId(), board);
        boardIssues.put(board, new ArrayList<>());
        boardMembers.put(board, new ArrayList<>());
    }

    public void removeBoard(Board board) {
        boards.remove(board.getId());
        boardIssues.remove(board);
        boardMembers.remove(board);
    }

    public Map<Integer, Board> getBoards() {
        return boards;
    }

    public Map<Board, ArrayList<Issue>> getBoardIssues() {
        return boardIssues;
    }

    public Map<Board, ArrayList<User>> getBoardMembers() {
        return boardMembers;
    }
    public ArrayList<User> getAllMembersForBoard(Board board) {
        return boardMembers.getOrDefault(board, new ArrayList<>());
    }
}