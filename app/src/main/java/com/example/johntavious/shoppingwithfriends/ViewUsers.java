package com.example.johntavious.shoppingwithfriends;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class ViewUsers extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        ListView listView = (ListView) findViewById(R.id.listView);
        List<User> list = new SQLiteController(this).getUsers();
        final ArrayAdapter<User> adapter = new ArrayAdapter<User>(this,
                        android.R.layout.simple_expandable_list_item_1,
                        list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                User u = new SQLiteController(getApplicationContext()).getUser(
                        textView.getText().toString().split(" ")[1]
                );
                showDialog(u, adapter);
            }
        });
    }

    private void showDialog(final User u, final ArrayAdapter<User> adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewUsers.this);
        builder.setTitle(R.string.deleteUser)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SQLiteController(ViewUsers.this).deleteUser(u);
                        adapter.remove(u);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_admin_signOut) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (id == R.id.action_admin_viewInterests) {
            startActivity(new Intent(this, ViewInterests.class));
        }
        if (id == R.id.action_admin_ViewSales) {
            startActivity(new Intent(this, ViewSales.class));
        }
        if (id == R.id.action_admin_home) {
            startActivity(new Intent(this, AdminWelcome.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
