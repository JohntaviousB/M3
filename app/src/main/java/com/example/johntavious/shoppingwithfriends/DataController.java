package com.example.johntavious.shoppingwithfriends;

import android.support.v7.app.ActionBarActivity;

/**
 * Created by Clay on 3/7/2015.
 * This class directs the retrieval, saving, and
 * deletion of persistent data for the application.
 * Currently directs data to and from the SQLite database
 * Can be expanded to work with files or mySQL
 */
public class DataController extends ActionBarActivity {
    private DBHandler dbHandler;

    public DataController() {
        dbHandler = new DBHandler(this, null, null, 3);
    }

    public boolean addUser(String name, String email, String password) {
        User user = new User (name, email, password);
        dbHandler.addUser(user);
        return true;
    }

    public User getUser(String name) {
        User user = dbHandler.getUser(name);
        return user;
    }

    public void addFriend(User user, User friend) {
        dbHandler.addFriend(user, friend);
    }

    public void addInterest(User user, Interest interest) {
        dbHandler.addInterest(user, interest);
    }

}


