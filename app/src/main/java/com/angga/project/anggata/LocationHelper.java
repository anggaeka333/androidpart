package com.angga.project.anggata;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationHelper extends Service implements
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private static LocationHelper locationHelper;

    private static GoogleApiClient googleApiClient;
    private Location location;
    private LocationRequest locationRequest;
    private static LocationHelperListener listener;
    private static Context context;
    private Intent intent;
    private boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService();
        Log.d("service", "started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopService();
        super.onDestroy();
    }

    public interface LocationHelperListener {
        public void onGetNewLocation(Location location);
        public void onServiceStateChanged(boolean state);
    }

    public boolean isRunning() {
        return isRunning;
    }

    public LocationHelper() {};

    private LocationHelper(Context context) {
        this.context = context;
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            createLocationRequest();
            isRunning = false;
            listener = (LocationHelperListener) context;
        }
    }

    public static LocationHelper GetInstance(Context context) {
        if (locationHelper == null)
            locationHelper = new LocationHelper(context);
        return locationHelper;
    }


    private void startService() {
        if(googleApiClient == null)
            locationHelper = new LocationHelper(context);
        googleApiClient.connect();
        isRunning = true;
        listener.onServiceStateChanged(isRunning);
    }

    private void stopService() {
        isRunning = false;
        listener.onServiceStateChanged(isRunning);
        stopLocationUpdates();
        googleApiClient.disconnect();
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        this.listener.onGetNewLocation(location);
        Log.d("Location changed",location.getLatitude() + ", " + location.getLongitude());
    }

    @Override
    public void onConnected(Bundle bundle) {
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
