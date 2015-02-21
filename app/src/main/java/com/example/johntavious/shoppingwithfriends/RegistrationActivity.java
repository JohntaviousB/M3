package com.example.johntavious.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * Represents the registration activity of the application
 */

public class RegistrationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    /**
     * Cancels the Registration Activity and returns User
     * to the opening page
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
    /**TODO: also check Username uniqueness, password filled out, and fix bug allowing empty names,
     * , as well as name that contain spaces"**/
    public void onDoneClick(View view) {
        String name = ((EditText)findViewById(R.id.name_field)).getText().toString();
        String email = ((EditText)findViewById(R.id.email_field)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_field)).getText().toString();
        if (name.trim() == "") {
            EditText nameView = (EditText)findViewById(R.id.name_field);
            nameView.setError("This field is invalid or taken");
            nameView.requestFocus();
        } else {
            if (LoginActivity.emailValid(email)) {
                User user = new User(name, email, password);
                LoginActivity.addUser(user);
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.putExtra("user", user.getName());
                startActivity(intent);
            } else {
                EditText emailView = (EditText) findViewById(R.id.email_field);
                emailView.setError(getString(R.string.email_taken));
                emailView.requestFocus();
            }
        }
    }
}
