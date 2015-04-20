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


public class ViewSales extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sales);
        final List<Sale> list = new SQLiteController(this).getSales();
        ListView listView = (ListView) findViewById(R.id.salesListView);
        final ArrayAdapter<Sale> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1,
                list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                Sale toDelete = null;
                for (Sale sale : list) {
                    if (sale.toString().equals(textView.getText().toString())) {
                        toDelete = sale;
                    }
                }
                showDialog(toDelete, adapter);
            }
        });
    }

    private void showDialog(final Sale s, final ArrayAdapter<Sale> adapter) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewSales.this);
        builder.setTitle("Delete this sale?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new SQLiteController(ViewSales.this).deleteSale(s);
                        adapter.remove(s);
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
        if (id == R.id.action_admin_viewInterests) {
            startActivity(new Intent(this, ViewInterests.class));
        }
        if (id == R.id.action_admin_home) {
            startActivity(new Intent(this, AdminWelcome.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
