//package Project.Logic.DataBase;
//
//import Project.Logic.Board;
//import Project.Logic.Issue;
//
//import java.util.ArrayList;
//import java.util.Map;
//
//public class BoardManager {
//    private static BoardManager instance;
//
//    private BoardManager() {
//    }
//
//    public static BoardManager getInstance() {
//        if (instance == null) {
//            instance = new BoardManager();
//        }
//        return instance;
//    }
//
//    BoardDatabase boardDatabase = BoardDatabase.getInstance();
//
//    public ArrayList<Board> getAllBoards() {
//        return new ArrayList<>(boardDatabase.getBoards().values());
//    }
//
//    public void addIssueToBoard(Board board, Issue issue) {
//        ArrayList<Issue> boardIssuesList = boardDatabase.getBoardIssues().get(board);
//        if (boardIssuesList != null) {
//            boardDatabase.getBoardIssues().get(board).add(issue);
//        }
//    }
//
//    public void removeIssueFromBoard(Board board, Issue issue) {
//        ArrayList<Issue> boardIssuesList = boardDatabase.getBoardIssues().get(board);
//        if (boardIssuesList != null) {
//            boardDatabase.getBoardIssues().get(board).remove(issue);
//        }
//    }
//
//    public void editBoardName(Board board, String newName) {
//        if (board != null && newName != null) {
//            board.setName(newName);
//        }
//    }
//    public ArrayList<Board> getBoardsWithIssue(Issue issue) {
//        ArrayList<Board> result = new ArrayList<>();
//
//        for (Map.Entry<Board, ArrayList<Issue>> entry : boardDatabase.getBoardIssues().entrySet()) {
//            Board board = entry.getKey();
//            ArrayList<Issue> issues = entry.getValue();
//
//            if (issues.contains(issue)) {
//                result.add(board);
//            }
//        }
//
//        return result;
//    }
//
//    public ArrayList<Issue> getIssuesByBoard(Board board) {
//        if (board == null) throw new IllegalArgumentException("Board cannot be null");
//        return boardDatabase.getBoardIssues().getOrDefault(board, new ArrayList<>());
//    }
//
//}
