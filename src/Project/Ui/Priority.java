package Project.Ui;

import java.awt.*;

public enum Priority {
    LOW("Low",Color.GREEN),
    MEDIUM("Medium",Color.yellow),
    HIGH("High",Color.RED);
    //colors are placeholder
    final String name;
    final Color color;

    Priority(String name, Color color) {
        this.name = name;
        this.color = color;
    }
}
