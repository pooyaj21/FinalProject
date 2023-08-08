//package Project.Logic.DataBase;
//
//import Project.Logic.Board;
//import Project.Logic.Issue;
//import Project.Logic.User;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class BoardDatabase {
//    private static BoardDatabase instance;
//    private final Map<Integer, Board> boards;
//    private final Map<Board, ArrayList<Issue>> boardIssues;
//
//    private BoardDatabase() {
//        boards = new HashMap<>();
//        boardIssues = new HashMap<>();
//    }
//
//    public static BoardDatabase getInstance() {
//        if (instance == null) {
//            instance = new BoardDatabase();
//        }
//        return instance;
//    }
//
//    public void addBoard(Board board) {
//        boards.put(board.getId(), board);
//        boardIssues.put(board, new ArrayList<>());
//    }
//
//    public void removeBoard(Board board) {
//        boards.remove(board.getId());
//        boardIssues.remove(board);
//    }
//
//    public Map<Integer, Board> getBoards() {
//        return boards;
//    }
//
//    public Map<Board, ArrayList<Issue>> getBoardIssues() {
//        return boardIssues;
//    }
//
//}