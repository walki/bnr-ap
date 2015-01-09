package com.blooot.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
    private Callbacks mCallbacks;

    /**
     * Required interface for hosting activities
     */
    public interface Callbacks{
        void onCrimeSelected(Crime crime);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public void updateUI(){
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

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

        ListView listView = (ListView)v.findViewById(android.R.id.list);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // Use floating context menus on Froyo and Gingerbread
            registerForContextMenu(listView);
        }else{
            // Use contextual action bar on HoneyComb and higher
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                    // req'd but unused here
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.crime_list_item_context, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                    // req'd but unused here
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch(item.getItemId()){
                        case R.id.menu_item_delete_crime:
                            CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
                            CrimeLab crimeLab = CrimeLab.get(getActivity());

                            for (int i = adapter.getCount()-1; i >= 0; i--){
                                if (getListView().isItemChecked(i)){
                                    crimeLab.deleteCrime(adapter.getItem(i));
                                }
                            }
                            mode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    // req'd but unused here
                }
            });
        }

        return v;

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
        Crime crime = adapter.getItem(position);

        switch (item.getItemId()){
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).deleteCrime(crime);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, c.getTitle() + " was clicked");

        mCallbacks.onCrimeSelected(c);
    }

    // Reload the List, changes might have occured in the CrimeFragment, when we are
    // backed back.
    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    private void startNewCrime(){
        Crime crime = new Crime();
        CrimeLab.get(getActivity()).addCrime(crime);
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
        mCallbacks.onCrimeSelected(crime);

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
