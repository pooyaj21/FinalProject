package Project.Logic;

public enum Status {
    TODO("ToDo"),
    IN_PROGRESS("In Progress"),
    DONE("Done");
    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
