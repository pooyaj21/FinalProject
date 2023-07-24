package Project.Logic;

import java.awt.*;

public enum priority {
    LOW("low",Color.GREEN),
    MEDIUM("Medium",Color.yellow),
    HIGH("High",Color.RED);

    private final String name;
    private final Color color;

    priority(String name, Color color) {
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
