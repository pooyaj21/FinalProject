package Project.Logic;

import java.awt.*;

public enum Type {
    STORY("story"),
    TASK("task"),
    BUG("Bug");
    private final String name;
    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
