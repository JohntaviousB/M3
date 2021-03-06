package com.example.johntavious.shoppingwithfriends;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Represents a Sale in the application.
 * Note that Sales can have an explicit location included
 * in the form of a String or, if the device is capable,
 * the latitude and longitude can be retrieved for the sale.
 */

final class Sale {
    private final String userName; // User who posted sale
    private final String item;
    private final double price;
    private String location;
    private Double latitude = null;
    private Double longitude = null;
    private String imageURI;
    private int id;
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
    public String getImageURI() {
        return imageURI;
    }
    public void setImageURI(String s) {
        imageURI = s;
    }
    /**
     * Sets the sale's id.
     * @param id the id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves this sale instance's id.
     * @return the id.
     */
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        return String.format("User:\t%s\nItem:\t%s\nPrice:\t%s", userName, item, fmt.format(price));
    }
}
