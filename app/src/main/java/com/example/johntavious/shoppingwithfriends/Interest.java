package com.example.johntavious.shoppingwithfriends;

import java.text.NumberFormat;

/**
 * Represents a User's interest in the application.
 * @version 1.0
 */
final class Interest {
    private String itemName;
    private double thresholdPrice;
    private int distance; //the max distance willing to travel
    private String user;

    /**
     * No arg constructor for an Interest.
     */
    public Interest() { }

    /**
     * Constructor that initializes an Interest with the given parameters.
     * @param item the item name of interest
     * @param price the threshold price of the interest
     * @param dist the maximum distance willing to travel for the item
     */
    public Interest(String item, double price, int dist, String u) {
        this.itemName = item;
        this.thresholdPrice = price;
        this.distance = dist;
        this.user = u;
    }

    /**
     * Gets the item name.
      * @return the item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Gets the threshold price.
     * @return the threshold price
     */
    public double getThresholdPrice() {
        return thresholdPrice;
    }

    /**
     * Gets the threshold distance.
     * @return the threshold distance for the item
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Sets the threshold price.
     * @param price the new threshold price
     */
    public void setThresholdPrice(double price) {
        if (price < 0) {
            this.thresholdPrice = 0;
        } else {
            thresholdPrice = price;
        }
    }

    /**
     * Sets the threshold distance.
     * @param dist the threshold distance
     */
    public void setDistance(int dist) {
        if (dist < 0) {
            this.distance = 0;
        } else {
            this.distance = dist;
        }
    }
    public String getUser() {
        return user;
    }
    public void setUser(String u) {
        this.user = u;
    }

    /**
     * Sets the item name.
     * @param name the item name
     */
    public void setItemName(String name) {
        if (name != null && !name.trim().equals("") && !name.equals(" ")) {
            this.itemName = name;
        }
    }


    @Override
    public String toString() {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        return String.format("Item Name%15s\nUp To%21s\nUp"
               + " To %d Miles Away", itemName,
                fmt.format(thresholdPrice), distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Interest interest = (Interest) o;

        return itemName.equalsIgnoreCase(interest.itemName);
    }

    @Override
    public int hashCode() {
        return itemName.hashCode();
    }
}
