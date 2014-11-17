package com.blooot.android.criminalintent;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;

/**
 * Created by rjw on 11/17/2014.
 */
public class CrimeListFragment extends ListFragment {

    private ArrayList<Crime> mCrimes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);
    }
}
