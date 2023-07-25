package Project.Logic;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDataBase {
    private static UserDataBase instance;
    private final HashMap<String,String> loginInfo;
    private final ArrayList<User> users;

    private UserDataBase() {
        loginInfo = new HashMap<>();
        users=new ArrayList<>();
        loginInfo.put("admin@a.com","admin");
    }

    public static UserDataBase getInstance() {
        if (instance == null) {
            instance = new UserDataBase();
        }
        return instance;
    }
    public HashMap<String,String> getLoginInfo(){
        return loginInfo;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}


