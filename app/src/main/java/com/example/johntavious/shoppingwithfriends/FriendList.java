package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.Button;

public class FriendList extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        String name = "";
        String userEmail ="";
        String userName = "";

//        Intent intent_ = getIntent();
//        Bundle extras = intent_.getExtras();
//        userName = extras.getString("name");
//        userEmail = extras.getString("userEmail");
        System.out.println("Is email printing at Friends: " + userEmail);

        Bundle extras = getIntent().getExtras();
//        String userName = "";
        if (extras != null) {
            userName = extras.getString("name");
            userEmail = extras.getString("userEmail");
        }

        TextView header = (TextView) findViewById(R.id.friend_list_header_text);
        header.setText(userName + "'s Friends");


        final String uName = userName;
        final String uEmail = userEmail;
        String[] friends = User.getFriends(userName);
        if (friends.length > 0) {
             // Populating the ListView with an adapter
             ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                     android.R.layout.simple_list_item_1, friends);
             ListView listView = (ListView) findViewById(R.id.list_of_friends);
             listView.setAdapter(adapter);
        }
        final Intent intent = new Intent(this, WelcomeActivity.class);
        Bundle extras_ = new Bundle();
        extras_.putString("user", userName);
        extras_.putString("userEmail", userEmail);
        intent.putExtras(extras_);
        final EditText friendName = (EditText) findViewById(R.id.add_friend_name);
        final EditText friendEmail = (EditText) findViewById(R.id.add_friend_email);
        Button addFriend = (Button) findViewById(R.id.add_friend_button);
        addFriend.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View view) {
                        String name = friendName.getText().toString();
                        String email = friendEmail.getText().toString();
                        User.addFriend(uName, uEmail, name, email);
                        startActivity(intent);
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
}
