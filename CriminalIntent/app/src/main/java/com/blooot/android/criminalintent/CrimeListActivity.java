package com.blooot.android.criminalintent;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by rjw on 11/17/2014.
 */
public class CrimeListActivity extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {

        if(findViewById(R.id.detailFragmentContainer) == null){
            // This is the phone layout
            // So we'll start an Instance of CrimePagerActivity
            Intent i = new Intent(this, CrimePagerActivity.class);
            i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
            startActivity(i);
        }else{
            // The tablet layout

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            if (oldDetail != null){
                ft.remove(oldDetail);
            }
            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        FragmentManager fm = getSupportFragmentManager();
        CrimeListFragment listFragment = (CrimeListFragment)fm.findFragmentById(R.id.fragmentContainer);
        listFragment.updateUI();
    }
}
