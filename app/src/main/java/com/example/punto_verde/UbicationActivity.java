package com.example.punto_verde;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Locale;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

public class UbicationActivity extends FragmentActivity implements OnMapReadyCallback {


    private static final String TAG = "Maps";
    private GoogleMap mMap;
    FragmentActivity aux = this;
    private BottomNavigation btNav;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key), Locale.US);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        btNav = findViewById(R.id.BottomNavigation);
        btNav.setMenuItemSelectionListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int i, int i1, boolean b) {
                Log.i(TAG, "onMenuItemSelect: i " + i);
                Log.i(TAG, "onMenuItemSelect: i1 " + i1);
                Log.i(TAG, "onMenuItemSelect: b " + b);

                if(i == btNav.getMenuItemId(0)){
                    Intent intent = new Intent(aux, MainActivity.class);
                    startActivity(intent);
                }

                if(i == btNav.getMenuItemId(1)){

                }

                if(i == btNav.getMenuItemId(2)){

                }


            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {
                Log.i(TAG, "onMenuItemReselect: i " + i);
                Log.i(TAG, "onMenuItemReselect: i1 " + i1);
                Log.i(TAG, "onMenuItemReselect: b " + b);

                if(i == btNav.getMenuItemId(0)){

                }

                if(i == btNav.getMenuItemId(1)){

                }

                if(i == btNav.getMenuItemId(2)){

                }

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(17.0f);
        mMap.setMaxZoomPreference(20.0f);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.

                        if (location != null) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .title("Mi Ubicación"));
                        }else{
                            Log.i("fusedLocationClient:","location null");
                        }
                    }
                });
        Intent intent = getIntent();
        mMap.addMarker(new MarkerOptions().position
                (new LatLng(intent.getDoubleExtra("lat",0),
                        intent.getDoubleExtra("lng", 0)))
                .title(intent.getStringExtra("name"))).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(intent.getDoubleExtra("lat",0),
                intent.getDoubleExtra("lng", 0))));
    }

}
