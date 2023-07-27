package Project.Logic.DataBase;

import Project.Logic.Role;
import Project.Logic.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDatabase {
    private static UserDatabase instance;
    private final HashMap<String, User> userMatches;
    private final ArrayList<User> users;

    private UserDatabase() {
        userMatches = new HashMap<>();
        users = new ArrayList<>();
        User superAdmin = new User("admin@a.com", "admin", "Super Admin", Role.SUPER_ADMIN);
        userMatches.put(superAdmin.getEmail(), superAdmin);
        users.add(superAdmin);
    }

    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public HashMap<String, User> getUserMatches() {
        return userMatches;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}


