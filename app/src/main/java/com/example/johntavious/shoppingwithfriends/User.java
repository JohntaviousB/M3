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

    /**
     * Changes the User's name
     * @param n The new name
     */
    protected void setName(String n) {
        this.name = n;
    }

    /**
     * Changes the User's email
     * @param e The new email address
     */
    protected void setEmail(String e) {
        this.email = e;
    }

    /**
     * Changes the User's password
     * @param p The new password
     */
    protected void setPassword(String p) {
        this.password = p;
    }

    /**
     * Returns the User's name
     * @return the User's name
     */
    protected String getName() {
        return this.name;
    }

    /**
     * Returns the User's email
     * @return the User's email
     */
    protected String getEmail() {
        return this.email;
    }

    /**
     * Returns the User's password
     * @return the User's password
     */
    protected String getPassword() {
        return this.password;
    }
    @Override
    public String toString() {
        return name + " " + email + " ";
    }
}
