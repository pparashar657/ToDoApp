package com.example.pawan.todoapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;

/**
 * Created by Pawan on 29-09-2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    static String time=null;
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        time=hourOfDay+":"+minute+":00";
    }
    public static String get(){
        if(time==null){
            return "";
        }
        return time;
    }
}
