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

import org.w3c.dom.Text;

import java.util.NoSuchElementException;

/**
 * Represents the screen that will be displayed when a User wants to
 * view a list of his/her friends
 * @version 1.0
 */
public class FriendList extends ActionBarActivity {
    private User user;
    ArrayAdapter<User> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = LoginActivity.getUser(extras.getString("name"));
        }
        TextView header = (TextView) findViewById(R.id.friend_list_header_text);
        header.setText(user.getName() + "'s Friends");

        // Populating the ListView with an adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, user.getFriends());
        ListView listView = (ListView) findViewById(R.id.list_of_friends);
        listView.setAdapter(adapter);

        /**
         * When a user clicks on a friend in the list, it will take him to the Friend's profile
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view;
                    String friendName = textView.getText().toString().split(" ")[0];//gets the friend's name
                    Intent friendProfile = new Intent(FriendList.this, FriendProfileActivity.class);
                    friendProfile.putExtra("otherUserName", friendName);
//                    Log.d("DEBUG", friendName);
                    friendProfile.putExtra("userName", user.getName());
                    startActivity(friendProfile);
                }
            }
        );

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friend_list, menu);
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
     * Attempts to add a new friend to the User's list of friends
     * @param view the Add Friend button click
     */
    public void onAddFriendClick(View view) {
        EditText addFriendText = (EditText)findViewById(R.id.add_friend_text);
        String searchName = addFriendText.getText().toString();
        User friendToAdd;
        if (!searchName.trim().equals("")) { // to avoid searching for no-name queries
            try {
                friendToAdd = LoginActivity.getUser(searchName);
                if (!friendToAdd.equals(user) && !user.getFriends().contains(friendToAdd)){
                    adapter.add(friendToAdd);
                    friendToAdd.addFriend(user); //adding friends should be mutual
                }
            } catch (NoSuchElementException e) {
                addFriendText.setError("No username " + searchName + " exists");
                addFriendText.requestFocus();
            }
        }
    }

    /**
     * Returns the User to his/her homepage
     * @param view the Home Button click
     */
    public void onHomeClick(View view) {
        Intent goHome = new Intent(this, WelcomeActivity.class);
        goHome.putExtra("user", user.getName());
        startActivity(goHome);
    }

    /**
     * Signs the User out of the app
     * @param view the Sign Out Button click
     */
    public void onSignOutClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
