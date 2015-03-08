package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import java.util.ArrayList;

import org.w3c.dom.Text;

import java.io.File;
import java.util.NoSuchElementException;

/**
 * Represents the screen that will be displayed when a User wants to
 * view a list of his/her friends
 * @version 1.0
 */
public class FriendList extends ActionBarActivity {
    private User user;
    ArrayAdapter<User> adapter;
    DBHandler dbHandler = new DBHandler(this, null, null, 3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = dbHandler.getUser(extras.getString("user"));
//            user = LoginActivity.getUser(extras.getString("user"));
        }
        TextView header = (TextView) findViewById(R.id.friend_list_header_text);
        header.setText(user.getName() + "'s Friends");

        // Temp solution for supplying the adapter with a list of users
        // Consider adjusting adapter to accept a list of Strings

        List<String> friends = user.getFriends();
        List<User> friendsList = new ArrayList<User>();

        for (String each : friends) {
            friendsList.add(dbHandler.getUser(each));
        }

        // Populating the ListView with an adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, friendsList);
        ListView listView = (ListView) findViewById(R.id.list_of_friends);
        listView.setAdapter(adapter);

        /**
         * When a user clicks on a friend in the list, it will take him to the Friend's profile
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                TextView textView = (TextView) view;
                                                User otherUser =
                                                        dbHandler.getUser(textView.getText().toString().split(" ")[0]);
//                       LoginActivity.getUser(textView.getText().toString().split(" ")[0]);
                                                Intent friendProfile = new Intent(FriendList.this, FriendProfileActivity.class);
                                                friendProfile.putExtra("otherUser", otherUser.getName());
//                    Log.d("DEBUG", friendName);
                                                friendProfile.putExtra("user", user.getName());
                                                startActivity(friendProfile);
                                            }
                                        }
        );

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
        if (id == R.id.action_home) {
            Intent returnHome = new Intent(this, WelcomeActivity.class);
            returnHome.putExtra("user", user.getName());
            startActivity(returnHome);
        }
        if (id == R.id.action_logout) {
            Intent logout = new Intent(this, LoginActivity.class);
            startActivity(logout);
        }
        if (id == R.id.action_register_interest) {
            Intent interest = new Intent(this, RegisterInterestActivity.class);
            interest.putExtra("user", user.getName());
            startActivity(interest);
        }
        if (id == R.id.action_interests) {
            Intent interests = new Intent(this, InterestsListActivity.class);
            interests.putExtra("user", user.getName());
            startActivity(interests);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Attempts to add a new friend to the User's list of friends
     * @param view the Add Friend button click
     */
    public void onAddFriendClick(View view) {
        EditText addFriendText = (EditText)findViewById(R.id.add_friend_text);
        String searchName = addFriendText.getText().toString();
        User friend = dbHandler.getUser(searchName);
        if (friend != null) {
            dbHandler.addFriend(user, friend);
        } else {
            addFriendText.setError("No username " + searchName + " exists");
            addFriendText.requestFocus();
        }
/*
        User friendToAdd;
        if (!searchName.trim().equals("")) { // to avoid searching for no-name queries
            try {

                friendToAdd = LoginActivity.getUser(searchName);
                if (!friendToAdd.equals(user) && !user.getFriends().contains(friendToAdd)){
                    adapter.add(friendToAdd);
                    friendToAdd.addFriend(user.getName()); //adding friends should be mutual
                }
            } catch (NoSuchElementException e) {
                addFriendText.setError("No username " + searchName + " exists");
                addFriendText.requestFocus();
            }
        }  */
    }
}
