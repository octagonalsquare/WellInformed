package com.example.wellinformed2;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.ui.IconGenerator;

//GoogleMapActivity displays google map with custom markers with wells name
//When a marker is clicked it displays more details about the well.
public class GoogleMapActivity extends FragmentActivity implements
        OnMapReadyCallback,
        View.OnClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMarkerClickListener{


    private static final String TAG = "Service location:";
    private GoogleMap mMap;
    private DatabaseReference mWellRef;
    private DatabaseReference mUserRef;
    private Location userLocation;
    protected LocationManager locationManager;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private static final int MY_LOCATION_REQUEST_CODE = 901;

    private boolean mPermissionDenied;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        enableMyLocation();
        addWellMarkers(mMap);

        if(mMap.isMyLocationEnabled()) {
            updateUserLocation(mMap);
        }

    }

    //method is in progress - This will updated the user information in the database evertime a user's location is changed
    //and will display that change on the map to provide user location
    private void updateUserLocation(GoogleMap googleMap){
        IconGenerator customIcon = new IconGenerator(this);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.worker_icon_24dp, getTheme());

        googleMap.setOnMarkerClickListener(this);
        mUserRef = FirebaseDatabase.getInstance().getReference().child("User");
        mUserRef.child(mAuth.getUid()).child("latitude").setValue(userLocation.getLatitude());
        mUserRef.child(mAuth.getUid()).child("longitude").setValue(userLocation.getLongitude());

        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot:dataSnapshot.getChildren()){
                    User user = userSnapshot.getValue(User.class);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(user.getLatitude(),user.getLongitude())).title(user.getName())).setIcon(BitmapDescriptorFactory.defaultMarker());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Adds well markers with custom well name to display on the map
    private void addWellMarkers(GoogleMap googleMap){
        IconGenerator customIcon = new IconGenerator(this);

        // Add points for waterwells
        googleMap.setOnMarkerClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mWellRef = FirebaseDatabase.getInstance().getReference().child("Well");
        mWellRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                googleMap.clear();
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                    Well well = postSnapshot.getValue(Well.class);

                    if(!well.Latitude.equals("")&&!well.Longitude.equals("")) {
                        if(well.Status.toUpperCase().equals("ACTIVE")) {
                            LatLng wellLocation = new LatLng(Double.parseDouble(well.Latitude), Double.parseDouble(well.Longitude));
                            mMap.addMarker(new MarkerOptions().position(wellLocation).title(well.Status).snippet(well.Latitude + " " + well.Longitude)).setIcon(BitmapDescriptorFactory.fromBitmap(customIcon.makeIcon(well.Name)));
                        }
                        else if(well.Status.toUpperCase().equals("PLUGGED")){
                            LatLng wellLocation = new LatLng(Double.parseDouble(well.Latitude), Double.parseDouble(well.Longitude));
                            mMap.addMarker(new MarkerOptions().position(wellLocation).title(well.Status).snippet(well.Latitude + " " + well.Longitude)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, MY_LOCATION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, false);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            locationManager = (LocationManager)this.getSystemService(LOCATION_SERVICE);
            userLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            userLocation.setElapsedRealtimeNanos(1000);
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.getLatitude(),userLocation.getLongitude()),10));

        }
    }

    //Checks to see if permission is granted so the app can access the user's location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != MY_LOCATION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    //displays a message that the location button was clicked
    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    //displays message with the user's location when clicked
    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

    }

    //if permission is not granted then displays a message to the user
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getFragmentManager(), "dialog");
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

}
