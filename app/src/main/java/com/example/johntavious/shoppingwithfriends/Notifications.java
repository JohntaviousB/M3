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

    /**
     * Sets the Notification's id
     * @param id the id of the Notification
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the userName of the user who reported the Sale
     * @param name the name of the user who reported the Sale
     */
    public void setUserName(String name) {
        this.userName = userName;
    }

    /**
     * Sets the Sale associated with the Notification
     * @param sale the Sale associated with the Notification
     */
    public void setSale(Sale sale) {
        this.sale = sale;
    }

}
