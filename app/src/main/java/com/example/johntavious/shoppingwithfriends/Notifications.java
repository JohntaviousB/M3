package com.example.johntavious.shoppingwithfriends;

/**
 * Created by Clay on 3/11/2015.
 */
public class Notifications {
    private int id;
    private String userName;
    private Sale sale;

    /**
     * Gets the userName of the user who reported the sale
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the Sale
     * @return the Sale
     */
    public Sale getSale() {
        return sale;
    }

}
