package com.example.johntavious.shoppingwithfriends;

/**
 * Created by Johntavious on 2/8/2015.
 */
public class User {
    private String name, email, password;

    protected User(String n, String e, String p) {
        this.name = n;
        this.email = e;
        this.password = p;
    }
    protected void setName(String n) {
        this.name = n;
    }
    protected void setEmail(String e) {
        this.email = e;
    }
    protected void setPassword(String p) {
        this.password = p;
    }
    protected String getName() {
        return this.name;
    }
    protected String getEmail() {
        return this.email;
    }
    protected String getPassword() {
        return this.password;
    }
    public String toString() {
        return name + " " + email + " ";
    }
}
