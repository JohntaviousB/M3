package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class AdminWelcome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_admin_signOut) {
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (id == R.id.action_admin_viewUsers) {
            startActivity(new Intent(this, ViewUsers.class));
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
    public void viewUsers(View view) {
        startActivity(new Intent(this, ViewUsers.class));
    }
    public void viewSales(View view) {
        startActivity(new Intent(this, ViewSales.class));
    }
    public void viewInterests(View view) {
        startActivity(new Intent(this, ViewInterests.class));
    }
    public void sendMessageClick(View view) {

    }
}
