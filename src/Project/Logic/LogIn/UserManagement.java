package Project.Logic.LogIn;

public class UserManagement {
    private static UserManagement instance;

    private UserManagement() {
    }

    public static UserManagement getInstance() {
        if (instance == null) {
            instance = new UserManagement();
        }
        return instance;
    }

    public void makeAccount(String email, String password) {
        if (!UserDataBase.getInstance().getLoginInfo().containsKey(email)) {
            UserDataBase.getInstance().getLoginInfo().put(email, password);
        } else {
            throw new IllegalArgumentException("Email already exists in the database: " + email);
        }
    }

    public void changePassword(String email, String password) {
        if (UserDataBase.getInstance().getLoginInfo().containsKey(email) && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$\n")) {
            UserDataBase.getInstance().getLoginInfo().put(email, password);
        } else {
            throw new IllegalArgumentException("Email don't exists in the database: " + email);
        }
    }

    public void checkEmail(String email) {
       boolean emailAuthentication= email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
       boolean emailExist = UserDataBase.getInstance().getLoginInfo().containsKey(email);
        if(!emailAuthentication || !emailExist) {
                throw new IllegalArgumentException("Email don't exists in the database: " + email);
        }
    }

    public void checkPassword(String email, String password) {
        checkEmail(email);
        if (!UserDataBase.getInstance().getLoginInfo().get(email).equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        }
    }
}
