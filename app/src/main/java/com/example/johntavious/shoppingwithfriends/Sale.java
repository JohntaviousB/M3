package com.example.johntavious.shoppingwithfriends;


public class Sale {
    private int id;
    private String userName; // User who posted sale
    private String item;
    private double price;
    private String location;
    private Double latitude = null, longitude = null;

    public Sale(String n, String i, double p, String l) {
        this.userName = n;
        this.item = i;
        this.price = p;
        this.location = l;
    }
    public Sale(String n, String i, double p, double lat, double lon) {
        this.userName = n;
        this.item = i;
        this.price = p;
        this.latitude = lat;
        this.longitude = lon;
    }
    public Sale(){}
    /**
     * Gets the sale id
     * @return the sale id
     */
    public int getId() {
        return id;
    }
    /**
     * Gets the item name
     * @return the item name
     */
    public String getItem() {
        return item;
    }

    /**
     * Gets the userName
     * @return the userName associated with the sale
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Gets the item price
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the location of the Sale
     * @return the Sale's location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the Sale's id
     * @param id the id of the Sale
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the userName
     * @param name the name of the user who posted the sale
     */
    public void setUserName(String name) {
        userName = name;
    }
    /**
     * Sets the item
     * @param item the name of the item
     */
    public void setItem(String item) {
        this.item = item;
    }

    /**
     * Sets the price of the item
     * @param price the price of the item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the Sale's location
     * @param location the location of the item
     */
    public void setLocation(String location) {
        this.location = location;
    }
    public void setLatitude(double lat) {
        this.latitude = lat;
    }
    public void setLongitude(double lon) {
        this.longitude = lon;
    }
    public Double getLatitude() {
        return latitude;
    }
    public Double getLongitude() {
        return longitude;
    }

}
