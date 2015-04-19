package com.example.johntavious.shoppingwithfriends;

import java.util.List;

/**
 * Classes implementing this interface should handle
 * the persistence of the application. I.e., they should
 * be connected to a database, or web server, etc. (persistent
 * structures).
 */
public interface DataController {
    int MIN_PASSWORD_LENGTH = 4;
    /**
     * Adds a user to the persistent structure.
     * @param user the user to add to the database
     */
    void addUser(User user);

    /**
     * Retrieves a user from the persistent structure.
     * @param email the email of the user to retrieve
     * @return the User from the database with the given email
     */
    User getUser(String email);

    /**
     * Retrieves a user from the persistent structure by username.
     * @param name the username of the user to retrieve
     * @return the User from the database with the given username
     */
    User getUserByName(String name);

    /**
     * Attempts to create a friendship between to Users.
     * The friendship is mutual, so parameter order is unimportant.
     * @param user one user associated with the friendship
     * @param friend the other user associated with the friendship
     * @return true if the friendship was successfully created, else false
     */
    boolean addFriend(User user, User friend);

    /**
     * Adds an interest to the database and associates it with a given user.
     * @param interest the interest of the user to add
     *                 to the persistent structure
     */
    void addInterest(Interest interest);

    /**
     * Terminates a friendship between two users.
     * The termination will be mutual so the order of
     * the parameters is unimportant.
     * @param u one user of the friendship
     * @param f another user of the friendship
     */
    void unfriend(User u, User f);
    /**
     * Adds a sale to the database.
     * @param sale the sale to add
     */

    void addSale(Sale sale);
    /**
     * Reports how many sales f has shared with u.
     * @param u the user logged in
     * @param f the friend of u
     * @return the number of such sales.
     */
    int getSalesShared(User u, User f);
    /**
     * Updates the User's profile.
     * @param u the User to be updated.
     */
    void updateUser(User u, String oldName);
    /**
     * Makes sure password is of valid length.
     * @param password the password to check
     * @return true if the password is long enough, false otherwise
     */
    boolean isPasswordValid(String password);

    /**
     * Checks the persistent structure to see if the email is contained.
     * @param email the email to check
     * @return true if the persistent structure contains the email
     */
    boolean isEmailValid(String email);

    /**
     * Checks the PS to see if the email can be added.
     * @param email the email to check.
     * @return true if the email can be added, false otherwise.
     */
    boolean emailValid(String email);

    /**
     * Checks the persistent structure to see if the username is contained
     * @param name the username to check
     * @return true if the persistent structure DOES NOT contain the name
     */
    boolean isValidUsername(String name);

    /**
     * Returns a list of ALL users in the persistent structure.
     * @return the list of all users.
     */
    List<User> getUsers();
}
