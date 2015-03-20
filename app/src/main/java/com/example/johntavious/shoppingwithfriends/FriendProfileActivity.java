package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity that represents the profile of a User not signed in on the device
 * Can be a friend or nonfriend
 */
public class FriendProfileActivity extends ActionBarActivity {
    User user;
    User otherUser;
    Button friendshipButton;
    DataController dc = new DataController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);
        Bundle extras = getIntent().getExtras();
        user = dc.getUser(extras.getString("user"));

        otherUser = dc.getUser(extras.getString("otherUser"));

        ((TextView)findViewById(R.id.otherUser_profile_header)).setText(otherUser.getName()
                + "'s Profile");
        ((TextView)findViewById(R.id.averge_rating)).setText(otherUser.getAverageRating()
                + "/5.0");
        ((TextView)findViewById(R.id.user_email_text)).setText(otherUser.getEmail());

        friendshipButton = ((Button)findViewById(R.id.friendship_button));
        updateViewContents(); //see private method below

        // will add or remove a user from this user's friends list
        // note the change will also be reflected in the other user's list based on
        // the methods in the User class
        friendshipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.isFriendsWith(otherUser)) {
                    user.unfriend(otherUser);
                    dc.unfriend(user, otherUser);
                    updateViewContents(); //resets the text of the screen to reflect change
                } else {
                    user.addFriend(otherUser.getName());
                    dc.addFriend(user, otherUser);
                    updateViewContents(); //resets the text of the screen to reflect change
                }
                Log.d("DEBUG", "User's friendsList size: " + user.getFriends().size()
                + " Friends friendslist size:" + otherUser.getFriends().size());
            }
        });

    }

    private void updateViewContents() {
        //Sets the content of the screen based on friendship
        if (user.isFriendsWith(otherUser)) {
            ((TextView)findViewById(R.id.friendship_text)).setText(getString(R.string.friendship_text));

            ((TextView)findViewById(R.id.sales_shared_to_user_text)).setText(otherUser.getName()
                    + " has shared " + user.getSalesReceivedByUser(otherUser) + " sales with you");

            ((TextView)findViewById(R.id.sales_shared_to_user_text)).setVisibility(View.VISIBLE);

            friendshipButton.setText("Unfriend");
        } else {
            ((TextView)findViewById(R.id.friendship_text)).setText(getString(R.string.nonfriendship_text));

            ((TextView)findViewById(R.id.sales_shared_to_user_text)).setVisibility(View.GONE);

            friendshipButton.setText("Add Friend");
        }
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
            returnHome.putExtra("user", user.getEmail());
            startActivity(returnHome);
        }
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
        return super.onOptionsItemSelected(item);
    }
}
