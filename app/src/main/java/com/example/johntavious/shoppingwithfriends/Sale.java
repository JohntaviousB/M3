package com.example.johntavious.shoppingwithfriends;

/**
 * Represents a Sale in the application.
 * Note that Sales can have an explicit location included
 * in the form of a String or, if the device is capable,
 * the latitude and longitude can be retrieved for the sale.
 */

public final class Sale {
    private int id;
    private String userName; // User who posted sale
    private String item;
    private double price;
    private String location;
    private Double latitude = null;
    private Double longitude = null;
    /**
     * Constructor to initialize a Sale when lat/lng was
     * unable to be retrieved.
     * @param n the username of the User who posted the sale
     * @param l the location of the sale
     * @param i the name of the item
     * @param p the price of the item
     */
    public Sale(String n, String i, double p, String l) {
        this.userName = n;
        this.item = i;
        this.price = p;
        this.location = l;
    }

    /**
     * Constructor to initialize a Sale when Lat/Lng was
     * successfully retrieved.
     * @param n the username of the User of who posted the sale
     * @param i the name of the item
     * @param p the price of the item
     * @param lat the latitude of the sale
     * @param lon the longitude of the sale
     */
    public Sale(String n, String i, double p, double lat, double lon) {
        this.userName = n;
        this.item = i;
        this.price = p;
        this.latitude = lat;
        this.longitude = lon;
    }
    /**
     * No arg constructor for a Sale.
     */
    public Sale() { }
    /**
     * Gets the sale id.
     * @return the sale id
     */
    public int getId() {
        return id;
    }
    /**
     * Gets the item name.
     * @return the item name
     */
    public String getItem() {
        return item;
    }

    /**
     * Gets the userName.
     * @return the userName associated with the sale
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Gets the item price.
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the location of the Sale.
     * @return the Sale's location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the Sale's id.
     * @param id the id of the Sale
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Sets the item name.
     * @param item the name of the item
     */
    public void setItem(String item) {
        this.item = item;
    }
    /**
     * Gets the latitude of the Sale.
     * @return the latitude of the Sale, null if no latitude was given
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude of the Sale.
     * @return the longitude of the Sale, null if no longitude was given
     */
    public Double getLongitude() {
        return longitude;
    }
}
