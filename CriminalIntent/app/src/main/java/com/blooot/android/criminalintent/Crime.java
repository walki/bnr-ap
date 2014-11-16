package com.blooot.android.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rjw on 11/16/2014.
 */
public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }


    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public Crime() {
        // Generate unique identifier for the Crime!
        mId = UUID.randomUUID();
        mDate = new Date();
    }

}
