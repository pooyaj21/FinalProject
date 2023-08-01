package Project.Logic;

public class Board {
    private String name;
    private final int id;
    private static int counter;

    public Board(String title) {
        counter++;
        this.name = title;
        id=counter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
