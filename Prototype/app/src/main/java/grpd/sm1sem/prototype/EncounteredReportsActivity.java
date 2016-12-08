package grpd.sm1sem.prototype;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import Database.DatabaseHelper;
import Fragments.ReportFragment;
import Wrappers.ReportWrapper;

public class EncounteredReportsActivity extends FragmentActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encountered_reports);

        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Integer> encounteredReportIDs;

        public ScreenSlidePagerAdapter(FragmentManager fm, Context context) {

            super(fm);
            DatabaseHelper dbHelper = new DatabaseHelper(context);

            encounteredReportIDs = dbHelper.getAllIds();
        }

        @Override
        public Fragment getItem(int position) {
            return ReportFragment.newInstance(encounteredReportIDs.get(position));
        }

        @Override
        public int getCount() {
            return encounteredReportIDs.size();
        }
    }
}
