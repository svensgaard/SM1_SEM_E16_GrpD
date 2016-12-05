package Geofencing;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import Services.GeofenceService;

/**
 * Created by Dan on 15-11-2016.
 */

public class Geofencer implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {
    private final int GEOFENCE_RADIUS_IN_METERS = 30;
    private static final String TAG = "Geofencer";
    public GoogleApiClient googleApiClient = null;
    private Context context;

    public Geofencer() {
    }

    public Geofencer(Context context) {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        this.context = context;
    }

    public void disconnect() {
        Log.d(TAG, "Disconnected GoogleApiClient");
        googleApiClient.disconnect();
    }

    public void reconnect() {
        Log.d(TAG, "Reconnected GoogleApiClient");
        googleApiClient.reconnect();
    }

    public void startLocationMonitoring() {
        Log.d(TAG, "App is now monitoring for geofences");
        //This is where we should implement conditions to optimize battery lifetime
        if (!googleApiClient.isConnected()) {
            Log.d(TAG, "Google API is not connected");
        } else {
            try {
                final LocationRequest locationRequest = LocationRequest.create()
                        .setInterval(10000)
                        .setFastestInterval(5000)
                                //.setNumUpdates(5)
                        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.d(TAG, "Location update lat/long " + location.getLatitude() + " " + location.getLongitude());
                    }
                });
            } catch (SecurityException e) {
                Log.d(TAG, "SecurityException - " + e.getMessage());
            }
        }
    }

    public void createGeofence(String ID, long latitude, long longitude) {
        Log.d(TAG, "Geofence create process started");
        try {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(ID)
                    .setCircularRegion(
                            latitude,
                            longitude,
                            GEOFENCE_RADIUS_IN_METERS
                    )
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build();

            GeofencingRequest geofenceRequest = new GeofencingRequest.Builder()
                    .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                    .addGeofence(geofence).build();

            Intent intent = new Intent(context, GeofenceService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (!googleApiClient.isConnected()) {
                Log.d(TAG, "GoogleApiClient is not connected");
            } else {
                LocationServices.GeofencingApi.addGeofences(googleApiClient, geofenceRequest, pendingIntent)
                        .setResultCallback(this);
            }
        } catch (SecurityException e) {
            Log.d(TAG, "SecurityException - " + e.getMessage());
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Connected to GoogleApiClient");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Suspended connection to GoogleApiClient");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Failed to connect to GoogleApiClient - " + connectionResult.getErrorCode());
    }

    @Override
    public void onResult(Status status) {

        if (status.getStatus().isSuccess()) {
            Log.d(TAG, "Successfully added geofence");
        } else {
            Log.d(TAG, "Failed to add geofence + " + status.getStatus());
        }
    }

}


