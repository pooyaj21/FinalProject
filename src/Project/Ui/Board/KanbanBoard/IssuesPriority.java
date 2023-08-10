package Project.Ui.Board.KanbanBoard;

import java.awt.*;

public enum IssuesPriority {
    LOW("Low",new Color(0x4CE808)),
    MEDIUM("Medium", new Color(0xFFFB00)),
    HIGH("High",new Color(0xFF0000));

    private final String name;
    private final Color color;

    IssuesPriority(String name, Color color) {
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
