package Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import Database.DatabaseHelper;
import Geofencing.Geofencer;
import Wrappers.ReportWrapper;
import LocationTracking.FallbackLocationTracker;
import LocationTracking.LocationTracker;
import grpd.sm1sem.prototype.CameraActivity;
import grpd.sm1sem.prototype.EncounteredReportsActivity;
import grpd.sm1sem.prototype.MainActivity;
import grpd.sm1sem.prototype.R;


public class CreateReportFragment extends Fragment {
    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PHOTO = 100;
    private ImageView imageView;
    private View view;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_report, container, false);

        Button btn = (Button)view.findViewById(R.id.btn);
        btn.setBackgroundResource(R.color.colorDefaultButton);
        Button btn2 = (Button)view.findViewById(R.id.btn2);
        btn2.setBackgroundResource(R.color.colorDefaultButton);
        Button btn3 = (Button)view.findViewById(R.id.btn3);
        btn3.setBackgroundResource(R.color.colorDefaultButton);
        this.imageView = (ImageView)view.findViewById(R.id.imageView);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        // Inflate the layout for this fragment

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) view.findViewById(R.id.imageView);
                if(img.getDrawable() != null) {

                    EditText topic = (EditText) view.findViewById(R.id.textTopic);
                    EditText desc = (EditText) view.findViewById(R.id.textDesc);

                    Bitmap bitImage = ((BitmapDrawable) img.getDrawable()).getBitmap();

                    locationTracker = new FallbackLocationTracker(getActivity().getApplicationContext());
                    locationTracker.start();
                    location = locationTracker.getLocation();
                    locationTracker.stop();
                    ReportWrapper rw;
                    if (location != null) {
                        rw = new ReportWrapper(0, topic.getText().toString(), "", desc.getText().toString(), location.getLatitude(), location.getLongitude(), "", "", "", "", bitImage, 0);
                    } else {
                        rw = new ReportWrapper(0, topic.getText().toString(), "", desc.getText().toString(), (double) 34, (double) 45, "", "", "", "", bitImage, 0);
                    }

                    DatabaseHelper dbh = new DatabaseHelper(getActivity());
                    dbh.insertReport(dbh.getWritableDatabase(), rw);

                    CharSequence text = "Report Created!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                } else {
                    CharSequence text = "No image found!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                }
            }

        });
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }

        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    InputStream imageStream = null;
                    try {
                        imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView.setImageBitmap(yourSelectedImage);// To display selected image in image view
                }
        }
    }



}
