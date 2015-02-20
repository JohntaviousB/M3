package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class RegistrationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    /**TODO: also check Username uniqueness, password filled out, and fix bug allowing empty names"**/
    public void onDoneClick(View view) {
        String name = ((EditText)findViewById(R.id.name_field)).getText().toString();
        System.out.println("@@@@@@@@@@@@@@@@@@@@" + name + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + name.equals(""));
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
                Bundle extras = new Bundle();
                extras.putString("user", user.getName());
                extras.putString("userEmail", user.getEmail());
                intent.putExtras(extras);

//                intent.putExtra("user", user.getName());
//                intent.putExtra("userEmail", user.getEmail());
                startActivity(intent);
            } else {
                EditText emailView = (EditText) findViewById(R.id.email_field);
                emailView.setError(getString(R.string.email_taken));
                emailView.requestFocus();
            }
        }
    }
}
