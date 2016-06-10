/*
*
* This seems to be working although it could be cleared up quite a bit :D
*
 */

package com.grapevine.officecrimes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_TIME = "com.grapevine.officecrimes.date";
    private static final String ARG_TIME = "time";
    private TimePicker mTimePicker;

    // Used to create a new DatePickerFragment instead of calling the Constructor directly
    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    // Creates the time picker dialog
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_TIME);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);

        // Check if we have API 23 ()
        if(Build.VERSION.SDK_INT >= 23) {
            mTimePicker.setHour(hour);
            mTimePicker.setMinute(minute);
        }
        else {
            mTimePicker.setCurrentHour(hour);
            mTimePicker.setCurrentMinute(minute);
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Time of crime:")
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(Build.VERSION.SDK_INT >= 23) {
                                    int hour = mTimePicker.getHour();
                                    int minute = mTimePicker.getMinute();
                                    Log.d("TimePIckerINNER", "Hour: " + hour + "\nMinute: " + minute);
                                    Date date = createDate(hour, minute);
                                    sendResult(Activity.RESULT_OK, date);
                                }
                                else {
                                    int hour = mTimePicker.getCurrentHour();
                                    int minute = mTimePicker.getCurrentMinute();
                                    Log.d("TimePIckerINNER", "Hour: " + hour + "\nMinute: " + minute);
                                    Date date = createDate(hour, minute);
                                    sendResult(Activity.RESULT_OK, date);
                                }
                            }
                        })
                .create();
    }

    private Date createDate(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        Date date = calendar.getTime();
        return date;
    }

    private void sendResult(int resultCode, Date date) {
        if(getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
