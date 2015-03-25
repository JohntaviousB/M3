package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InterestsListActivity extends ActionBarActivity {
    private User user;
    private DataController dc = new SQLiteController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests_list);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = dc.getUser(extras.getString("user"));
        }
        // Populating the ListView with an adapter
        ArrayAdapter<Interest> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, user.getInterests());
        ListView listView = (ListView) findViewById(R.id.interests_list);
        listView.setAdapter(adapter);

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
        if (id == R.id.action_home) {
            Intent returnHome = new Intent(this, WelcomeActivity.class);
            returnHome.putExtra("user", user.getEmail());
            startActivity(returnHome);
        }
        if (id == R.id.action_logout) {
            Intent logout = new Intent(this, LoginActivity.class);
            startActivity(logout);
        }
        if (id == R.id.action_register_interest) {
            Intent interest = new Intent(this, RegisterInterestActivity.class);
            interest.putExtra("user", user.getEmail());
            startActivity(interest);
        }
        if (id == R.id.action_friendslist) {
            Intent friends = new Intent(this, FriendList.class);
            friends.putExtra("user", user.getEmail());
            startActivity(friends);
        }
        if (id == R.id.action_post_sale) {
            Intent postSale = new Intent(this, PostSaleActivity.class);
            postSale.putExtra("user", user.getEmail());
            startActivity(postSale);
        }

        return super.onOptionsItemSelected(item);
    }
}
