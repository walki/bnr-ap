package com.blooot.android.criminalintent;

import android.support.v4.app.Fragment;

import java.util.UUID;

// This is no longer being used.
// Normally, I would delete this, but I wanted to leave it around to
// remember how this was done.
public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);

        return CrimeFragment.newInstance(crimeId);
    }
}
