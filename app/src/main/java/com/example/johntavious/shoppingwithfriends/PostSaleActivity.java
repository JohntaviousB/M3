package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class PostSaleActivity extends ActionBarActivity {
    User user;
    DataController dc = new DataController(this);
    String itemName;
    String location;
    double price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_sale);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = dc.getUser(extras.getString("user"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_register_interest) {
            Intent interest = new Intent(this, RegisterInterestActivity.class);
            interest.putExtra("user", user.getEmail());
            startActivity(interest);
        }
        if (id == R.id.action_interests) {
            Intent interests = new Intent(this, InterestsListActivity.class);
            interests.putExtra("user", user.getEmail());
            startActivity(interests);
        }
        if (id == R.id.action_home) {
            Intent home = new Intent(this, WelcomeActivity.class);
            home.putExtra("user", user.getEmail());
            startActivity(home);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Attempts to post the sale found by the user and returns home if successfully posted
     * @param view the post button click
     */
    public void onPostClick(View view) {
        EditText itemText = (EditText)findViewById(R.id.postSaleEditText);
        EditText priceText = (EditText)findViewById(R.id.postSalePriceEditText);
        EditText locationText = (EditText)findViewById(R.id.postSaleLocationEditText);
        String itemName = itemText.getText().toString().trim();
        double price = 0;
        try {
            price = Double.parseDouble(priceText.getText().toString());
        } catch (NumberFormatException e) {
            priceText.setError(getString(R.string.invalidSalePrice));
            priceText.requestFocus();
        }
        String location = locationText.getText().toString().trim();
        if (itemName.length() < 2) {
            itemText.setError(getString(R.string.invalidSaleItem));
            itemText.requestFocus();
        } else if (price <= 0) {
            priceText.setError(getString(R.string.invalidSalePrice));
            priceText.requestFocus();
        } else if (locationText.length() >= 2) {
            dc.addSale(new Sale(user.getName(), itemName, price, location));
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("user", user.getEmail());
            startActivity(intent);
        } else {
            locationText.setError(getString(R.string.invalidSaleLocation));
            locationText.requestFocus();
        }
    }

    /**
     * Cancels the Sale Post and returns the user home
     * @param view the Cancel button click
     */
    public void onCancelClick(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("user", user.getEmail());
        startActivity(intent);
    }
}
