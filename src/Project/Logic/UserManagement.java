package Project.Logic;

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

    public void makeAccount(User user) {
        if (!UserDataBase.getInstance().getLoginInfo().containsKey(user.getEmail().toLowerCase())) {
            UserDataBase.getInstance().getLoginInfo().put(user.getEmail().toLowerCase(), user.getPassword());
            UserDataBase.getInstance().getUsers().add(user);
        } else {
            throw new IllegalArgumentException("Email already exists in the database: " + user.getEmail());
        }
    }

    public void changePassword(String email, String password) {
        if (UserDataBase.getInstance().getLoginInfo().containsKey(email.toLowerCase()) && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$\n")) {
            UserDataBase.getInstance().getLoginInfo().put(email.toLowerCase(), password);
        } else {
            throw new IllegalArgumentException("Email don't exists in the database: " + email);
        }
    }

    public void checkEmail(String email) {
       boolean emailAuthentication= email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        if(!emailAuthentication || !isEmailExist(email)) {
                throw new IllegalArgumentException("Email don't exists in the database: " + email);
        }
    }

    public void checkPassword(String email, String password) {
        checkEmail(email.toLowerCase());
        if (!UserDataBase.getInstance().getLoginInfo().get(email.toLowerCase()).equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        }
    }

    public boolean isEmailExist(String email){
        return UserDataBase.getInstance().getLoginInfo().containsKey(email.toLowerCase());
    }
}
