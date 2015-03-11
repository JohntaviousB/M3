package com.example.johntavious.shoppingwithfriends;

/**
 * Created by Clay on 3/11/2015.
 */
public class Sale {
    private int id;
    private String item;
    private double price;
    private String location;

    /**
     * Gets the item name
     * @return the item name
     */
    public String getItem() {
        return item;
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


}
