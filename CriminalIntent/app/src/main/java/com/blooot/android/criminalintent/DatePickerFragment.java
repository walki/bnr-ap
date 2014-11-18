package com.blooot.android.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

/**
 * Created by rjw on 11/18/2014.
 */
public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.data_picker_title)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
