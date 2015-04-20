package com.example.johntavious.shoppingwithfriends;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents the screen that will be displayed when a User wants to
 * view a list of his/her friends.
 */
public final class FriendList extends ActionBarActivity {
    private User user;
    private ArrayAdapter<User> adapter;
    private final DataController dc = new SQLiteController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = dc.getUser(extras.getString("user"));
        }
        TextView header = (TextView) findViewById(R.id.friend_list_header_text);
        header.setText(user.getName() + "'s Friends");
        List<String> friends = user.getFriends();
        List<User> friendsList = new ArrayList<>();
        for (String each : friends) {
            User u = dc.getUserByName(each);
            friendsList.add(u);
        }

        // Populating the ListView with an adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, friendsList);
        ListView listView = (ListView) findViewById(R.id.list_of_friends);
        listView.setAdapter(adapter);

        /**
         * When a user clicks on a friend in the list, it will
         * take him to the Friend's profile
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                  int position, long id) {
                TextView textView = (TextView) view;
                User otherUser =
                        dc.getUser(textView.getText().toString().split(" ")[1]);
                Intent friendProfile = new Intent(
                        FriendList.this, FriendProfileActivity.class);
                friendProfile.putExtra("otherUser", otherUser.getEmail());
                friendProfile.putExtra("user", user.getEmail());
                startActivity(friendProfile);
            }
        }
        );

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
        if (id == R.id.action_interests) {
            Intent interests = new Intent(this, InterestsListActivity.class);
            interests.putExtra("user", user.getEmail());
            startActivity(interests);
        }
        if (id == R.id.action_post_sale) {
            Intent postSale = new Intent(this, PostSaleActivity.class);
            postSale.putExtra("user", user.getEmail());
            startActivity(postSale);
        }
        if (id == R.id.action_update_profile) {
            Intent intent = new Intent(this, UpdateProfile.class);
            intent.putExtra("user", user.getEmail());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Attempts to add a new friend to the User's list of friends.
     * @param view the Add Friend button click
     */
    public void onAddFriendClick(View view) {
        EditText addFriendText = (EditText) findViewById(R.id.add_friend_text);
        String searchName = addFriendText.getText().toString();
        addFriendText.setText("");
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(addFriendText.getWindowToken(), 0);
        User friend = dc.getUserByName(searchName);
        if (friend != null) {
            if (dc.addFriend(user, friend)) {
                adapter.add(friend);
            }
        } else {
            addFriendText.setError("No username " + searchName + " exists");
            addFriendText.requestFocus();
        }
    }
}
