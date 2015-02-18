package com.example.johntavious.shoppingwithfriends;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class FriendList extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Bundle extras = getIntent().getExtras();
        String userName = "";
        if (extras != null) {
            userName = extras.getString("name");
        }
        TextView header = (TextView) findViewById(R.id.friend_list_header_text);
        header.setText(userName + "'s Friends");

        // Dummy data for friends list
        String[] friends = {
            "Patrick",
            "Johntavious",
            "Hosna",
            "Somayeh",
            "Clay"
        };

        // Populating the ListView with an adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, friends);
        ListView listView = (ListView) findViewById(R.id.list_of_friends);
        listView.setAdapter(adapter);
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

    public void onSearchClick(View view) {
        String searchText = ((EditText)findViewById(R.id.search_for_friend)).getText().toString();
        User friend = LoginActivity.getUser(searchText);
    }
}
