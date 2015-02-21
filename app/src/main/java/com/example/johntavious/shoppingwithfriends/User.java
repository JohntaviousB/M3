package com.example.johntavious.shoppingwithfriends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a non-admin user of the application
 * @version 1.1
 */
public class User {
    private String name, email, password;
    private List<User> friends;
    private Map<User, Integer> salesSharedByUser; // a map of friends who shared sales with this User
    private int totalOfRatings, numOfRatings;

    protected User(String n, String e, String p) {
        this.name = n;
        this.email = e;
        setPassword(p);
        totalOfRatings = 0;
        friends = new ArrayList<>();
        salesSharedByUser = new HashMap<>();
    }

    /**
     * Attempts to change the User's name
     * @param n The new name
     * @return true if successful, false otherwise;
     */
    protected boolean setName(String n) {
        if (n != null && !n.equals("") && RegistrationActivity.isValidUsername(n)) {
            this.name = n;
            return true;
        }
        return false;
    }

    /**
     * Attempts to change the User's email
     * @param e The new email address
     * @return true if successful, false otherwise
     */
    protected boolean setEmail(String e) {
        if (LoginActivity.emailValid(e)) {
            this.email = e;
            return true;
        }
        return false;
    }

    /**
     * Attempts to change the User's password
     * @param p The new password
     * @return true if successful, false otherwise
     */
    protected boolean setPassword(String p) {
        if (p != null && p.length() >= 4) {
            this.password = p;
            return true;
        }
        return false;
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
     * @return true if successfully added to the list, false otherwise
     */
    protected boolean addFriend(User friend) {
        if (this.equals(friend)) {
            return false;
        }
        if (friend != null && !isFriendsWith(friend)) {
            friends.add(friend);
            if (!friend.isFriendsWith(this)) {
                friend.friends.add(this); //so friendship will be mutual
            }
            return true;
        }
        return false;
    }

    /**
     * Attempts to remove a User from the friends list
     * @param friend the User to be removed
     * @return true if successfully removed, false otherwise
     */
    protected boolean unfriend(User friend) {
        if (isFriendsWith(friend)) {
            friends.remove(friend);
            friend.friends.remove(this); //so deleting friends will be mutual
            return true;
        }
        return false;
    }

    /**
     * Returns whether or not to Users are friends
     * @param u the User to be determined if friends
     * @return true if friendship exists, false otherwise
     */
    protected boolean isFriendsWith(User u) {
        return friends.contains(u);
    }

    /**
     * Gets the average rating of this User
     * @return the averge rating
     */
    protected double getAverageRating() {
        if (numOfRatings == 0) return 0;
        return (double) totalOfRatings / numOfRatings;
    }

    /**
     * Posts a sale found by this User
     * TODO: THIS METHOD WILL NEED SOME SERIOUS FUNCTIONALITY UPDATES
     * Must also update (or insert for this User's first post) the number of sales posts by this user
     * in all the "salesSharedByUser" Maps of any friend who was interested in the item.
     * Also not quite sure on the parameters for this method just yet;
     */
    protected void postSale() {

    }
    /**
     * Returns the number of sales a friend has shared with this User
     * @param u the friend we wish to determine the number of sales shared with this User
     * @return the number of such sales
     */
    //TODO: fix method so it doesn't always return 0
    protected int getSalesReceivedByUser(User u) {
       return 0; //returns 0 until Sale posting functionality is proper
    }
    /**
     * Rates another friend
     * @param u the friend to rate
     * @param rating the rating to give the friend
     */
    protected void rate(User u, int rating) {
        if (isFriendsWith(u)) {
            u.totalOfRatings += rating;
            u.numOfRatings++;
        }
    }
    /**
     * Returns the user's list of friends
     * @return the list of friends
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
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name + " " + email + " ";
    }
}
