package com.example.johntavious.shoppingwithfriends;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a non-admin user of the application
 * @version 1.0
 */
public class User {
    private String name, email, password;
    private List<User> friends;

    protected User(String n, String e, String p) {
        setName(n);
        setEmail(e);
        setPassword(p);
        friends = new ArrayList<>();
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

    /**
     * Attempts to add a new user to the friends list
     * @param friend the User to be added to the friends list
     * @return true if successfully added, false otherwise
     */
    protected boolean addFriend(User friend) {
        if (this.equals(friend)) {
            return false;
        }
        if (friend != null && !friends.contains(friend)) {
            friends.add(friend);
            return true;
        }
        return false;
    }

    /**
     * Returns the user's list of friends
     * @return
     */
    protected List<User> getFriends() {
        return friends;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof User)) return false;
        User that = (User) other;
        return this.name.equals(that.name) && this.email.equals(that.email);
    }
    @Override
    public String toString() {
        return name + " " + email + " ";
    }
}
