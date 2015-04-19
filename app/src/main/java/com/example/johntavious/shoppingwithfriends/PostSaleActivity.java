package com.example.johntavious.shoppingwithfriends;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.Duration;


public final class PostSaleActivity extends ActionBarActivity {
    private User user;
    private final DataController dc = new SQLiteController(this);
    private Double lat = null;
    private Double lon = null;
    String imageURI;
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_sale);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = dc.getUser(extras.getString("user"));
        }
        mImageView = (ImageView)findViewById(R.id.saleImageView);
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
        if (id == R.id.action_home) {
            Intent home = new Intent(this, WelcomeActivity.class);
            home.putExtra("user", user.getEmail());
            startActivity(home);
        }
        if (id == R.id.action_update_profile) {
            Intent intent = new Intent(this, UpdateProfile.class);
            intent.putExtra("user", user.getEmail());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Attempts to post the sale found by the user
     * and returns home if successfully posted.
     * @param view the post button click
     */
    public void onPostClick(View view) {
        EditText itemText = (EditText) findViewById(
                R.id.postSaleEditText);
        EditText priceText = (EditText) findViewById(
                R.id.postSalePriceEditText);
        EditText latitudeText = (EditText) findViewById(
                R.id.postSaleLatitudeEditText);
        EditText longitudeText = (EditText) findViewById(
                R.id.postSaleLongitudeEditText);
        Switch locationSwitch = (Switch) findViewById(
                R.id.retrieveLocationSwitch);
        String itemName = itemText.getText().toString().trim();
        boolean useLatLng = false; //keeps track of whether the location
                                //will be auto retrieved
        double price = 0;
        try {
            price = Double.parseDouble(priceText.getText().toString());
        } catch (NumberFormatException e) {
            priceText.setError(getString(R.string.invalidSalePrice));
            priceText.requestFocus();
        }
        if (locationSwitch.isChecked()) {
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
        if (!useLatLng) {
            try {
                lat = Double.parseDouble(latitudeText.getText().toString());
                lon = Double.parseDouble(longitudeText.getText().toString());
            } catch (NumberFormatException e) {
                latitudeText.setError("Enter valid coordinates!");
                latitudeText.requestFocus();
            }
        }
        if (itemName.length() < 2) {
            itemText.setError(getString(R.string.invalidSaleItem));
            itemText.requestFocus();
        } else if (price <= 0) {
            priceText.setError(getString(R.string.invalidSalePrice));
            priceText.requestFocus();
        } else if (lat != null && lon != null) {
            Sale sale = new Sale(user.getName(), itemName.toLowerCase(), price, lat, lon);
            sale.setImageURI(imageURI);
            dc.addSale(sale);
            Intent intent = new Intent(this, WelcomeActivity.class);
            intent.putExtra("user", user.getEmail());
            startActivity(intent);
        }
    }

    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                imageURI = photoFile.toURI().toString();
                Log.d("PSA_IMAGEURI", imageURI +"");
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            mImageView.setImageURI(Uri.parse(imageURI));
            mImageView.setVisibility(View.VISIBLE);
        }
    }
    /**
     * Cancels the Sale Post and returns the user home.
     * @param view the Cancel button click
     */
    public void onCancelClick(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra("user", user.getEmail());
        startActivity(intent);
    }
}
