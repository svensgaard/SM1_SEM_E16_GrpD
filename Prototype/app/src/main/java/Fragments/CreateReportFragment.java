package Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import LocationTracking.FallbackLocationTracker;
import LocationTracking.LocationTracker;
import grpd.sm1sem.prototype.CameraActivity;
import grpd.sm1sem.prototype.MainActivity;
import grpd.sm1sem.prototype.R;


public class CreateReportFragment extends Fragment {
    private final static String TAG = "CreateReportFragment";
    private LocationTracker locationTracker;
    private Location location;

    public CreateReportFragment() {
        // Required empty public constructor
    }

    @Override
    public String toString() {
        return "CreateReportFragment";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.isAdded()) {
            locationTracker = new FallbackLocationTracker(getActivity().getApplicationContext());
            location = locationTracker.getLocation();
            locationTracker.stop();
        }
        //location.getLatitude, location.getLongitude
        //Gem koordinater, billeder, kommentarer osv. i databasen
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_report, container, false);

        Button btn = (Button)view.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CameraActivity.class));
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


}
