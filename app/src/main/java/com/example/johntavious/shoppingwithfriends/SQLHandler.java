package com.example.johntavious.shoppingwithfriends;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the database used by the application.
 */
public final class SQLHandler extends SQLiteOpenHelper {
    // Constants used in construction of database
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "ShopWFriends.db";

    // Constants used in construction of main table
    public static final String TABLE_MAIN = "Users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    // Constants used in construction of friends table
    public static final String TABLE_FRIENDS = "Friends";
    public static final String COLUMN_FRIEND_LIST_ID = "friend_list_id";
    public static final String COLUMN_USER_NAME = "user_id";
    public static final String COLUMN_FRIEND_NAME = "friend_id";

    // Constants used in construction of Interests table
    public static final String TABLE_INTERESTS = "Interests";
    public static final String COLUMN_INTEREST_ID = "interest_id";
    public static final String COLUMN_INTEREST_USER_NAME = "interest_user_name";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_THRESHOLD_PRICE = "threshold_price";
    public static final String COLUMN_DISTANCE = "distance";

    // Constants used in construction of Sales table
    public static final String TABLE_SALES = "Sales";
    public static final String COLUMN_SALE_ID = "sale_id";
    public static final String COLUMN_SALE_USER = "sale_user";
    public static final String COLUMN_SALE_ITEM = "sale_item";
    public static final String COLUMN_SALE_PRICE = "sale_price";
    public static final String COLUMN_SALE_LOCATION = "sale_location";
    public static final String COLUMN_SALE_LATITUDE = "sale_latitude";
    public static final String COLUMN_SALE_LONGITUDE = "sale_longitude";

    public static final String TABLE_NOTIFICATIONS = "Notifications";

    /**
     * Constructor to initialize our database helper.
     * @param context the context of the database
     */
    public SQLHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableMain = "CREATE TABLE " + TABLE_MAIN
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NAME + " TEXT," + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT)";
        String createTableFriends = "CREATE TABLE " + TABLE_FRIENDS + "("
                + COLUMN_FRIEND_LIST_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_FRIEND_NAME + " TEXT)";
        String createTableInterests = "CREATE TABLE " + TABLE_INTERESTS + "("
                + COLUMN_INTEREST_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_INTEREST_USER_NAME + " TEXT, "
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_THRESHOLD_PRICE + " REAL, "
                + COLUMN_DISTANCE + " INTEGER)";
        String createTableSales = "CREATE TABLE " + TABLE_SALES + "("
                + COLUMN_SALE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_SALE_USER + " TEXT, "
                + COLUMN_SALE_ITEM + " TEXT, "
                + COLUMN_SALE_PRICE + " REAL, "
                + COLUMN_SALE_LOCATION + " TEXT, "
                + COLUMN_SALE_LATITUDE + " REAL, "
                + COLUMN_SALE_LONGITUDE + " REAL)";

        db.execSQL(createTableMain);
        db.execSQL(createTableFriends);
        db.execSQL(createTableInterests);
        db.execSQL(createTableSales);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        onCreate(db);
    }

    /**
     * Adds a user to the database.
     * @param user the User to add to the database
     */
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MAIN, null, values);
        db.close();
    }

    /**
     * Creates a friendship in the database between two users.
     * @param user one member of the friendship
     * @param friend the other member of the friendship
     * @return true if the creation was successful, false otherwise
     */
    public boolean addFriend(User user, User friend) {
        List<String> friendsList = user.getFriends();
        if (!friendsList.contains(friend.getName())
                && !user.equals(friend)) {
            friendsList.add(friend.getName());
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_NAME, user.getName());
            values.put(COLUMN_FRIEND_NAME, friend.getName());

            ContentValues values2 = new ContentValues();
            values2.put(COLUMN_USER_NAME, friend.getName());
            values2.put(COLUMN_FRIEND_NAME, user.getName());

            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_FRIENDS, null, values);
            db.insert(TABLE_FRIENDS, null, values2);
            db.close();
            return true;
        }
        return false;
    }

    /**
     * Creates an interest in the database associated with a given User.
     * @param user the User whose interest is to be added
     * @param interest the interest to add
     */
    public void addInterest(User user, Interest interest) {
        user.registerInterest(interest);
        ContentValues values = new ContentValues();
        values.put(COLUMN_INTEREST_USER_NAME, user.getName());
        values.put(COLUMN_ITEM_NAME, interest.getItemName());
        values.put(COLUMN_THRESHOLD_PRICE, interest.getThresholdPrice());
        values.put(COLUMN_DISTANCE, interest.getDistance());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_INTERESTS, null, values);
        db.close();
    }

    /**
     * Adds a Sale into the database.
     * @param sale the sale to be added
     */
    public void addSale(Sale sale) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SALE_USER, sale.getUserName());
        values.put(COLUMN_SALE_ITEM,   sale.getItem());
        values.put(COLUMN_SALE_PRICE, sale.getPrice());
        values.put(COLUMN_SALE_LOCATION, sale.getLocation());
        values.put(COLUMN_SALE_LATITUDE, sale.getLatitude());
        values.put(COLUMN_SALE_LONGITUDE, sale.getLongitude());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_SALES, null, values);
        db.close();
    }

    /**
     * Retrieves a user from the database along with
     * all of his interests, friends, and notifications.
     * @param email the email of the User to retrieve
     * @return the User
     */
    public User getUser(String email) {
        String query = "SELECT * FROM " + TABLE_MAIN
                + " WHERE " + COLUMN_EMAIL
                + " = \"" + email + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user = new User();

        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            cursor.close();
            includeFriends(user, db);
            includeInterests(user, db);
            includeNotifications(user, db);
        } else {
            user = null;
        }

        db.close();
        return user;
    }

    /**
     * Retrieves a User from the database with the given Username
     * along with his friends, notifications, and interests.
     * @param name the username of the user to retrieve
     * @return the user with the given name
     */
    public User getUserByName(String name) {
        String query = "SELECT * FROM " + TABLE_MAIN
                + " WHERE " + COLUMN_NAME + " = \"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user = new User();
        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(0)));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            cursor.close();
            includeFriends(user, db);
            includeInterests(user, db);
            includeNotifications(user, db);
        } else {
            user = null;
        }
        db.close();
        return user;
    }
    /**
     * Helper method to insert all of a User's interests from the database
     * into the User's List of interests.
     * @param user the user whose interests are to be retrieved
     * @param db the database to retrieve the interests from
     */
    private void includeInterests(User user, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_INTERESTS
                + " WHERE " + COLUMN_INTEREST_USER_NAME
                + " = \"" + user.getName() + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Interest interest = new Interest();
                interest.setId(Integer.parseInt(cursor.getString(0)));
                interest.setItemName(cursor.getString(2));
                interest.setThresholdPrice(
                        Double.parseDouble(cursor.getString(3)));
                interest.setDistance(Integer.parseInt(cursor.getString(4)));
                user.registerInterest(interest);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    /**
     * Helper method to insert all of a User's friends from the
     * database into the user's List of friends.
     * @param user the user whose friends are to be retrieved
     * @param db the database to retrieve the friends from
     */
    private  void includeFriends(User user, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_FRIENDS
                + " WHERE " + COLUMN_USER_NAME
                + " = \"" + user.getName() + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String name;
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(2);
                boolean areFriends = false;
                List<String> friends = user.getFriends();
                for (String element : friends) {
                    if (element.equalsIgnoreCase(name)) {
                        areFriends = true;
                    }
                }
                if (!areFriends) {
                    user.addFriend(name);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    /**
     * Performs a join on the Sale and Friends table to help
     * retrieve notifications for a User.
     * @param user the user whose notifications are to be retrieved
     * @param db the database to retrieve the notifications from
     */
    private void includeNotifications(User user, SQLiteDatabase db) {
        String query = "SELECT "
                + COLUMN_SALE_USER + ", "
                + COLUMN_SALE_ITEM
                + ", " + COLUMN_SALE_PRICE
                + ", " + COLUMN_SALE_LOCATION
                + ", " + COLUMN_SALE_LATITUDE
                + ", " + COLUMN_SALE_LONGITUDE
                + " FROM "
                + TABLE_SALES + " s, "
                + TABLE_FRIENDS + " f "
                + "WHERE s." + COLUMN_SALE_USER
                + " = " + "f." + COLUMN_FRIEND_NAME
                + " AND f." + COLUMN_USER_NAME
                + " = \"" + user.getName() + "\"";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String friend = cursor.getString(0);
                String itemName = cursor.getString(1);
                double price = Double.parseDouble(cursor.getString(2));
                String location = cursor.getString(3);
                double lat = 0;
                double lon = 0;
                if (cursor.getString(4) != null) {
                    lat = Double.parseDouble(cursor.getString(4));
                }
                if (cursor.getString(5) != null) {
                    lon = Double.parseDouble((cursor.getString(5)));
                }
                List<Interest> interests = user.getInterests();
                for (Interest interest : interests) {
                    if (interest.getItemName().equalsIgnoreCase(itemName)) {
                        if (interest.getThresholdPrice() >= price) {
                            if (location != null) {
                                //unable to retrieve lat/lng
                                user.addNotification(
                                        new Notification(friend, location,
                                                itemName, price));
                            } else {
                                //able to retrieve lat/lng
                                user.addNotification(
                                        new Notification(friend, itemName,
                                                price, lat, lon));
                            }
                        }
                    }
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
    /**
     * Checks first to see if the db contains the email, and then if the email
     * has a valid format. Purpose is to make sure the
     * email is not already taken.
     * @param email the email to check
     * @return true if the email is not taken and formatted properly
     */
    public boolean emailValid(String email) {
        String query = "SELECT * FROM "
                + TABLE_MAIN
                + " WHERE " + COLUMN_EMAIL
                + " = \"" + email + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return false;
        } else {
            return email != null && email.contains("@") && email.contains(".");
        }
    }

    /**
     * Checks to see if the db contains the email.
     * @param email the email to be checked
     * @return true if the db contains the email, false otherwise
     */
    public boolean isEmailValid(String email) {
        String query = "SELECT * FROM "
                + TABLE_MAIN + " WHERE " + COLUMN_EMAIL
                + " = \"" + email + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks to see if a username is already taken in the database.
     * @param name the username to check
     * @return true if the name is available, false otherwise
     */
    public boolean isValidUsername(String name) {
        if (name != null && !name.contains(" ")
                && !name.contains("@") && name.length() > 2) {
            String query = "SELECT * FROM "
                    + TABLE_MAIN + " WHERE " + COLUMN_NAME
                    + " = \"" + name + "\"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                cursor.close();
                return false;  //return false b/c the name is taken
            } else {
                return true;  //we've searched all names and no one is using it
            }
        }
        return false; //will return false here if n contains
                      // one of the "bad" characters or is null

    }

    /**
     * Terminates a friendship in the database. Note friendship is mutual.
     * @param user one member of the friendship
     * @param friend the other member of the friendship
     */
    public void unfriend(User user, User friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRIENDS, COLUMN_USER_NAME + " = ? AND "
                + COLUMN_FRIEND_NAME + " = ?", new String[]{user.getName(),
                                                        friend.getName()});
        db.delete(TABLE_FRIENDS, COLUMN_USER_NAME + " = ? AND "
                + COLUMN_FRIEND_NAME + " = ?", new String[]{friend.getName(),
                                                        user.getName()});
    }

    /**
     * Generates a List of all Users in the database.
     * @return a List of all Users in the database
     */
    public List<User> syncRegisteredUsers() {
        List<User> registeredUsers = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MAIN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                registeredUsers.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return registeredUsers;
    }
}
