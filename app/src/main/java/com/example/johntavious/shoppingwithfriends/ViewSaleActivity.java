package com.example.johntavious.shoppingwithfriends;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Represents the screen where Users can view location of Sales.
 * Will either appear with a marker at the location, or at a
 * default location if the poster was unable to include his location.
 */
public final class ViewSaleActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play
                        // services APK is not available.
    private double lat;
    private double lon;
    private String location;
    private User user;
    private DataController dc = new SQLiteController(this);
    ImageView imgView;
    String imgURI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_sale_activiy);
        Bundle extras = this.getIntent().getExtras();
        imgView = (ImageView) findViewById(R.id.viewSaleImageView);
        if (extras != null) {
            user = dc.getUser(extras.getString("user"));
            location = extras.getString("location");
            if (location == null) {
                lat = extras.getDouble("lat");
                lon = extras.getDouble("lon");
            }
            imgURI = extras.getString("img");
            if (imgURI != null) {
                imgView.setImageURI(Uri.parse(imgURI));
                Button button = (Button) findViewById(R.id.showHideImage);
                button.setVisibility(View.VISIBLE);
            }
        }
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
        if (id == R.id.action_post_sale){
            Intent intent = new Intent(this, PostSaleActivity.class);
            intent.putExtra("user", user.getEmail());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets up the map if it is possible to do so
     * (i.e., the Google Play services APK is correctly installed)
     * and the map has not already been instantiated.
     * <p/>
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not
        // already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners
     * or move the camera. In this case, we add a marker at the Burj Khalifa
     * if no lat/long was retrieved when the sale was posted.
     */
    private void setUpMap() {
        final int zoom = 17;
        final double latOfLocation = 25.197170;
        final double lonOfLocation = 55.2745;
        if (location == null) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                    .title(getString(R.string.FoundItemGoogleMaps)));
        } else {
            //location of the Burj Khalifa in Dubai
            lat = latOfLocation;
            lon = lonOfLocation;
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                    .title(getString(R.string.Friend)
                            + getString(R.string.GoogleMapsNoLocation)));
        }
        mMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(lat, lon), zoom));
    }

    public void imageButtonClick(View view) {
        Log.d("Visibility", ""+ (imgView.getVisibility() == View.GONE));
        if (imgView.getVisibility() == View.GONE && imgURI != null) {
            Log.d("Inside if", "inside if");
            imgView.setVisibility(View.VISIBLE);
        } else {
            Log.d("inside else", "inside else");
            imgView.setVisibility(View.GONE);
        }
    }
}
