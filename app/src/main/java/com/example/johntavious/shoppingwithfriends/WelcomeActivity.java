package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import java.util.List;

/**
 * Represents the home page of a logged-in user.
 */
public final class WelcomeActivity extends ActionBarActivity {
    private User user;
    private final DataController dc = new SQLiteController(this);
    private ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = dc.getUser(extras.getString("user"));
        }
        TextView welcomeText = (TextView) findViewById(R.id.welcome_text);
        welcomeText.setText("Welcome, " + user.getName());

        imgView = (ImageView) findViewById(R.id.profileImage);
        if (user.getProfilePic() != null) {
            imgView.setImageURI(Uri.parse(user.getProfilePic()));
        }
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), 1);
            }
        });
        // Friend's List
        Button friendListButton = (Button) findViewById(
                R.id.friend_list_button);
        final Intent goToFriendList = new Intent(this, FriendList.class);
        goToFriendList.putExtra("user", user.getEmail());
        friendListButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        startActivity(goToFriendList);
                    }
                }
        );
        //User's notifications
        ArrayAdapter<Notification> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, user.getNotifications());
        ListView listview = (ListView) findViewById(R.id.notificationsList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View view,
                                    int c, long d) {
                Intent intent = new Intent(
                        WelcomeActivity.this, ViewSaleActivity.class);
                intent.putExtra("user", user.getEmail());
                List<Notification> notes = user.getNotifications();
                String message = ((TextView) view).getText().toString();
                for (Notification note : notes) {
                    if (note.toString().equals(message)) {
                        intent.putExtra("location", note.getLocation());
                        if (note.getLocation() == null) {
                            intent.putExtra("lat", note.getLatitude());
                            intent.putExtra("lon", note.getLongitude());
                        }
                        WelcomeActivity.this.startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                imgView.setImageURI(data.getData());
                user.setProfilePic(data.getData().toString());
                dc.updateUser(user);
            }
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
        if (id == R.id.action_post_sale) {
            Intent postSale = new Intent(this, PostSaleActivity.class);
            postSale.putExtra("user", user.getEmail());
            startActivity(postSale);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Takes user to RegisterInterestActivity.
     * @param view the New Interest button click
     */
    public void onNewInterestClick(View view) {
        Intent intent = new Intent(this, RegisterInterestActivity.class);
        intent.putExtra("user", user.getEmail());
        startActivity(intent);
    }
}
