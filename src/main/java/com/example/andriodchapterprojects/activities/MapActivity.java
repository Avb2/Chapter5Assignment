package com.example.andriodchapterprojects.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.andriodchapterprojects.R;

public class MapActivity extends AppCompatActivity {

    LocationManager locationManager;

    LocationListener gpsListener;
    final int PERMISSION_REQUEST_LOCATION = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_map);
//        setToolbarTitle();
        initListButton();
        initSettingsButton();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_contact_map), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void startLocationUpdates() {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(MapActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            MapActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    )) {
                        Snackbar.make(findViewById(R.id.activity_contact_map),
                                "MyContactList requires this permission to locate " +
                                        "your contacts", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ActivityCompat.requestPermissions(
                                                MapActivity.this,
                                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSION_REQUEST_LOCATION
                                                );

                                    }
                                })
                                .show();

                    } else {
                        ActivityCompat.requestPermissions(
                                MapActivity.this,
                                new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                                PERMISSION_REQUEST_LOCATION);
                    }
                } else {
                    startLocationUpdates();
                }
            } else {
                startLocationUpdates();
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Error requesting permission",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String permissions[], int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationUpdates();
                } else  {
                    Toast.makeText(MapActivity.this, "MyContactList will not locate your contacts",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            locationManager.removeUpdates(gpsListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///
    private void initGetLocationButton() {
        Button locationButton = (Button) findViewById(R.id.buttonGetLocation);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    locationManager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);
                    gpsListener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            TextView txtLatitude = (TextView) findViewById(R.id.textLatitude);
                            TextView txtLongitude = (TextView) findViewById(R.id.textLongitude);
                            TextView txtAccuracy = (TextView) findViewById(R.id.textAccuracy);

                            txtLatitude.setText(String.valueOf(location.getLatitude()));
                            txtLongitude.setText(String.valueOf(location.getLongitude()));
                            txtAccuracy.setText(String.valueOf(location.getAccuracy()));
                        }
                        public void onStatusChanged(String provider, int status, Bundle extras){}
                        public void onProviderEnabled(String provider) {}
                        public void onProviderDisabled(String provider){}
                    };
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error, Location not available", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    /// Tool bar
    private void setToolbarTitle(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("My Contact list");
    }


    /// Nav Bar
    private void initListButton(){
        ImageButton ibList=findViewById(R.id.imageButtonList);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapActivity.this, ContactListActivty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initSettingsButton(){
        ImageButton ibList=findViewById(R.id.imageButtonSettings);
        ibList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapActivity.this, SettingsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

}