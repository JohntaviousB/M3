package com.example.johntavious.shoppingwithfriends;

/**
 * Represents a non-admin user of the application
 */
public class User {
    private String name, email, password;

    protected User(String n, String e, String p) {
        setName(n);
        setEmail(e);
        setPassword(p);
    }

    /**
     * Changes the User's name
     * @param n The new name
     */
    protected void setName(String n) {
        if (n != null && !n.equals("")) {
            this.name = n;
        }
    }

    /**
     * Changes the User's email
     * @param e The new email address
     */
    protected void setEmail(String e) {
        if (e != null && !e.equals("")) {
            this.email = e;
        }
    }

    /**
     * Changes the User's password
     * @param p The new password
     */
    protected void setPassword(String p) {
        if (p != null && p.length() >= 4) {
            this.password = p;
        }
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
