package com.example.johntavious.shoppingwithfriends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents a non-admin user of the application.
 */
public final class User {
    private String name;
    private String email;
    private String password;
    private int id;
    private List<String> friends = new ArrayList<>();
    private Map<User, Integer> salesSharedByUser = new HashMap<>();
    private List<Interest> interests = new ArrayList<>();
                    // a map of friends who shared sales with this User
    private LinkedList<Notification> notifications = new LinkedList<>();
    private int totalOfRatings;
    private int numOfRatings;

    /**
     * A no arg constructor for a User.
     */
    public User() { }

    /**
     * Initializes the username, email, and password of this user.
     * @param n the username
     * @param e the email
     * @param p the password
     */
    public User(String n, String e, String p) {
        setName(n);
        setEmail(e);
        setPassword(p);
        totalOfRatings = 0;
        numOfRatings = 0;
    }

    /**
     * Sets the username of this user.
     * @param name the new username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the email of this user.
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Sets the password of this user.
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the id of the user.
     * @param id the new id of the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the User's username.
     * @return the User's username
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the User's email.
     * @return the User's email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Returns the User's password.
     * @return the User's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Gets the id of this User.
     * @return the id of this user
     */
    public int getId() {
        return id;
    }

    /**
     * Adds friend to the user's list of friends.
     * @param friend the username of the friend to add
     */
    public void addFriend(String friend) {
        friends.add(friend);
    }

    /**
     * Attempts to remove a User from the friends list.
     * @param friend the User to be removed
     * @return true if successfully removed, false otherwise
     */
    public boolean unfriend(User friend) {
        if (isFriendsWith(friend)) {
            friends.remove(friend.getName());
            friend.friends.remove(this.getName());
            //so deleting friends will be mutual
            return true;
        }
        return false;
    }

    /**
     * Returns whether or not to Users are friends.
     * @param u the User to be determined if friends
     * @return true if friendship exists, false otherwise
     */
    public boolean isFriendsWith(User u) {
        for (String each : friends) {
            if (each.equalsIgnoreCase(u.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the average rating of this User.
     * @return the average rating
     */
    public double getAverageRating() {
        if (numOfRatings == 0) {
            return 0;
        }
        return (double) totalOfRatings / numOfRatings;
    }
    /**
     * Returns the number of sales a friend has shared with this User.
     * @param u the friend we wish to determine the
     *          number of sales shared with this User
     * @return the number of such sales
     */
    public int getSalesReceivedByUser(User u) {
        return 0;
    }

    /**
     * Adds a new notification to the User's list of notifications.
     * @param n the notification to add
     */
    public void addNotification(Notification n) {
        notifications.addFirst(n);
    }

    /**
     * Return the list of notifications
     * @return the list of notifications
     */
    public List<Notification> getNotifications() {
        return notifications;
    }
    /**
     * Attempts to add a new Interest to the list of interests.
     * @param interest the new Interest to add
     * @return true if successfully added, false otherwise
     */
    public boolean registerInterest(Interest interest) {
        return interests.add(interest);
    }

    /**
     * Returns the user's list of friends.
     * @return the list of friends
     */
    public List<String> getFriends() {
        return friends;
    }

    /**
     * Rates another friend
     * @param u the friend to rate
     * @param rating the rating to give the friend
     */
    public void rate(User u, int rating) {
        if (isFriendsWith(u)) {
            u.totalOfRatings += rating;
            u.numOfRatings++;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof User)) {
            return false;
        }
        User that = (User) other;
        return this.name.equalsIgnoreCase(that.name)
                && this.email.equalsIgnoreCase(that.email);
    }

    /**
     * Returns this User's list of interests.
     * @return the list of interests
     */
    public List<Interest> getInterests() {
        return interests;
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
