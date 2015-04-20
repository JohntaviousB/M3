package com.example.johntavious.shoppingwithfriends;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Represents the registration activity of the application.
 */

public final class RegistrationActivity extends Activity {
    private ImageView imgView;
    private String imgPath;
    private String methods[] = {"SMS", "PUSH"};
    String howToNotify;
    private final DataController sqlHandler = new SQLiteController(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        imgView = (ImageView) findViewById(R.id.imageView);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Profile Picture"), 1);
            }
        });
        Spinner methodSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, methods);
        methodSpinner.setAdapter(adapter);
        methodSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        if (position == 0) {
                            howToNotify = methods[0];
                        } else {
                            howToNotify = methods[1];
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                imgView.setImageURI(data.getData());
                imgPath = data.getData().toString();
            }
        }
    }
    /**
     * Cancels the Registration Activity and returns User
     * to the opening page.
     * @param view the cancel button click
     */
    public void onCancelClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Attempts to create a new User once "done" is clicked
     * and log him into the system.
     * @param view the done button click
     */

    public void onDoneClick(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        String name = ((EditText) findViewById(R.id.name_field))
                        .getText().toString().trim();
        String email = ((EditText) findViewById(R.id.email_field))
                        .getText().toString().trim();
        String password = ((EditText) findViewById(R.id.password_field))
                        .getText().toString();
        String phoneNumber = ((EditText) findViewById(R.id.PhoneNumberEditText))
                        .getText().toString();
        if (!sqlHandler.isValidUsername(name)) {
            EditText nameView = (EditText) findViewById(R.id.name_field);
            nameView.setError(getString(R.string.InvalidUsername)
                            + getString(R.string.invalidUsername2)
                            + getString(R.string.invalidUsername3)
            );
            nameView.requestFocus();
        } else if (!sqlHandler.isPasswordValid(password)) {
            EditText passwordView =
                    (EditText) findViewById(R.id.password_field);
            passwordView.setError(getString(R.string.validPasswordFormat));
            passwordView.requestFocus();
        } else if (phoneNumber.length() != 10) {
            EditText phoneView = (EditText) findViewById(R.id.PhoneNumberEditText);
            phoneView.setError("Please enter phone number with no dashes.");
            phoneView.requestFocus();
        } else if (sqlHandler.emailValid(email)) {
            Double lat = null;
            Double lon = null;
            if (checkBox.isChecked()) {
                boolean useLatLng = false;
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
                        lat = loc.getLatitude();
                        lon = loc.getLongitude();
                        useLatLng = true;
                    }
                }
                if (isNetworkEnabled && !useLatLng) {
                    loc = locMan.getLastKnownLocation(
                            LocationManager.NETWORK_PROVIDER);
                    if (loc != null) {
                        lat = loc.getLatitude();
                        lon = loc.getLongitude();
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
            User user = new User(name, email, password);
            user.setLatitude(lat);
            user.setLongitude(lon);
            user.setProfilePic(imgPath);
            user.setNotificationMethod(howToNotify.equals("SMS") ?
                    NotificationMethod.SMS : NotificationMethod.PUSH);
            user.setPhoneNumber(phoneNumber);
            sqlHandler.addUser(user);
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("user", user.getEmail());
            startActivity(intent);
        } else {
            EditText emailView = (EditText) findViewById(R.id.email_field);
            emailView.setError(getString(R.string.email_taken));
            emailView.requestFocus();
        }
    }
}
