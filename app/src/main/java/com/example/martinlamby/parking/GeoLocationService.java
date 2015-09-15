package com.example.martinlamby.parking;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class GeoLocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {

    private static final int TIME_INTERVAL = 1000;
    private static GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private static Location lastLocation;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("GeoLocationService started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        System.out.println("GeoLocationService stopped");
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        //System.out.println("NEW LOCATION    " + location.getLatitude());
        lastLocation = location;
    }

    public static double getLastLocationLatitude(){
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        return lastLocation.getLatitude();
    }
    public static double getLastLocationLongitude(){
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        return lastLocation.getLongitude();
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(TIME_INTERVAL);
        mLocationRequest.setFastestInterval(TIME_INTERVAL/2);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        System.out.println("Connection Suspended   "+i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        System.out.println("Connection Failed----ErrorResult:"+connectionResult.getErrorCode());
    }
}
