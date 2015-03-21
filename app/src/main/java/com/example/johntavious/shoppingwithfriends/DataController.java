package com.example.johntavious.shoppingwithfriends;

import android.content.Context;

/**
 * This class directs the retrieval, saving, and
 * deletion of persistent data for the application.
 * Currently directs data to and from the SQLite database
 * Can be expanded to work with files or mySQL
 */
public class DataController  {
    private SQLHandler liteHandler;

    /**
     * Constructor for the data controller to be connected to a database
     * @param context the context of the data controller
     */
    public DataController(Context context) {
        liteHandler = new SQLHandler(context, null, null, 6);
    }

    /**
     * Adds a user to the database
     * @param user the user to add to the database
     */
    public void addUser(User user) {
        liteHandler.addUser(user);
    }

    /**
     * Retrieves a user from the database
     * @param email the email of the user to retrieve
     * @return the User from the database with the given email
     */
    public User getUser(String email) {
        return liteHandler.getUser(email);
    }

    /**
     * Retrieves a user from the database
     * @param name the username of the user to retrieve
     * @return the User from the database with the given username
     */
    public User getUserByName(String name) {
        return liteHandler.getUserByName(name);
    }

    /**
     * Attempts to create a friendship between to Users
     * The friendship is mutual, so parameter order is unimportant
     * @param user one user associated with the friendship
     * @param friend the other user associated with the friendship
     * @return true if the friendship was successfully created, else false
     */
    public boolean addFriend(User user, User friend) {
        return liteHandler.addFriend(user, friend);
    }

    /**
     * Adds an interest to the database and associates it with a given user
     * @param user the user whose interest is to be added to the database
     * @param interest the interest of the user to add to the database
     */
    public void addInterest(User user, Interest interest) {
        liteHandler.addInterest(user, interest);
    }

    /**
     * Terminates a friendship between two users
     * The termination will be mutual so the order of
     * the parameters is unimportant
     * @param u one user of the friendship
     * @param f another user of the friendship
     */
    public void unfriend(User u, User f) {
        liteHandler.unfriend(u, f);
    }
    /**
     * Adds a sale to the database
     * @param sale the sale to add
     */
    public void addSale(Sale sale) {
        liteHandler.addSale(sale);
    }
}


