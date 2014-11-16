package com.blooot.android.criminalintent;

import java.util.UUID;

/**
 * Created by rjw on 11/16/2014.
 */
public class Crime {

    public UUID getId() {
        return mId;
    }

    private UUID mId;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    private String mTitle;

    public Crime() {
        // Generate unique identifier for the Crime!
        mId = UUID.randomUUID();
    }

}
