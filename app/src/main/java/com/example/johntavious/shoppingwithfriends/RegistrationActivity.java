package com.example.johntavious.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Represents the registration activity of the application
 */

public class RegistrationActivity extends Activity {

    SQLHandler sqlHandler = new SQLHandler(this, null, null, 4);
    DataController dc = new DataController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    /**
     * Cancels the Registration Activity and returns User
     * to the opening page
     *
     * @param view the cancel button click
     */
    public void onCancelClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Attempts to create a new User once "done" is clicked
     * and log him into the system
     * @param view the done button click
     */
    /**
     * TODO: also check Username uniqueness, and fix bug allowing empty names,
     * , as well as name that contain spaces"*
     */
    public void onDoneClick(View view) {
        String name = ((EditText) findViewById(R.id.name_field)).getText().toString().trim();
        String email = ((EditText) findViewById(R.id.email_field)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.password_field)).getText().toString();
        if (!sqlHandler.isValidUsername(name)) {
            EditText nameView = (EditText) findViewById(R.id.name_field);
            nameView.setError("This name is either taken or invalid. Usernames" +
                            " cannot contain \"@\" or spaces and must be more than 2 " +
                            "characters"
            );
            nameView.requestFocus();
        } else if (!sqlHandler.isPasswordValid(password)) {
            EditText passwordView = (EditText) findViewById(R.id.password_field);
            passwordView.setError("Passwords must be at least 4 characters");
            passwordView.requestFocus();
        } else if (sqlHandler.emailValid(email)) {
            User user = new User(name, email, password);
            sqlHandler.addUser(user);
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("user", user.getEmail());
            startActivity(intent);
        } else {
            EditText emailView = (EditText) findViewById(R.id.email_field);
            emailView.setError(getString(R.string.email_taken));
            emailView.requestFocus();
        }
    }
}