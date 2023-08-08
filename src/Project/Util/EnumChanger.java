package Project.Util;

import Project.Logic.Priority;
import Project.Logic.Role;
import Project.Logic.Status;
import Project.Logic.Type;

public class EnumChanger {
    public static String toString(Type type) {
        switch (type) {
            case STORY:
                return "Story";
            case TASK:
                return "Task";
            case BUG:
                return "Bug";
            default:throw new IllegalArgumentException("Enum didn't found");
        }
    }
    public static String toString(Priority priority) {
        switch (priority) {
            case LOW:
                return "Low";
            case MEDIUM:
                return "Medium";
            case HIGH:
                return "High";
            default:throw new IllegalArgumentException("Enum didn't found");
        }
    }

    public static String toString(Status status) {
        switch (status) {
            case TODO:
                return "ToDo";
            case IN_PROGRESS:
                return "In Progress";
            case QA:
                return "QA";
            case DONE:
                return "Done";
            default:throw new IllegalArgumentException("Enum didn't found");
        }
    }

    public static Role toEnumRole(String role) {
        switch (role) {
            case "SUPER_ADMIN":
                return Role.SUPER_ADMIN;
            case "PROJECT_OWNER":
                return Role.PROJECT_OWNER;
            case "QA":
                return Role.QA;
            case "DEVELOPER":
                return Role.DEVELOPER;
            default:throw new IllegalArgumentException("Enum didn't found");
        }
    }
    public static Type toEnumType(String type) {
        switch (type) {
            case "STORY":
                return Type.STORY;
            case "TASK":
                return Type.TASK;
            case "BUG":
                return Type.BUG;
            default:throw new IllegalArgumentException("Enum didn't found");
        }
    }
    public static Priority toEnumPriority(String priority) {
        switch (priority) {
            case "LOW":
                return Priority.LOW;
            case "MEDIUM":
                return Priority.MEDIUM;
            case "HIGH":
                return Priority.HIGH;
            default:throw new IllegalArgumentException("Enum didn't found");
        }
    }

    public static Status toEnumStatus(String status) {
        switch (status) {
            case "TODO":
                return Status.TODO;
            case "IN_PROGRESS":
                return Status.IN_PROGRESS;
            case "QA":
                return Status.QA;
            case "DONE":
                return Status.DONE;
            default:throw new IllegalArgumentException("Enum didn't found");
        }
    }

    public static String[] toStringArray(Type[] types) {
        String[] typeStrings = new String[types.length];
        for (int i = 0; i < types.length; i++) {
            typeStrings[i] = toString(types[i]);
        }
        return typeStrings;
    }

    public static String[] toStringArray(Priority[] priorities) {
        String[] priorityStrings = new String[priorities.length];
        for (int i = 0; i < priorities.length; i++) {
            priorityStrings[i] = toString(priorities[i]);
        }
        return priorityStrings;
    }

    public static String[] toStringArray(Status[] statuses) {
        String[] statusStrings = new String[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            statusStrings[i] = toString(statuses[i]);
        }
        return statusStrings;
    }
}

