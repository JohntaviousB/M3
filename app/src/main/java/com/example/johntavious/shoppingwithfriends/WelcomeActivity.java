package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

/**
 * The hompe page of a logged-in user
 * @version 1.0
 */
public class WelcomeActivity extends ActionBarActivity {
    User user;

    DBHandler dbHandler = new DBHandler(this, null, null, 3);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            user = dbHandler.getUser(extras.getString("user"));
//            user = LoginActivity.getUser(extras.getString("user"));
        }
        TextView welcomeText = (TextView)findViewById(R.id.welcome_text);
        welcomeText.setText("Welcome, " + user.getName());

        // Friend's List
        Button friend_list_button = (Button) findViewById(R.id.friend_list_button);
        final Intent goToFriendList = new Intent(this, FriendList.class);
        goToFriendList.putExtra("user", user.getName());
        friend_list_button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        startActivity(goToFriendList);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            Intent logout = new Intent(this, LoginActivity.class);
            startActivity(logout);
        }
        if (id == R.id.action_friendslist) {
            Intent friendsList = new Intent(this, FriendList.class);
            friendsList.putExtra("user", user.getName());
            startActivity(friendsList);
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
     * Takes user to RegisterInterestActivity
     * @param view the New Interest button click
     */
    public void onNewInterestClick(View view) {
        Intent intent = new Intent(this, RegisterInterestActivity.class);
        intent.putExtra("user", user.getName());
        startActivity(intent);
    }
}
