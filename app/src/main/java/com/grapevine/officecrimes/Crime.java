package com.grapevine.officecrimes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    Calendar calendar = GregorianCalendar.getInstance();
    private boolean mSolved;

    public Crime() {
        // Generate unique identifier
        mId = UUID.randomUUID();
        // Sets date to the current date
        mDate = new Date();
        calendar.setTime(mDate);
    }

    // Returns the formatted date
    public String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    // Returns the formatted time
    public String formatTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm");
        return sdf.format(date);
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    // TODO -> these are for testing only at the moment
    // Get the time
    public int getHourOfDay() {
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getMinutes() {
        return calendar.get(Calendar.MINUTE);
    }
}
