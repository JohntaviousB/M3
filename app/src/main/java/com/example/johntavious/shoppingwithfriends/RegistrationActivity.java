package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class RegistrationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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

    public void onCancelClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onDoneClick(View view) {
        String name = ((EditText)findViewById(R.id.name_field)).getText().toString();
        String email = ((EditText)findViewById(R.id.email_field)).getText().toString();
        String password = ((EditText)findViewById(R.id.password_field)).getText().toString();
        if (LoginActivity.emailValid(email)) {
            User user = new User(name, email, password);
            LoginActivity.addUser(user);
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("user", user.getName());
            startActivity(intent);
        } else {
            EditText emailView = (EditText)findViewById(R.id.email_field);
            emailView.setError(getString(R.string.email_taken));
            emailView.requestFocus();
        }
    }
}
