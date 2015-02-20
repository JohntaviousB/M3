package com.example.johntavious.shoppingwithfriends;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Johntavious on 2/8/2015.
 */
public class User {
    // temp friendMap for workaround M5
    private static final HashMap<String, ArrayList<String>> friendMap = new HashMap<>();
    private String name, email, password;

    protected User(String n, String e, String p) {
        this.name = n;
        this.email = e;
        this.password = p;
        friendMap.put(name, new ArrayList<String>());
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

    // Temp static methods for workaround M5
    protected static void addFriend(String uName, String uEmail, String name, String email) {
        if (LoginActivity.searchUser(name, email)) {
            friendMap.get(uName).add(name + ": " + email);
            friendMap.get(name).add(uName + ": " + uEmail);
        }
    }
    protected static String[] getFriends(String name) {
        ArrayList<String> friendsList = friendMap.get(name);
        int length = friendsList.size();
        String[] friendsArray = new String[length];
        for (int i = 0; i < length; i++) {
            friendsArray[i] = friendsList.get(i);
        }
        return friendsArray;
    }
}
