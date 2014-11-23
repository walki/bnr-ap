package com.blooot.android.criminalintent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by rjw on 11/17/2014.
 */
public class CrimeListFragment extends ListFragment {

    private static final String TAG = "CrimeListFragment";

    private ArrayList<Crime> mCrimes;
    private boolean mSubTitleVisible;

    private Button mNewCrimeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.crimes_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();

        CrimeAdapter adapter = new CrimeAdapter(mCrimes);

        setListAdapter(adapter);
        setRetainInstance(true);
        mSubTitleVisible = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crime_list, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if (mSubTitleVisible){
                ((ActionBarActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle);
            }
        }

        mNewCrimeButton = (Button) v.findViewById(R.id.new_crime_button);
        if (mNewCrimeButton != null){
            mNewCrimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startNewCrime();
                }
            });
        }

        return v;

    }


    private void startNewCrime(){
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
        startActivityForResult(i, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_new_crime:
                startNewCrime();
                return true;
            case R.id.menu_item_show_subtitle:
                if (((ActionBarActivity)getActivity()).getSupportActionBar().getSubtitle() == null){
                    ((ActionBarActivity)getActivity()).getSupportActionBar().setSubtitle(R.string.subtitle);
                    mSubTitleVisible = true;
                    item.setTitle(R.string.hide_subtitle);
                }else {
                    ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle(null);
                    mSubTitleVisible = false;
                    item.setTitle(R.string.show_subtitle);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem showSubTitle = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubTitleVisible && showSubTitle != null){
            showSubTitle.setTitle(R.string.hide_subtitle);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, c.getTitle() + " was clicked");

        //Start CrimePagerActivity
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        startActivity(i);
    }

    // Reload the List, changes might have occured in the CrimeFragment, when we are
    // backed back.
    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    private class CrimeAdapter extends ArrayAdapter<Crime> {
        public CrimeAdapter(ArrayList<Crime> crimes){
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we aren't provided a view, inflate one:
            if (convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_crime, null);
            }

            // And configure the view the Crime...
            Crime c = getItem(position);
            TextView titleTextView = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());

            TextView dateTextView = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(c.getFormattedDate());

            CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }
    }
}
