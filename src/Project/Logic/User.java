package Project.Logic;

import java.util.HashMap;

public class User {
    private int id;
    private String Email;
    private String password;
    private String fullName;
    private Role role;

    public User(String Email, String password, String fullName, Role role) {
        this.Email = Email;
        this.password = password;
        this.fullName = fullName;
        this.role=role;
    }
    public User(int id,String Email, String password, String fullName, Role role) {
        this.id=id;
        this.Email = Email;
        this.password = password;
        this.fullName = fullName;
        this.role=role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
