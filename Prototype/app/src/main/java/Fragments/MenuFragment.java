package Fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
}
