package com.example.johntavious.shoppingwithfriends;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class UpdateProfile extends ActionBarActivity {
    private User user;
    private DataController dc = new SQLiteController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = dc.getUser(extras.getString("user"));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if (id == R.id.action_home) {
            Intent goHome = new Intent(this, WelcomeActivity.class);
            goHome.putExtra("user", user.getEmail());
            startActivity(goHome);
        }

        return super.onOptionsItemSelected(item);
    }
    public void onUpdateCancel(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("user", user.getEmail());
        startActivity(intent);
    }
    public void onUpdate(View view) {
        String oldUsername = user.getName();
        String username = user.getName();
        String email = user.getEmail();
        String password = user.getPassword();
        String phoneNumber = user.getPhoneNumber();
        Double latitude = user.getLatitude();
        Double longitude = user.getLongitude();

        EditText nameEditText = (EditText) findViewById(R.id.updateUsername);
        EditText emailEditText = (EditText) findViewById(R.id.updateEmail);
        EditText passwordEditText = (EditText) findViewById(R.id.updatePassword);
        EditText phoneNumberEditText = (EditText) findViewById(R.id.updatePhoneNumber);
        EditText latitudeEditText = (EditText) findViewById(R.id.updateLatitude);
        EditText longitudeEditText = (EditText) findViewById(R.id.updateLongitude);
        Switch updateLocation = (Switch) findViewById(R.id.updateLocation);

        String potentialUsername = nameEditText.getText().toString().trim();
        String potentialEmail = emailEditText.getText().toString().trim();
        String potentialPassword = passwordEditText.getText().toString();
        String potentialPhoneNumber = phoneNumberEditText.getText().toString();
        String potentialLatitude = latitudeEditText.getText().toString();
        String potentialLongitude = longitudeEditText.getText().toString();

        if (dc.isValidUsername(potentialUsername)) {
            username = potentialUsername;
        } else if (potentialUsername.length() > 0) {
            nameEditText.setError("Username taken or invalid");
            return;
        }
        if (dc.emailValid(potentialEmail)) {
            email = potentialEmail;
        } else if (potentialEmail.length() > 0) {
            emailEditText.setError("Invalid or taken email");
            return;
        }
        if (potentialPassword.length() > 3) {
            password = potentialPassword;
        } else if (potentialPassword.length() > 0) {
            passwordEditText.setError("Password too short");
            return;
        }
        if (potentialPhoneNumber.length() == 10) {
            phoneNumber = potentialPhoneNumber;
        } else if (potentialPhoneNumber.length() > 0) {
            phoneNumberEditText.setError("Enter a valid number");
            return;
        }
        boolean useLatLng = false;
        if (updateLocation.isChecked()) {
            //checks to see if it is possible to retrieve the location
            LocationManager locMan =
                    (LocationManager) this.getSystemService(
                            Context.LOCATION_SERVICE);
            boolean isGPSEnabled = locMan.isProviderEnabled(
                    LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled =
                    locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Location loc;
            if (isGPSEnabled) {
                loc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (loc != null) {
                    latitude = loc.getLatitude();
                    longitude = loc.getLongitude();
                    useLatLng = true;
                }
            }
            if (isNetworkEnabled && !useLatLng) {
                loc = locMan.getLastKnownLocation(
                        LocationManager.NETWORK_PROVIDER);
                if (loc != null) {
                    latitude = loc.getLatitude();
                    longitude = loc.getLongitude();
                    useLatLng = true;
                }
            }
            if (!useLatLng) {
                Toast frenchToast = Toast.makeText(this,
                        getString(R.string.unableToRetreiveLocation),
                        Toast.LENGTH_SHORT);
                frenchToast.show();
            }
        }
        if (potentialLatitude.length() != 0 && !useLatLng) {
            try {
                latitude = Double.parseDouble(potentialLatitude);
            } catch (NumberFormatException e) {
                latitudeEditText.setError("Invalid latitude");
                return;
            }
        }
        if (potentialLongitude.length() != 0 && !useLatLng) {
            try {
                longitude = Double.parseDouble(potentialLongitude);
            } catch (NumberFormatException e) {
                longitudeEditText.setError("Invalid Longitude");
                return;
            }
        }
        user.setName(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setLatitude(latitude);
        user.setLongitude(longitude);
        dc.updateUser(user, oldUsername);

        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("user", user.getEmail());
        startActivity(intent);
    }
}
