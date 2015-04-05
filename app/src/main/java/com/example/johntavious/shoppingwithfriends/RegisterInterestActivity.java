package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Represents the screen where the User will register interests.
 */
public final class RegisterInterestActivity extends ActionBarActivity {
    private User user;
    private Spinner distance;
    private int maxDistance = 0;
    private final String[] distances =
            {"5", "10", "20", "35", "50", "75", "100+"};
    private final int MAX_DISTANCE = 100;
    private final DataController dc = new SQLiteController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_interest);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = dc.getUser(extras.getString("user"));
        }
        distance = (Spinner) findViewById(R.id.distance_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, distances);
        distance.setAdapter(adapter);
        distance.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View view, int position, long id) {
                    if (position < distances.length - 1) {
                        maxDistance = Integer.parseInt(distances[position]);
                    } else {
                        maxDistance = MAX_DISTANCE;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Intent logout = new Intent(this, LoginActivity.class);
            startActivity(logout);
        }
        if (id == R.id.action_friendslist) {
            Intent friendsList = new Intent(this, FriendList.class);
            friendsList.putExtra("user", user.getEmail());
            startActivity(friendsList);
        }
        if (id == R.id.action_home) {
            Intent returnHome = new Intent(this, WelcomeActivity.class);
            returnHome.putExtra("user", user.getEmail());
            startActivity(returnHome);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Attempts to register a new interest for the user.
     * @param view the Post button click
     */
    public void onPostClick(View view) {
        EditText item = (EditText) findViewById(R.id.item_name_edit_text);
        EditText price = (EditText) findViewById(
                R.id.threshold_price_edit_text);
        String itemName = item.getText().toString().trim();
        double thresholdPrice = 0;
        try {
            thresholdPrice = Double.parseDouble(price.getText().toString());
        } catch (NumberFormatException e) {
            price.setError("Please enter only valid dollar amounts");
            price.requestFocus();
        }
        if (itemName.length() < 2) {
            item.setError("Please enter a valid item name");
            item.requestFocus();
        } else if (maxDistance == 0) {
            TextView distanceText = (TextView) findViewById(R.id.distance_text);
            distanceText.setError("Please select a maximum distance");
            distance.requestFocus();
        } else if (thresholdPrice > 0) {
            dc.addInterest(user, new Interest(
                     itemName.toLowerCase(), thresholdPrice, maxDistance));
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("user", user.getEmail());
            startActivity(intent);
        }
    }

    /**
     * Cancels the post and returns the User home
     * @param view the cancel button click
     */
    public void onCancelClick(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("user", user.getEmail());
        startActivity(intent);
    }
}
