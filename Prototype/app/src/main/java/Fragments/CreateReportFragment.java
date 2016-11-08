package Fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import grpd.sm1sem.prototype.R;


public class CreateReportFragment extends Fragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_report, container, false);
    }


}
