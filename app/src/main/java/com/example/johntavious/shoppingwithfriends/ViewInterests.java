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


public class ViewInterests extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_interests);
        final List<Interest> list = new SQLiteController(this).getInterests();
        ListView listView = (ListView) findViewById(R.id.interestsListView);
        final ArrayAdapter<Interest> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                Interest toDelete = null;
                 for (Interest interest : list) {
                    if (interest.toString().equals(textView.getText().toString())) {
                        toDelete = interest;
                    }
                 }
                showDialog(toDelete, adapter);
            }
        });
    }

    private void showDialog(final Interest i, final ArrayAdapter<Interest> adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewInterests.this);
        builder.setTitle("Delete this interest?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SQLiteController(ViewInterests.this).deleteInterest(i);
                        adapter.remove(i);
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
        if (id == R.id.action_admin_viewUsers) {
            startActivity(new Intent(this, ViewUsers.class));
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
