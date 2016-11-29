package Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import Database.Contract;
import Database.DatabaseHelper;
import Utitlities.ImageUtils;
import Wrappers.ReportWrapper;
import grpd.sm1sem.prototype.R;

public class ReportFragment extends Fragment {

    private static final String ARG_PARAM1 = "reportWrapper";

    private int reportID;
    private ReportWrapper report;

    public ReportFragment() {
        // Required empty public constructor
    }

    /* Use this method to get an instance of the fragement! */
    public static ReportFragment newInstance(Integer ID) {
        ReportFragment fragment = new ReportFragment();

        Bundle args = new Bundle();
        args.putInt("ID", ID);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        reportID = getArguments().getInt("ID");
        report = dbHelper.getReport(reportID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Find the views
        View mainView = inflater.inflate(R.layout.fragment_report, container, false);
        TextView emneTextView = (TextView) mainView.findViewById(R.id.EmneTextView);
        TextView elementTextView = (TextView) mainView.findViewById(R.id.elementTextView);
        TextView descriptionTextView = (TextView) mainView.findViewById(R.id.descriptionTextView);
        ImageView imageView = (ImageView) mainView.findViewById(R.id.imageView);
        Button upvoteBtn = (Button) mainView.findViewById(R.id.upvoteButton);
        Button downvoteBtn = (Button) mainView.findViewById(R.id.downvoteButton);
        Button commentBtn = (Button) mainView.findViewById(R.id.addCommentButton);

        //Fill views
        emneTextView.setText(report.getEmne());
        elementTextView.setText(report.getElement());
        descriptionTextView.setText(report.getDescription());
        imageView.setImageBitmap(report.getImage());

        //Set button listeners
        upvoteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                report.setPoints(dbHelper.upvoteReport(report.getId(), report.getPoints()));
                Log.d(this.toString(), "Upvoted!");
            }
        });
        downvoteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                report.setPoints(dbHelper.downvoteReport(report.getId(), report.getPoints()));
                Log.d(this.toString(), "Downvoted!");
            }
        });
        commentBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Add Comment
            }
        });

        // Inflate the layout for this fragment
        return mainView;
    }


}
