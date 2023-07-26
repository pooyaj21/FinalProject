package Project.Logic;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDataBase {
    private static UserDataBase instance;
    private final HashMap<String, User> userMatches;
    private final ArrayList<User> users;

    private UserDataBase() {
        userMatches = new HashMap<>();
        users = new ArrayList<>();
        User superAdmin = new User("admin@a.com", "admin", "Super Admin", Role.SUPER_ADMIN);
        userMatches.put(superAdmin.getEmail(), superAdmin);
        users.add(superAdmin);
    }

    public static UserDataBase getInstance() {
        if (instance == null) {
            instance = new UserDataBase();
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


