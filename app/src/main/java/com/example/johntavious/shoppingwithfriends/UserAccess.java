package com.example.johntavious.shoppingwithfriends;

/**
 * Created by Clay on 3/7/2015.
 * This class handles user access to the application.
 * Registers new users and verifies login credentials
 * of existing users.
 */
public class UserAccess {
    private static DataController dc;

    public UserAccess() {
        dc = new DataController();
    }

    public boolean validateCredentials(String email, String password) {
        boolean credentialsValid = false;
        User user = dc.getUser(email);
        if (null != user) {
            if (user.getPassword().equals(password)) {
                credentialsValid = true;
            }
        }
        return credentialsValid;
    }

    public boolean registerUser(String name, String email, String password) {
        boolean registrationSuccessful = false;
        if (isNameValid(name) && isEmailValid(email) && isPasswordValid(password)){
            registrationSuccessful = true;
            dc.addUser(name, email, password);
        }
        return registrationSuccessful;
    }

    // todo Complete method
    private boolean isNameValid(String name) {
        return true;
    }

    // todo Complete method
    private boolean isEmailValid(String email) {
        return true;
    }

    // todo Complete method
    private boolean isPasswordValid(String password) {
        return true;
    }

}
