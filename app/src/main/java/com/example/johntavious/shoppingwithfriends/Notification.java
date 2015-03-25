package com.example.johntavious.shoppingwithfriends;
/**
 * Represents a notification a User will receive
 * Notification will either have a latitude/longitude
 * or an explicit String location but not both
 */
import java.text.NumberFormat;

public final class Notification {
    private String message;
    private String user;
    private String location;
    private String item;
    private double price;
    private Double latitude = null;
    private Double longitude = null;

    /**
     * Constructor to initialize a Notification when lat/lng was
     * unable to be retrieved.
     * @param user the username of the User who posted the sale
     * @param location the location of the sale
     * @param item the name of the item
     * @param price the price of the item
     */
    public Notification(String user, String location,
                        String item, double price) {
        this.user = user;
        this.location = location;
        this.item = item;
        this.price = price;
        createMessage(user, location, item, price);
    }

    /**
     * Constructor to initialize a Notification when the device's lat/lng
     * was successfully retrieved.
     * @param user the name of the User who posted the sale
     * @param item the name of the item
     * @param price the price of the item
     * @param lat the latitude of the sale
     * @param lon the longitude of the sale
     */
    public Notification(String user, String item, double price,
                        double lat, double lon) {
        this.user = user;
        this.item = item;
        this.price = price;
        this.latitude = lat;
        this.longitude = lon;
        createMessage(user, item, price, lat, lon);
    }

    /**
     * Creates a message that uses a location with no known lat/lng
     * @param user the name of the User who posted the sale.
     * @param location the location of the sale
     * @param item the name of the item
     * @param price the price of the item
     */
    private void createMessage(String user, String location,
                               String item, double price) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        this.message = user + " found an item you were interested in!\n"
                + "A(n) " + item + " at " + location + "\n For only "
                + fmt.format(price) + "!";
    }

    /**
     * Creates a message that uses the associated lat/lon of the Sale
     * @param user the name of the User who posted the sale.
     * @param item the name of the item
     * @param price the price of the item
     * @param lat the latitude of the sale
     * @param lon the longitude of the sale
     */
    private void createMessage(String user, String item,
                               double price, double lat, double lon) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        this.message = user + " found an item you were interested in!\n"
                + "A(n) " + item + " at " + lat + ", " + lon + "\n For only "
                + fmt.format(price) + "!";
    }

    /**
     * Gets the String location if no latitude/longitude was supplied.
     * @return the location
     */
    public String getLocation() {
        return location;
    }
    /**
     * Gets the latitude of the notification.
     * @return the latitude of the associated sale
     */
    public double getLatitude() {
        return latitude;
    }
    /**
     * Gets the longitude of the notification.
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }
    @Override
    public String toString() {
        return message;
    }
}
