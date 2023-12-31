package Project.Ui.Board.KanbanBoard;

import java.awt.*;

public enum IssuesTypes {
    STORY("Story",new Color(0x78C257)),
    TASK("Task", new Color(0x00ABC0)),
    BUG("Bug",new Color(0xA98D00));

    private final String name;
    private final Color color;

    IssuesTypes(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
