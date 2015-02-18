package com.example.johntavious.shoppingwithfriends;

import android.os.Parcelable;
import android.os.Parcel;
import java.util.ArrayList;

/**
 * Created by Johntavious on 2/8/2015.
 */
public class User implements Parcelable {
    private String name, email, password;
    private ArrayList<User> friends;

    protected User(String n, String e, String p) {
        this.name = n;
        this.email = e;
        this.password = p;
        friends = new ArrayList<User>();

    }

 //   public User(Parcel in) {
 //       readFromParcel(in);
 //   }

    // Parcelable allows us to pass User objects between Activities
    // Parcelable methods
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(email);
        out.writeString(password);
        out.writeTypedList(friends);
    }

    public void readFromParcel(Parcel in) {
        name = in.readString();
        email = in.readString();
        password = in.readString();
        in.readTypedList(friends, User.CREATOR);
    }

        public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

        public User(Parcel in) {
            name = in.readString();
            email = in.readString();
            password = in.readString();
            in.readTypedList(friends, User.CREATOR);

        }






        protected void addFriend(User friend) {
        friends.add(friend);
    }

    /**
     * Changes the User's name
     * @param n The new name
     */
    protected void setName(String n) {
        this.name = n;
    }

    /**
     * Changes the User's email
     * @param e The new email address
     */
    protected void setEmail(String e) {
        this.email = e;
    }

    /**
     * Changes the User's password
     * @param p The new password
     */
    protected void setPassword(String p) {
        this.password = p;
    }

    /**
     * Returns the User's name
     * @return the User's name
     */
    protected String getName() {
        return this.name;
    }

    /**
     * Returns the User's email
     * @return the User's email
     */
    protected String getEmail() {
        return this.email;
    }

    /**
     * Returns the User's password
     * @return the User's password
     */
    protected String getPassword() {
        return this.password;
    }
    @Override
    public String toString() {
        return name + " " + email + " ";
    }
}
