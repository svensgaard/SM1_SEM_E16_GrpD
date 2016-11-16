package Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Database.Contract;
import Utitlities.ImageUtils;
import Wrappers.ReportWrapper;
import grpd.sm1sem.prototype.R;

public class ReportFragment extends Fragment {

    private static final String ARG_PARAM1 = "reportWrapper";

    private int reportID;
    private String reportEmne;
    private String reportElement;
    private String reportDescription;
    private Bitmap reportImage;

    public ReportFragment() {
        // Required empty public constructor
    }

    /* Use this method to get an instance of the fragement! */
    public static ReportFragment newInstance(ReportWrapper reportWrapper) {
        ReportFragment fragment = new ReportFragment();

        Bundle args = new Bundle();
        args.putInt("ID", reportWrapper.getId());
        args.putString("EMNE", reportWrapper.getEmne());
        args.putString("ELEMENT", reportWrapper.getElement());
        args.putString("DESCRIPTION", reportWrapper.getDescription());
        args.putByteArray("IMAGE", ImageUtils.getBitmapAsByteArray(reportWrapper.getImage()));

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportID = getArguments().getInt("ID");
        reportEmne = getArguments().getString("EMNE");
        reportElement = getArguments().getString("ELEMENT");
        reportDescription = getArguments().getString("DESCRIPTION");
        reportImage = ImageUtils.getByteArrayAsBitmap(getArguments().getByteArray("IMAGE"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_report, container, false);
        // Inflate the layout for this fragment
        return mainView;
    }


}
