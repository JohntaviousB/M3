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
    private SQLHandler liteHandler;

    public DataController(Context context) {
        liteHandler = new SQLHandler(context, null, null, 5);
    }

/*    public boolean addUser(String name, String email, String password) {
        User user = new User (name, email, password);
        SQLHandler.addUser(user);
        return true;
    }  */

    public void addUser(User user) {
        liteHandler.addUser(user);
    }

    public User getUser(String email) {
        return liteHandler.getUser(email);
    }

    public User getUserByName(String name) {
        return liteHandler.getUserByName(name);
    }

    public void getFriends(User user) {
        liteHandler.getFriends(user);
    }

    public boolean addFriend(User user, User friend) {
        return liteHandler.addFriend(user, friend);
    }

    public void addInterest(User user, Interest interest) {
        liteHandler.addInterest(user, interest);
    }
    public void unfriend (User u, User f) {
        liteHandler.unfriend(u, f);
    }
    public void addSale(Sale sale) {
        liteHandler.addSale(sale);
    }

    public Sale getSale(String userName) {
        return liteHandler.getSale(userName);
    }
}


