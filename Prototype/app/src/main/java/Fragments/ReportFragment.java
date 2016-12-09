package Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Database.Contract;
import Database.DatabaseHelper;
import Utitlities.ArrayAdapterSizeUtil;
import Utitlities.ImageUtils;
import Wrappers.CommentAdapter;
import Wrappers.CommentWrapper;
import Wrappers.ReportWrapper;
import grpd.sm1sem.prototype.AddCommentActivity;
import grpd.sm1sem.prototype.EncounteredReportsActivity;
import grpd.sm1sem.prototype.R;

public class ReportFragment extends Fragment {

    private static final String ARG_PARAM1 = "reportWrapper";
    public static final int REQUEST_COMMENT = 19823;
    private int reportID;
    private ReportWrapper report;
    private CommentAdapter commentAdapter;
    private ListView commentListview;

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
        final Button upvoteBtn = (Button) mainView.findViewById(R.id.upvoteButton);
        upvoteBtn.setBackgroundResource(R.color.colorDefaultButton);
        Button downvoteBtn = (Button) mainView.findViewById(R.id.downvoteButton);
        downvoteBtn.setBackgroundResource(R.color.colorDefaultButton);
        Button commentBtn = (Button) mainView.findViewById(R.id.addCommentButton);
        commentBtn.setBackgroundResource(R.color.colorDefaultButton);
        final Button scoreBtn = (Button) mainView.findViewById(R.id.scoreButton);

        if(report.getPoints() > 0) {
            upvoteBtn.setBackgroundResource(R.color.colorUpvoteSelected);
        }

        //Fill views
        emneTextView.setText(report.getEmne());
        elementTextView.setText(report.getElement());
        descriptionTextView.setText(report.getDescription());
        imageView.setImageBitmap(report.getImage());

        scoreBtn.setText(String.valueOf(report.getPoints()));

        //Set button listeners
        upvoteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                report.setPoints(dbHelper.upvoteReport(report.getId(), report.getPoints()));

                ((EncounteredReportsActivity) getActivity()).notiifyChange();
                Log.d(this.toString(), "Upvoted!");
            }
        });
        downvoteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
                report.setPoints(dbHelper.downvoteReport(report.getId(), report.getPoints()));

                ((EncounteredReportsActivity) getActivity()).notiifyChange();
                Log.d(this.toString(), "Downvoted!");
            }
        });
        commentBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getActivity(), AddCommentActivity.class);
                startIntent.putExtra(AddCommentActivity.EXTRAS_ID, reportID);
                startActivityForResult(startIntent, REQUEST_COMMENT);

            }
        });
        //Get comments
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        //Get comments and cast arraylist to array
        ArrayList<CommentWrapper> commentWrapperArrayList = dbHelper.getComments(report.getId());

        commentAdapter = new CommentAdapter(getActivity(), R.layout.comment_item, commentWrapperArrayList);
        commentListview = (ListView) mainView.findViewById(R.id.arrayAdapterListView);
        commentListview.setAdapter(commentAdapter);
        ArrayAdapterSizeUtil.getListViewSize(commentListview);
        return mainView;
    }

    private void updateComments(CommentWrapper commentWrapper) {
        CommentAdapter adapter = (CommentAdapter) commentListview.getAdapter();
        adapter.add(commentWrapper);
        adapter.notifyDataSetChanged();
        ArrayAdapterSizeUtil.getListViewSize(commentListview);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_COMMENT && resultCode == Activity.RESULT_OK) {
            int commentID = data.getIntExtra("id", 0);
            Log.d(this.toString(), "Id of comment: " + String.valueOf(commentID));
            //Get comment
            DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
            CommentWrapper commentWrapper = dbHelper.getComment(commentID);
            if(commentWrapper != null) {
                updateComments(commentWrapper);
            }
        }
    }

}
