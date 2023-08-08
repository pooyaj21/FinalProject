//package Project.Logic.DataBase;
//
//import Project.Logic.Role;
//import Project.Logic.User;
//
//public class UserManagement {
//    private static UserManagement instance;
//    private final UserDatabase userDataBase = UserDatabase.getInstance();
//
//    private UserManagement() {
//    }
//
//    public static UserManagement getInstance() {
//        if (instance == null) {
//            instance = new UserManagement();
//        }
//        return instance;
//    }
//
//    public void makeAccount(User user) {
//        if (!UserDatabase.getInstance().getUserMatches().containsKey(user.getEmail().toLowerCase())) {
//            UserDatabase.getInstance().getUserMatches().put(user.getEmail().toLowerCase(), user);
//            UserDatabase.getInstance().getUsers().add(user);
//        } else {
//            throw new IllegalArgumentException("Email already exists in the database: " + user.getEmail());
//        }
//    }
//
//    public void deleteUser(User user) {
//        String lowerCaseEmail = user.getEmail().toLowerCase();
//
//        if (userDataBase.getUserMatches().containsKey(lowerCaseEmail)) {
//            userDataBase.getUsers().remove(user);
//            userDataBase.getUserMatches().remove(lowerCaseEmail);
//        } else {
//            throw new IllegalArgumentException("User with email not found: " + user.getEmail());
//        }
//    }
//
//    public void checkEmail(String email) {
//        if (!emailAuthentication(email) || !isEmailExist(email)) {
//            throw new IllegalArgumentException("Email don't exists in the database: " + email);
//        }
//    }
//
//    public void checkPassword(String email, String password) {
//        checkEmail(email.toLowerCase());
//        if (!UserDatabase.getInstance().getUserMatches().get(email.toLowerCase()).getPassword().equals(password)) {
//            throw new IllegalArgumentException("Wrong password");
//        }
//    }
//
//    public boolean isEmailExist(String email) {
//        return UserDatabase.getInstance().getUserMatches().containsKey(email.toLowerCase());
//    }
//
//    public boolean emailAuthentication(String email) {
//        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
//    }
//
//    public void editUserEmail(User user, String newEmail) throws IllegalArgumentException, IllegalStateException {
//        if (!userDataBase.getUserMatches().containsKey(user.getEmail())) {
//            throw new IllegalArgumentException("User with old email not found");
//        }
//
//        if (userDataBase.getUserMatches().containsKey(newEmail)) {
//            throw new IllegalStateException("New email already belongs to another user");
//        }
//
//        userDataBase.getUserMatches().remove(user.getEmail());
//        user.setEmail(newEmail);
//        userDataBase.getUserMatches().put(newEmail, user);
//    }
//
//    // Function to edit user name
//    public void editUserName(User user, String newName) throws IllegalArgumentException {
//        user.setFullName(newName);
//    }
//
//    // Function to edit user password
//    public void editUserPassword(User user, String newPassword) throws IllegalArgumentException {
//        user.setPassword(newPassword);
//    }
//
//    // Function to edit user role
//    public void editUserRole(User user, Role newRole) throws IllegalArgumentException {
//        user.setRole(newRole);
//    }
//
//    public User getUser(String email){
//       return userDataBase.getUserMatches().get(email.toLowerCase());
//    }
//}
