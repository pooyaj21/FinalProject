package Project.Logic;

public enum Role {
    SUPER_ADMIN(0),
    PROJECT_OWNER(1),
    DEVELOPER(2),
    QA(3);
    final int levelOfAccess;

    Role( int levelOfAccess) {
        this.levelOfAccess = levelOfAccess;
    }
}
