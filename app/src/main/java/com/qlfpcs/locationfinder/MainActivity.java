package com.qlfpcs.locationfinder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_PERMISSION = 1;

    private double mLatitude;
    private double mLongitude;

//    private Button startButton;
//    private Button stopButton;
//    private TextView latitudeText;
//    private TextView longitudeText;
//    private TextView locationText;

    @BindView(R.id.startButton) Button startButton;
    @BindView(R.id.stopButton) Button stopButton;
    @BindView(R.id.latitudeText) TextView latitudeText;
    @BindView(R.id.longitudeText) TextView longitudeText;
    @BindView(R.id.LocationText) TextView locationText;

    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        startButton = findViewById(R.id.startButton);
//        stopButton = findViewById(R.id.stopButton);

        ButterKnife.bind(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


    }

    @OnClick(R.id.startButton)
    public void startFindLocation()
    {
        getLastKnownLocation();
    }


    void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions();
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {

                            mLatitude = location.getLatitude();
                            mLongitude = location.getLongitude();

                            updateUI();
                        }
                    }
                });
    }

    private void updateUI() {

        latitudeText.setText(Double.toString(mLatitude));
        longitudeText.setText(Double.toString(mLongitude));

    }

    private void requestPermissions() {

        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
        {

        }
        else
        {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case REQUEST_PERMISSION:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                }
            }
        }
    }
}
