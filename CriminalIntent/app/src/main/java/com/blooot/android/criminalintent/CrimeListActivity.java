package com.blooot.android.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by rjw on 11/17/2014.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
