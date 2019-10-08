package com.example.punto_verde;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.punto_verde.Config.Config;
import com.example.punto_verde.Interface.Apiservice;
import com.example.punto_verde.model.ImagePlace;
import com.example.punto_verde.model.Place;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    AppCompatActivity aux = this;
    private GreenAdapter greenAdapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_menu_green_24dp);

        toolbar.inflateMenu(R.menu.menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.INTERNET) !=
                            PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 1);
                return;
            }
        }


        retrofit = Config.getRetrofit();

        final BottomNavigation btNav = findViewById(R.id.BottomNavigation);

        RecyclerView rv_place = findViewById(R.id.rv_places);

        greenAdapter = new GreenAdapter(this);
        rv_place.setAdapter(greenAdapter);

        rv_place.setHasFixedSize(true);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rv_place.setLayoutManager(gridLayoutManager);

        btNav.setMenuItemSelectionListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(int i, int i1, boolean b) {

                if (i == btNav.getMenuItemId(0)) {

                }

                if (i == btNav.getMenuItemId(1)) {
                    Intent intent = new Intent(aux, MapsActivity.class);
                    startActivity(intent);
                }

                if (i == btNav.getMenuItemId(2)) {

                }

            }

            @Override
            public void onMenuItemReselect(int i, int i1, boolean b) {
                Log.i(TAG, "onMenuItemReselect: i " + i);
                Log.i(TAG, "onMenuItemReselect: i1 " + i1);
                Log.i(TAG, "onMenuItemReselect: b " + b);

                if (i == btNav.getMenuItemId(0)) {

                }

                if (i == btNav.getMenuItemId(1)) {

                }

                if (i == btNav.getMenuItemId(2)) {

                }
            }
        });

        getPlace();
        getImages();
        checkPermission();
    }

    private void checkPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                    .PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager
                            .PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 1);
                return;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.san_jose:
                Toast.makeText(this, "Hola", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPlace(){

        Apiservice service = retrofit.create(Apiservice.class);
        Call<List<Place>> listCall = service.getPlaces();

        listCall.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                if(response.isSuccessful()){
                    greenAdapter.addPlace((ArrayList<Place>) response.body());
                }else{
                    Log.i(TAG, "onResponse: ");
                }
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t);
                getPlace();
            }

        });
    }

    private void getImages() {

        Apiservice service = retrofit.create(Apiservice.class);
        final Call<List<ImagePlace>> listCall = service.getImages();

        listCall.enqueue(new Callback<List<ImagePlace>>() {
            @Override
            public void onResponse(Call<List<ImagePlace>> call, Response<List<ImagePlace>> response) {
                if (response.isSuccessful()){
                    greenAdapter.addImage((ArrayList<ImagePlace>) response.body());
                }else {
                    Log.i(TAG, "onResponse: "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<ImagePlace>> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
                getImages();
            }
        });

    }

}

