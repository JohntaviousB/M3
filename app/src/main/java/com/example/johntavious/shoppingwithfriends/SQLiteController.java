package com.example.johntavious.shoppingwithfriends;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * This class directs the retrieval, saving, and
 * deletion of persistent data for the application.
 * Currently directs data to and from the SQLite database
 */
public final class SQLiteController implements DataController {
    private final SQLHandler liteHandler;

    /**
     * Constructor for the data controller to be connected to a database.
     * @param context the context of the data controller
     */
    public SQLiteController(Context context) {
        liteHandler = new SQLHandler(context);
    }

    /**
     * Adds a user to the database.
     * @param user the user to add to the database
     */
    @Override
    public void addUser(User user) {
        liteHandler.addUser(user);
    }

    /**
     * Retrieves a user from the database.
     * @param email the email of the user to retrieve
     * @return the User from the database with the given email
     */
    @Override
    public User getUser(String email) {
        return liteHandler.getUser(email);
    }

    /**
     * Retrieves a user from the database.
     * @param name the username of the user to retrieve
     * @return the User from the database with the given username
     */
    @Override
    public User getUserByName(String name) {
        return liteHandler.getUserByName(name);
    }

    /**
     * Attempts to create a friendship between to Users.
     * The friendship is mutual, so parameter order is unimportant.
     * @param user one user associated with the friendship
     * @param friend the other user associated with the friendship
     * @return true if the friendship was successfully created, else false
     */
    @Override
    public boolean addFriend(User user, User friend) {
        return liteHandler.addFriend(user, friend);
    }

    /**
     * Adds an interest to the database and associates it with a given user.
     * @param interest the interest of the user to add to the database
     */
    @Override
    public void addInterest(Interest interest) {
        liteHandler.addInterest(interest);
    }

    /**
     * Terminates a friendship between two users.
     * The termination will be mutual so the order of
     * the parameters is unimportant.
     * @param u one user of the friendship
     * @param f another user of the friendship
     */
    @Override
    public void unfriend(User u, User f) {
        liteHandler.unfriend(u, f);
    }
    @Override
    public void addSale(Sale sale) {
        liteHandler.addSale(sale);
    }

    @Override
    public int getSalesShared(User u, User f) {
        return liteHandler.getSalesShared(u, f);
    }

    @Override
    public void updateUser(User u, String oldName) {
        liteHandler.updateUser(u, oldName);
    }

    @Override
    public List<User> getUsers() {
        return liteHandler.syncRegisteredUsers();
    }

    @Override
    public Map<String, String> getAdmins() {
        return liteHandler.syncAdmins();
    }

    @Override
    public List<Sale> getSales() {
        return liteHandler.getSales();
    }

    @Override
    public List<Interest> getInterests() {
        return liteHandler.getInterests();
    }

    @Override
    public boolean isEmailValid(String email) {
        return liteHandler.isEmailValid(email);
    }

    @Override
    public boolean emailValid(String email) {
        return liteHandler.emailValid(email);
    }

    @Override
    public boolean isValidUsername(String name) {
        return liteHandler.isValidUsername(name);
    }
    public boolean isPasswordValid(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH;
    }
    public void deleteUser(User u) {
        liteHandler.deleteUser(u);
    }
    public void deleteInterest(Interest i) {
        liteHandler.deleteInterest(i);
    }
    public void deleteSale(Sale s) {
        liteHandler.deleteSale(s);
    }
}

