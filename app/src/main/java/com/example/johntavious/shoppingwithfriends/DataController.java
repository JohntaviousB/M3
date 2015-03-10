package com.example.johntavious.shoppingwithfriends;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;

/**
 * Created by Clay on 3/7/2015.
 * This class directs the retrieval, saving, and
 * deletion of persistent data for the application.
 * Currently directs data to and from the SQLite database
 * Can be expanded to work with files or mySQL
 */
public class DataController  {
//    SQLHandler liteHandler = new SQLHandler(this, null, null, 3);
    private SQLHandler liteHandler;

    public DataController(Context context) {
        liteHandler = new SQLHandler(context, null, null, 3);
    }

/*    public boolean addUser(String name, String email, String password) {
        User user = new User (name, email, password);
        SQLHandler.addUser(user);
        return true;
    }  */

    public void addUser(User user) {
        liteHandler.addUser(user);
    }

    public User getUser(String name) {
        User user = liteHandler.getUser(name);
        return user;
    }

    public void addFriend(User user, User friend) {
        liteHandler.addFriend(user, friend);
    }

    public void addInterest(User user, Interest interest) {
        liteHandler.addInterest(user, interest);
    }

}


