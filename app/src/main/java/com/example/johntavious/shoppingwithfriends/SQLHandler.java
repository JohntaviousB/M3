package com.example.johntavious.shoppingwithfriends;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the database used by the application.
 */
final class SQLHandler extends SQLiteOpenHelper {
    // Constants used in construction of database
    private static final int DATABASE_VERSION = 23;
    private static final String DATABASE_NAME = "ShopWFriends.db";

    // Constants used in construction of main table
    private static final String TABLE_MAIN = "Users";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_PICTURE = "picture";
    private static final String COLUMN_NUMBER = "phone_number";
    private static final String COLUMN_NOTIFICATION_METHOD = "notification_method";

    // Constants used in construction of friends table
    private static final String TABLE_FRIENDS = "Friends";
    private static final String COLUMN_USER_NAME = "user_id";
    private static final String COLUMN_FRIEND_NAME = "friend_id";

    // Constants used in construction of Interests table
    private static final String TABLE_INTERESTS = "Interests";
    private static final String COLUMN_INTEREST_USER_NAME = "interest_user_name";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_THRESHOLD_PRICE = "threshold_price";
    private static final String COLUMN_DISTANCE = "distance";

    // Constants used in construction of Sales table
    private static final String TABLE_SALES = "Sales";
    private static final String COLUMN_SALE_ID = "_id";
    private static final String COLUMN_SALE_USER = "sale_user";
    private static final String COLUMN_SALE_ITEM = "sale_item";
    private static final String COLUMN_SALE_PRICE = "sale_price";
    private static final String COLUMN_SALE_LOCATION = "sale_location";
    private static final String COLUMN_SALE_LATITUDE = "sale_latitude";
    private static final String COLUMN_SALE_LONGITUDE = "sale_longitude";
    private static final String COLUMN_SALE_IMAGE = "image_uri";

    // Constants used in construction of Notifications table
    private static final String TABLE_NOTIFICATIONS = "Notifications";
    private static final String COLUMN_NOTIFICATION_USER = "user";
    private static final String COLUMN_NOTIFICATION_SALE = "sale_id";
    private static final String COLUMN_NOTIFICATION_IMAGE = "image_uri";

    //CONSTANTS USED IN CONSTRUCTION OF ADMIN TABLE
    private static final String TABLE_ADMIN = "Admins";
    private static final String ADMIN_NAME = "name";
    private static final String ADMIN_PASSWORD = "password";

    private static final String ADMIN_ONE = "Barkley";
    private static final String ADMIN_PW = "admin";

    private Context context;
    private static int numOfSales; //used to help generate surrogate keys for Sales
    private int numNotifications = 0;
    /**
     * Constructor to initialize our database helper.
     * @param context the context of the database
     */
    public SQLHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableMain = "CREATE TABLE " + TABLE_MAIN + "("
                + COLUMN_NAME + " TEXT PRIMARY KEY,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_LATITUDE + " REAL,"
                + COLUMN_LONGITUDE + " REAL, "
                + COLUMN_PICTURE + " TEXT, "
                + COLUMN_NUMBER + " TEXT, "
                + COLUMN_NOTIFICATION_METHOD + " TEXT)";
        String createTableFriends = "CREATE TABLE " + TABLE_FRIENDS + "("
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_FRIEND_NAME + " TEXT,"
                + "PRIMARY KEY (" + COLUMN_USER_NAME + ", " + COLUMN_FRIEND_NAME + ")"
                + ", FOREIGN KEY (" + COLUMN_USER_NAME + ") REFERENCES " + TABLE_MAIN + "(" + COLUMN_NAME + ")" +
                " ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY (" + COLUMN_FRIEND_NAME + ") REFERENCES " + TABLE_MAIN + "(" + COLUMN_NAME + ")"
                + " ON DELETE CASCADE ON UPDATE CASCADE)";
        String createTableInterests = "CREATE TABLE " + TABLE_INTERESTS + "("
                + COLUMN_INTEREST_USER_NAME + " TEXT, "
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_THRESHOLD_PRICE + " REAL, "
                + COLUMN_DISTANCE + " INTEGER,"
                + "PRIMARY KEY (" + COLUMN_INTEREST_USER_NAME + ", " + COLUMN_ITEM_NAME + ")," +
                " FOREIGN KEY (" + COLUMN_INTEREST_USER_NAME + ") REFERENCES " + TABLE_MAIN + "(" + COLUMN_NAME + ")" +
                " ON UPDATE CASCADE ON DELETE CASCADE)";
        String createTableSales = "CREATE TABLE " + TABLE_SALES + "("
                + COLUMN_SALE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_SALE_USER + " TEXT, "
                + COLUMN_SALE_ITEM + " TEXT, "
                + COLUMN_SALE_PRICE + " REAL, "
                + COLUMN_SALE_LOCATION + " TEXT, "
                + COLUMN_SALE_LATITUDE + " REAL, "
                + COLUMN_SALE_LONGITUDE + " REAL, "
                + COLUMN_SALE_IMAGE + " TEXT, "
                + "FOREIGN KEY (" + COLUMN_SALE_USER + ") "
                + "REFERENCES " + TABLE_MAIN + "(" + COLUMN_NAME + ") ON UPDATE CASCADE ON DELETE CASCADE)";
        String createTableNotifications = "CREATE TABLE " + TABLE_NOTIFICATIONS + "("
                + COLUMN_NOTIFICATION_USER + " TEXT,"
                + COLUMN_NOTIFICATION_SALE + " INTEGER, "
                + COLUMN_NOTIFICATION_IMAGE + " TEXT, "
                + "PRIMARY KEY (" + COLUMN_NOTIFICATION_USER + ", "
                + COLUMN_NOTIFICATION_SALE + "), "
                + "FOREIGN KEY (" + COLUMN_NOTIFICATION_SALE + ") "
                + "REFERENCES " + TABLE_SALES + "(" + COLUMN_SALE_ID + ")"
                + " ON UPDATE CASCADE ON DELETE CASCADE, FOREIGN KEY (" + COLUMN_NOTIFICATION_USER + ")"
                + " REFERENCES " + TABLE_MAIN + "(" + COLUMN_NAME + ") ON UPDATE CASCADE ON DELETE CASCADE)";
        String createTableAdmin = "CREATE TABLE " + TABLE_ADMIN + "(" + ADMIN_NAME + " TEXT PRIMARY KEY, "
                + ADMIN_PASSWORD + " TEXT)";

        db.execSQL(createTableMain);
        db.execSQL(createTableFriends);
        db.execSQL(createTableInterests);
        db.execSQL(createTableSales);
        db.execSQL(createTableNotifications);
        db.execSQL(createTableAdmin);

        ContentValues values1 = new ContentValues();
        values1.put(ADMIN_NAME, ADMIN_ONE);
        values1.put(ADMIN_PASSWORD, ADMIN_PW);
        db.insert(TABLE_ADMIN, null, values1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERESTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
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
        values.put(COLUMN_LATITUDE, user.getLatitude());
        values.put(COLUMN_LONGITUDE, user.getLongitude());
        values.put(COLUMN_PICTURE, user.getProfilePic());
        values.put(COLUMN_NUMBER, user.getPhoneNumber());
        values.put(COLUMN_NOTIFICATION_METHOD, user.getNotificationMethod().toString());

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
            user.addFriend(friend.getName());
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
     * @param interest the interest to add
     */
    public void addInterest(Interest interest) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_INTEREST_USER_NAME, interest.getUser());
        values.put(COLUMN_ITEM_NAME, interest.getItemName());
        values.put(COLUMN_THRESHOLD_PRICE, interest.getThresholdPrice());
        values.put(COLUMN_DISTANCE, interest.getDistance());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_INTERESTS, null, values);
        db.close();
    }

    /**
     * Adds a Sale into the database and generates appropriate notifications.
     * @param sale the sale to be added
     */
    public void addSale(Sale sale) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (numOfSales == 0) {
            String query = "SELECT COUNT(*) FROM " + TABLE_SALES;
            Cursor cursor1 = db.rawQuery(query, null);
            if (cursor1.moveToFirst()) {
                numOfSales = Integer.parseInt(cursor1.getString(0));
            }
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_SALE_ID, ++numOfSales);
        values.put(COLUMN_SALE_USER, sale.getUserName());
        values.put(COLUMN_SALE_ITEM,   sale.getItem());
        values.put(COLUMN_SALE_PRICE, sale.getPrice());
        values.put(COLUMN_SALE_LOCATION, sale.getLocation());
        values.put(COLUMN_SALE_LATITUDE, sale.getLatitude());
        values.put(COLUMN_SALE_LONGITUDE, sale.getLongitude());
        values.put(COLUMN_SALE_IMAGE, sale.getImageURI());
        db.insert(TABLE_SALES, null, values);
        sale.setId(numOfSales);
        Log.d("SALE_INSERTION:", "Sale Id: " + sale.getId() + " User: " + sale.getUserName() + " Item: " + sale.getItem());


        //FRIENDS WITH NO LOCATION STORED NOTIFIED AUTOMATICALLY
        String friendsToNotify = "SELECT " + COLUMN_FRIEND_NAME
                + " FROM " + TABLE_FRIENDS + " f, "
                + TABLE_INTERESTS + " i, "
                + TABLE_MAIN + " u "
                + "WHERE f." + COLUMN_FRIEND_NAME + " = i." + COLUMN_INTEREST_USER_NAME
                + " AND i." + COLUMN_ITEM_NAME + " = '" + sale.getItem() + "'"
                + " AND i." + COLUMN_THRESHOLD_PRICE + " >= " + sale.getPrice()
                + " AND f." + COLUMN_USER_NAME + " = '" + sale.getUserName() + "'"
                + " AND u." + COLUMN_NAME + " = " + "i." + COLUMN_INTEREST_USER_NAME
                + " AND u." + COLUMN_LATITUDE + " IS NULL";
        Cursor cursor2 = db.rawQuery(friendsToNotify, null);
        if (cursor2.moveToFirst()) {
            do {
                ContentValues notificationValues = new ContentValues();
                notificationValues.put(COLUMN_NOTIFICATION_SALE, sale.getId());
                notificationValues.put(COLUMN_NOTIFICATION_USER, cursor2.getString(0));
                notificationValues.put(COLUMN_NOTIFICATION_IMAGE, sale.getImageURI());
                Log.d("Notification_INSERTION::", " Sale id: " + sale.getId() + " User: " + cursor2.getString(0));
                db.insert(TABLE_NOTIFICATIONS, null, notificationValues);
            } while (cursor2.moveToNext());
            cursor2.close();
        }
        String otherFriendsToNotify = "SELECT " + COLUMN_FRIEND_NAME
                + ", " + COLUMN_LATITUDE
                + ", " + COLUMN_LONGITUDE
                + ", " + COLUMN_DISTANCE
                + ", " + COLUMN_NUMBER
                + ", " + COLUMN_NOTIFICATION_METHOD
                + " FROM " + TABLE_FRIENDS + " f, "
                + TABLE_INTERESTS + " i, "
                + TABLE_MAIN + " u "
                + "WHERE f." + COLUMN_FRIEND_NAME + " = i." + COLUMN_INTEREST_USER_NAME
                + " AND i." + COLUMN_ITEM_NAME + " = '" + sale.getItem() + "'"
                + " AND i." + COLUMN_THRESHOLD_PRICE + " >= " + sale.getPrice()
                + " AND f." + COLUMN_USER_NAME + " = '" + sale.getUserName() + "'"
                + " AND u." + COLUMN_NAME + " = " + "i." + COLUMN_INTEREST_USER_NAME
                + " AND u." + COLUMN_LATITUDE + " IS NOT NULL"
                + " AND u." + COLUMN_LONGITUDE + " IS NOT NULL";
        Cursor newCursor = db.rawQuery(otherFriendsToNotify, null);
        if (newCursor.moveToFirst()) {
            do {
                double metersToMiles = 0.000621371;
                double lat = sale.getLatitude();
                double lon = sale.getLongitude();
                double distance = Double.parseDouble(newCursor.getString(3));
                double userLat = Double.parseDouble(newCursor.getString(1));
                double userLon = Double.parseDouble(newCursor.getString(2));
                Location saleLocation = new Location(LocationManager.GPS_PROVIDER);
                saleLocation.setLatitude(lat);
                saleLocation.setLongitude(lon);
                float[] result = new float[1];
                Location.distanceBetween(lat, lon, userLat, userLon, result);
                Log.d("ARRAY:", "distance in miles: " + (metersToMiles * result[0]));
                double distanceDifference = metersToMiles * result[0];
                if (distanceDifference <= distance) {
                    ContentValues notificationValues = new ContentValues();
                    notificationValues.put(COLUMN_NOTIFICATION_SALE, sale.getId());
                    notificationValues.put(COLUMN_NOTIFICATION_USER, newCursor.getString(0));
                    notificationValues.put(COLUMN_NOTIFICATION_IMAGE, sale.getImageURI());
                    db.insert(TABLE_NOTIFICATIONS, null, notificationValues);
                    sendNotification(sale, distanceDifference,
                            distance, newCursor.getString(0),
                            newCursor.getString(5), newCursor.getString(4));
                }
            } while (newCursor.moveToNext());
            newCursor.close();
        }
        db.close();
    }

    /**
     * Sends push notification to phone.
     * @param sale the sale found
     * @param distanceDifference the difference between the threshold
     *                           distance and the actual distance
     * @param distance the threshold distance
     * @param username the name of the User
     */
    private void sendNotification(Sale sale, double distanceDifference,
                                  double distance, String username, String method,
                                  String phoneNumber) {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        DecimalFormat fmt2 = new DecimalFormat("0.##");
        String itemname = sale.getItem();
        String threshdist = fmt2.format(distance);
        String poster = sale.getUserName();
        String dist = fmt2.format(distanceDifference);
        String price = fmt.format(sale.getPrice());
        String message = String.format("%s, you requested an item (%s) only %s miles away. \n%s found one " +
                "that's %s miles away for only %s!", username, itemname, threshdist, poster, dist, price);
        if (method.equals("PUSH")) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this.context)
                            .setSmallIcon(R.drawable.cart)
                            .setContentTitle("Your friend found a sale!")
                            .setContentText(message)
                            .setAutoCancel(true);
            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this.context, LoginActivity.class);

            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.context);
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(LoginActivity.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // mId allows you to update the notification later on.
            mNotificationManager.notify(numNotifications++, mBuilder.build());
            Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(750);
        } else if (method.equals("SMS")) {
            SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null,null);
        }
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
            user.setName(cursor.getString(0));
            user.setEmail(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            if (cursor.getString(3) != null) {
                user.setLatitude(Double.parseDouble(cursor.getString(3)));
            }
            if (cursor.getString(4) != null) {
                user.setLongitude(Double.parseDouble(cursor.getString(4)));
            }
            if (cursor.getString(5) != null) {
                user.setProfilePic(cursor.getString(5));
            }
            if (cursor.getString(6) != null) {
                user.setPhoneNumber(cursor.getString(6));
            }
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
            user.setName(cursor.getString(0));
            user.setEmail(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            if (cursor.getString(3) != null) {
                user.setLatitude(Double.parseDouble(cursor.getString(3)));
            }
            if (cursor.getString(4) != null) {
                user.setLongitude(Double.parseDouble(cursor.getString(4)));
            }
            if (cursor.getString(5) != null) {
                user.setProfilePic(cursor.getString(5));
            }
            if (cursor.getString(6) != null) {
                user.setPhoneNumber(cursor.getString(6));
            }
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
                interest.setItemName(cursor.getString(1));
                interest.setThresholdPrice(
                        Double.parseDouble(cursor.getString(2)));
                interest.setDistance(Integer.parseInt(cursor.getString(3)));
                interest.setUser(cursor.getString(0));
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
                name = cursor.getString(1);
                user.addFriend(name);
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
                + ", n." + COLUMN_NOTIFICATION_IMAGE
                + " FROM "
                + TABLE_SALES + " s, "
                + TABLE_NOTIFICATIONS + " n "
                + "WHERE s." + COLUMN_SALE_ID
                + " = " + "n." + COLUMN_NOTIFICATION_SALE
                + " AND n." + COLUMN_NOTIFICATION_USER
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
                if (location != null) {
                    //unable to retrieve lat/lng
                    Notification notification = new Notification(friend, location, itemName, price);
                    notification.setImgURI(cursor.getString(6));
                    user.addNotification(notification);
                } else {
                    //able to retrieve lat/lng
                    Notification notification = new Notification(friend, itemName, price, lat, lon);
                    notification.setImgURI(cursor.getString(6));
                    user.addNotification(notification);
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
        if (name != null && !name.contains(" ") && !name.contains("\"")
                && !name.contains("@") && !name.contains("'") && name.length() > 2) {
            String query = "SELECT * FROM "
                    + TABLE_MAIN + " WHERE " + COLUMN_NAME
                    + " = \"" + name + "\"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                cursor.close();
                return false;  //return false b/c the name is taken
            } else {
                String query2 = "SELECT * FROM "
                        + TABLE_ADMIN + " WHERE " + ADMIN_NAME
                        + " = \"" + name + "\"";
                Cursor cursor2 = db.rawQuery(query2, null);
                Log.d("query", "executing query");
                if (cursor2.moveToFirst()) {
                    Log.d("query", "inside if");
                    cursor2.close();
                    return false;
                }
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
        user.unfriend(friend);
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FRIENDS, COLUMN_USER_NAME + " = ? AND "
                + COLUMN_FRIEND_NAME + " = ?", new String[]{user.getName(),
                                                        friend.getName()});
        db.delete(TABLE_FRIENDS, COLUMN_USER_NAME + " = ? AND "
                + COLUMN_FRIEND_NAME + " = ?", new String[]{friend.getName(),
                                                        user.getName()});
        db.close();
    }
    public void deleteUser(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MAIN, COLUMN_NAME + " = ?", new String[]{u.getName()});
        db.close();
    }
    public void deleteSale(Sale s) {
        this.getWritableDatabase().delete(TABLE_SALES, COLUMN_SALE_ID + " = ?",
                new String[]{String.valueOf(s.getId())});
    }
    public void deleteInterest(Interest i) {
        this.getWritableDatabase().delete(TABLE_INTERESTS, COLUMN_INTEREST_USER_NAME + " = ? " +
                        "AND " + COLUMN_ITEM_NAME + " = ?",
                new String[]{i.getUser(), i.getItemName()});
    }
    /**
     * Updates the User's profile.
     * @param u the User to be updated.
     */
    public void updateUser(User u, String oldUsername) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, u.getName());
        values.put(COLUMN_EMAIL, u.getEmail());
        values.put(COLUMN_PASSWORD, u.getPassword());
        values.put(COLUMN_LATITUDE, u.getLatitude());
        values.put(COLUMN_LONGITUDE, u.getLongitude());
        values.put(COLUMN_PICTURE, u.getProfilePic());
        values.put(COLUMN_NUMBER, u.getPhoneNumber());

        db.update(TABLE_MAIN, values, COLUMN_NAME + " = ?", new String[]{oldUsername} );
        db.close();
    }
    /**
     * Reports how many sales f has shared with u.
     * @param u the user logged in
     * @param f the friend of u
     * @return the number of such sales.
     */
    public int getSalesShared(User u, User f) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM "
                + TABLE_NOTIFICATIONS + " n, " + TABLE_SALES + " s "
                + "WHERE n." + COLUMN_NOTIFICATION_SALE + " = s." + COLUMN_SALE_ID
                + " AND n." + COLUMN_NOTIFICATION_USER + " = '" + u.getName() + "'"
                + " AND s." + COLUMN_SALE_USER + " = \"" + f.getName() + "\"";
        Cursor cursor = db.rawQuery(query, null);
        return cursor.moveToFirst() ? Integer.parseInt(cursor.getString(0)) : 0;
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
                user.setName(cursor.getString(0));
                user.setEmail(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                registeredUsers.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return registeredUsers;
    }
    public Map<String, String> syncAdmins() {
        Map<String, String> toReturn = new HashMap<>();
        String query = "SELECT * FROM " + TABLE_ADMIN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                toReturn.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return toReturn;
    }
    public List<Sale> getSales() {
        List<Sale> sales = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_SALES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Sale sale = new Sale(cursor.getString(1),
                            cursor.getString(2),
                            Double.parseDouble(cursor.getString(3)),
                            null); // don't really care about the location
                sale.setId(Integer.parseInt(cursor.getString(0)));
                sales.add(sale);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return sales;
    }
    public List<Interest> getInterests() {
        List<Interest> interests = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_INTERESTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                interests.add(new Interest(cursor.getString(1),
                        Double.parseDouble(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)),
                        cursor.getString(0)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return interests;
    }
}
