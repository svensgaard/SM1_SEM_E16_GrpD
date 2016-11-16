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
import grpd.sm1sem.prototype.EncounteredReportsActivity;
import grpd.sm1sem.prototype.R;


public class MenuFragment extends Fragment implements View.OnClickListener{
    View view;
    Button createReportButton;
    Button encReportsButton;


    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void showOtherFragment() {
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
            case R.id.encReportsBtn:
                Intent startEncounteredActivityIntent = new Intent(getActivity(), EncounteredReportsActivity.class);
                startActivity(startEncounteredActivityIntent);
        }
    }
}
