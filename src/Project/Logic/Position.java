package Project.Logic;

public enum Position {
    ADMIN("Admin",0),
    PROJECT_OWNER("Project Owner",1),
    DEVELOPER("Developer",2),
    QA("QA",3);
    final String name;
    final int levelOfAccess;

    Position(String name, int levelOfAccess) {
        this.name = name;
        this.levelOfAccess = levelOfAccess;
    }
}
