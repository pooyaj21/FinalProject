package Project.Logic.LogIn;

import java.util.HashMap;

public class UserDataBase {
    private static UserDataBase instance;
    private final HashMap<String,String> loginInfo;

    private UserDataBase() {
        loginInfo = new HashMap<>();
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

}


