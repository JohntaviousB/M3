package com.example.johntavious.shoppingwithfriends;


import java.text.NumberFormat;

public class Notification {
    private String message;
    private String user, location, item;
    private double price;
    public Notification(String user, String location, String item, double price) {
        this.user = user;
        this.location = location;
        this.item = item;
        this.price = price;
        createMessage(user, location, item, price);
    }
    private void createMessage(String user, String location, String item, double price) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        this.message = user + " found an item you were interested in!\n" +
                "A(n) " + item + " at " + location + "\n For only " + fmt.format(price) + "!";
    }
    @Override
    public String toString() {
        return message;
    }
}
