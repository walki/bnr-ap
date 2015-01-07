package com.blooot.android.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by rjw on 11/16/2014.
 */
public class Crime {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_SUSPECT = "suspect";

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    private String mSuspect;

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo photo) {
        mPhoto = photo;
    }

    private Photo mPhoto;

    public Date getDate() {
        return mDate;
    }

    public String getFormattedDate() {
//        String day = new SimpleDateFormat("EEEE").format(mDate);
//        String date =  DateFormat.getDateInstance().format(mDate);
//        String time =  DateFormat.getTimeInstance().format(mDate);
//        return day + ", " + date +  " " + time;
        return new SimpleDateFormat("EEEE, MMM d yyyy, h:mm a").format(mDate);
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


    public Crime(JSONObject jsonObject) throws JSONException {
        mId = UUID.fromString(jsonObject.getString(JSON_ID));
        if (jsonObject.has(JSON_TITLE)) {
            mTitle = jsonObject.getString(JSON_TITLE);
        }
        mSolved = jsonObject.getBoolean(JSON_SOLVED);
        mDate = new Date(jsonObject.getLong(JSON_DATE));
        if (jsonObject.has(JSON_PHOTO)) {
            mPhoto = new Photo(jsonObject.getJSONObject(JSON_PHOTO));
        }
        if (jsonObject.has(JSON_SUSPECT)) {
            mSuspect = jsonObject.getString(JSON_SUSPECT);
        }
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JSON_ID, mId.toString());
        jsonObject.put(JSON_TITLE, mTitle);
        jsonObject.put(JSON_SOLVED, mSolved);
        jsonObject.put(JSON_DATE, mDate.getTime());
        if (mPhoto != null) {
            jsonObject.put(JSON_PHOTO, mPhoto.toJSON());
        }
        jsonObject.put(JSON_SUSPECT, mSuspect);
        return jsonObject;
    }
}
