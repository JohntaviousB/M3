package com.example.johntavious.shoppingwithfriends;


import java.text.NumberFormat;

public class Notification {
    private String message;
    private String user, location, item;
    private double price;
    private Double latitude = null, longitude = null;
    public Notification(String user, String location, String item, double price) {
        this.user = user;
        this.location = location;
        this.item = item;
        this.price = price;
        createMessage(user, location, item, price);
    }
    public Notification(String user, String item, double price, double lat, double lon) {
        this.user = user;
        this.item = item;
        this.price = price;
        this.latitude = lat;
        this.longitude = lon;
        createMessage(user, item, price, lat, lon);
    }
    private void createMessage(String user, String location, String item, double price) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        this.message = user + " found an item you were interested in!\n" +
                "A(n) " + item + " at " + location + "\n For only " + fmt.format(price) + "!";
    }
    private void createMessage(String user, String item, double price, double lat, double lon) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        this.message = user + " found an item you were interested in!\n" +
                "A(n) " + item + " at " + lat +", " + lon + "\n For only "
                + fmt.format(price) + "!";
    }
    public String getLocation() {
        return location;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    @Override
    public String toString() {
        return message;
    }
}
