package Fragments;

import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import Services.GeofenceService;
import grpd.sm1sem.prototype.R;


public class MenuFragment extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    View view;
    Button createReportButton;
    Button encReportsButton;
    public GoogleApiClient googleApiClient = null;
    private final String GEOFENCE_ID = "ID";
    private final int GEOFENCE_RADIUS_IN_METERS = 30;
    private static final String TAG = "MenuFragment";


    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) getActivity().getApplicationContext())
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .build();

        //Loop igennem geofences i databasen, og kald createGeofence metoden på hver af dem
    }

    @Override
    public String toString() {
        return "MenuFragment";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        //Set onclicklisteners on buttons
        createReportButton = (Button) view.findViewById(R.id.CreateReportBtn);
        createReportButton.setOnClickListener(this);
        encReportsButton = (Button) view.findViewById(R.id.encReportsBtn);
        encReportsButton.setOnClickListener(this);

        return view;
    }

    public void showOtherFragment()
    {
        Fragment fr = new CreateReportFragment();
        FragmentChangeListener fc = (FragmentChangeListener) getActivity();
        fc.replaceFragment(fr);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.CreateReportBtn:
                showOtherFragment();
                break;
        }
    }

    @Override
    public void onResume(){
        Log.d(TAG, "onResume called");
        super.onResume();

        int response = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.getActivity());
        if (response != ConnectionResult.SUCCESS) {
            Log.d(TAG, "Google Play Services is not available - Show dialog to ask user to download it");
            GoogleApiAvailability.getInstance().getErrorDialog(this.getActivity(),response, 1).show();
        } else {
            Log.d(TAG, "Google Play Services is available - no action is required");
        }
    }

    @Override
    public void onStart(){
        Log.d(TAG, "onStart called");
        super.onStart();
        googleApiClient.reconnect();
    }

    @Override
    public void onStop(){
        Log.d(TAG, "onStop called");
        super.onStop();
        googleApiClient.disconnect();
    }

    public void startLocationMonitoring() {
        Log.d(TAG, "App is now monitoring for geofences");
        //This is where we should implement conditions to optimize battery lifetime
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

    private void createGeofence(Long latitude, Long longitude) {
        Log.d(TAG, "Geofence create process started");
        try {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(GEOFENCE_ID)
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

            Intent intent = new Intent(this.getActivity(), GeofenceService.class);
            PendingIntent pendingIntent = PendingIntent.getService(this.getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (!googleApiClient.isConnected()){
                Log.d(TAG, "GoogleApiClient is not connected");
            } else {
                LocationServices.GeofencingApi.addGeofences(googleApiClient, geofenceRequest, pendingIntent)
                        .setResultCallback(new ResultCallback<Result>(){
                            @Override
                            public void onResult(Result result){
                                if (result.getStatus().isSuccess()) {
                                    Log.d(TAG, "Successfully added geofence");
                                } else {
                                    Log.d(TAG, "Failed to add geofence + " + result.getStatus());
                                }
                            }
                        });
            }
        } catch (SecurityException e){
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
        Log.d(TAG, "Failed to connect to GoogleApiClient - " + connectionResult.getErrorMessage());
    }
}
