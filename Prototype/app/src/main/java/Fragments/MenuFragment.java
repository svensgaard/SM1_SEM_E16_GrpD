package Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;

import MovementDetection.MovementDetector;
import grpd.sm1sem.prototype.EncounteredReportsActivity;
import grpd.sm1sem.prototype.R;


public class MenuFragment extends Fragment implements View.OnClickListener, SensorEventListener{
    View view;
    Button createReportButton;
    Button encReportsButton;
    GoogleApiClient googleApiClient;
    MovementDetector movementDetector;
    private final static String TAG = "MenuFragment";

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        movementDetector = new MovementDetector(getActivity());
    }

    /*private void InitializeGeofencing() {
        DatabaseHelper dbh = new DatabaseHelper(getActivity());
        List<GeoReport> geoReportList = dbh.getAllGeoReports();

        geofencer = new Geofencer(this.getActivity());

        for (GeoReport report : geoReportList){
            geofencer.createGeofence(report.getID(), report.getLatitude(), report.getLongitude());
            Log.d(TAG, "Created geofence with id " + report.getID() + " latitude " + report.getLatitude() + " longitude " + report.getLongitude());
        }
    }*/

    @Override
    public String toString() {
        return "MenuFragment";
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);
        //Set onclicklisteners on buttons
        createReportButton = (Button) view.findViewById(R.id.CreateReportBtn);
        createReportButton.setOnClickListener(this);
        createReportButton.setBackgroundResource(R.color.colorDefaultButton);
        encReportsButton = (Button) view.findViewById(R.id.encReportsBtn);
        encReportsButton.setOnClickListener(this);
        encReportsButton.setBackgroundResource(R.color.colorDefaultButton);

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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
