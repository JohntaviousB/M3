package com.example.johntavious.shoppingwithfriends;

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Clay on 3/3/2015.
 */
public class SQLHandler extends SQLiteOpenHelper {
    // Constants used in construction of database
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "ShopWFriends.db";

    // Constants used in construction of main table
    public static final String TABLE_MAIN = "Users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_RATINGS = "ratings";

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
    public static final String COLUMN_SALE_USER = "sale_user"; // user who posted sale
    public static final String COLUMN_SALE_ITEM = "sale_item";
    public static final String COLUMN_SALE_PRICE = "sale_price";
    public static final String COLUMN_SALE_LOCATION = "sale_location";

    // Constants used in construction of Notifications table
    public static final String TABLE_NOTIFICATIONS = "Notifications";
    public static final String COLUMN_NOTIFICATION_ID = "notification_id";
    public static final String COLUMN_NOTIFICATION_USER = "notification_user";
    public static final String COLUMN_NOTIFICATION_SALE = "notification_sale";

    public SQLHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MAIN = "CREATE TABLE " + TABLE_MAIN + "(" + COLUMN_ID +
                " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," +
                COLUMN_EMAIL + " TEXT," + COLUMN_PASSWORD + " TEXT" + ")";
        String CREATE_TABLE_FRIENDS = "CREATE TABLE " + TABLE_FRIENDS + "(" + COLUMN_FRIEND_LIST_ID +
                " INTEGER PRIMARY KEY," + COLUMN_USER_NAME + " TEXT," + COLUMN_FRIEND_NAME + " TEXT" + ")";
        String CREATE_TABLE_INTERESTS = "CREATE TABLE " + TABLE_INTERESTS + "(" + COLUMN_INTEREST_ID +
                " INTEGER PRIMARY KEY," + COLUMN_INTEREST_USER_NAME + " TEXT, " + COLUMN_ITEM_NAME + " TEXT," +
                COLUMN_THRESHOLD_PRICE + " REAL, " + COLUMN_DISTANCE + " INTEGER" + ")";
        String CREATE_TABLE_SALES = "CREATE TABLE " + TABLE_SALES + "(" + COLUMN_SALE_ID +
                " INTEGER PRIMARY KEY," + COLUMN_SALE_USER + " TEXT, " + COLUMN_SALE_ITEM + " TEXT, " + COLUMN_SALE_PRICE + " REAL, " +
                 COLUMN_SALE_LOCATION + " TEXT" + ")";
        String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE " + TABLE_NOTIFICATIONS + "(" + COLUMN_NOTIFICATION_ID +
                " INTEGER PRIMARY KEY," + COLUMN_NOTIFICATION_USER + " TEXT, " + COLUMN_NOTIFICATION_SALE + " INTEGER, " + ")";

        db.execSQL(CREATE_TABLE_MAIN);
        db.execSQL(CREATE_TABLE_FRIENDS);
        db.execSQL(CREATE_TABLE_INTERESTS);
        db.execSQL(CREATE_TABLE_SALES);
        db.execSQL(CREATE_TABLE_NOTIFICATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERESTS);
        onCreate(db);
    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MAIN, null, values);
        db.close();
    }

    public void addFriend(User user, User friend) {
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
    }

    public void addInterest(User user, Interest interest) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_INTEREST_USER_NAME, user.getName());
        values.put(COLUMN_ITEM_NAME, interest.getItemName());
        values.put(COLUMN_THRESHOLD_PRICE, interest.getThresholdPrice());
        values.put(COLUMN_DISTANCE, interest.getDistance());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_INTERESTS, null, values);
        db.close();
    }

    public void addSale(Sale sale) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SALE_ITEM,   sale.getItem());
        values.put(COLUMN_SALE_PRICE, sale.getPrice());
        values.put(COLUMN_SALE_LOCATION, sale.getLocation());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_SALES, null, values);
        db.close();
    }

    public User getUser(String email) {
        String query = "SELECT * FROM " + TABLE_MAIN + " WHERE " + COLUMN_EMAIL +
                " = \"" + email + "\"";
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
        } else {
            user = null;
        }

        db.close();
        return user;
    }

    public User getUserByName(String name) {
        String query = "SELECT * FROM " + TABLE_MAIN + " WHERE " + COLUMN_NAME +
                " = \"" + name + "\"";
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
        } else {
            user = null;
        }

        db.close();
        return user;
    }

    public Sale getSale(String userName) {
        String query = "SELECT * FROM " + TABLE_SALES + " WHERE " + COLUMN_SALE_USER +
                " = \"" + userName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Sale sale = new Sale();

        if (cursor.moveToFirst()) {
            sale.setId(Integer.parseInt(cursor.getString(0)));
            sale.setUserName(cursor.getString(1));
            sale.setItem(cursor.getString(2));
            sale.setPrice(Double.parseDouble(cursor.getString(3)));
            sale.setLocation(cursor.getString(4));
            cursor.close();
        } else {
            sale = null;
        }

        db.close();
        return sale;
    }

    public void includeInterests(User user, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_INTERESTS + " WHERE " + COLUMN_INTEREST_USER_NAME +
                " = \"" + user.getName() + "\"";
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                Interest interest = new Interest();
                interest.setId(Integer.parseInt(cursor.getString(0)));
                interest.setItemName(cursor.getString(2));
                interest.setThresholdPrice(Double.parseDouble(cursor.getString(3)));
                interest.setDistance(Integer.parseInt(cursor.getString(4)));
                user.registerInterest(interest);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private static void includeFriends(User user, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_FRIENDS + " WHERE " + COLUMN_USER_NAME +
                " = \"" + user.getName() + "\"";
        Cursor cursor = db.rawQuery(query, null);
        String name = " ";

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

    // Methods don't belong here
    public boolean emailValid(String email) {
        String query = "SELECT * FROM " + TABLE_MAIN + " WHERE " + COLUMN_EMAIL +
                " = \"" + email + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return false;
        } else {
            return email != null && email.contains("@") && email.contains(".");
        }
    }

    public boolean isEmailValid(String email) {
        String query = "SELECT * FROM " + TABLE_MAIN + " WHERE " + COLUMN_EMAIL +
                " = \"" + email + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidUsername(String name) {
        if (name != null && !name.contains(" ") && !name.contains("@") && name.length() > 2) {
            String query = "SELECT * FROM " + TABLE_MAIN + " WHERE " + COLUMN_NAME +
                    " = \"" + name + "\"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                cursor.close();
                return false;  //return false b/c the name is taken
            } else {
                return true;  //we've searched all names and no one is using it
            }
        }
        return false; //will return false here if n contains one of the "bad" characters or is null

    }

    public void unfriend(User user, User friend) {
/*        String query = "DELETE FROM " + TABLE_FRIENDS + " WHERE " + COLUMN_USER_NAME +
                " = \"" + user.getName() + " AND " + COLUMN_FRIEND_NAME + " = \"" + friend.getName()
                + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery(query, null);   */
//        db.delete(TABLE_FRIENDS, COLUMN_USER_NAME + " = \"" + user.getName() + " AND " +
//                COLUMN_FRIEND_NAME + " = \"" + friend.getName() + "\"", null);

    }

    public void getFriends(User user) {
        String query = "SELECT * FROM " + TABLE_FRIENDS + " WHERE " + COLUMN_USER_NAME +
                " = \"" + user.getName() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String name = " ";

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

    public void getInterests(User user) {
        String query = "SELECT * FROM " + TABLE_INTERESTS + " WHERE " + COLUMN_INTEREST_USER_NAME +
                " = \"" + user.getName() + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                Interest interest = new Interest();
                interest.setId(Integer.parseInt(cursor.getString(0)));
                interest.setItemName(cursor.getString(2));
                interest.setThresholdPrice(Double.parseDouble(cursor.getString(3)));
                interest.setDistance(Integer.parseInt(cursor.getString(4)));
                user.registerInterest(interest);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    public ArrayList<User> syncRegisteredUsers() {
        ArrayList<User> registeredUsers = new ArrayList<User>();
        String query = "SELECT * FROM " + TABLE_MAIN + "\"";
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
