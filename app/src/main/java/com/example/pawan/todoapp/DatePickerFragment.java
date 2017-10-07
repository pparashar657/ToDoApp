package com.example.pawan.todoapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.format.DateFormat;
import android.widget.DatePicker;

import java.util.Date;

/**
 * Created by Pawan on 29-09-2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    static String date;
    public void onDateSet(DatePicker view, int year, int month, int day) {
        date = (month+1) + " " + day + " " + year + " ";
    }
    public static String get(){
        if(date==null){
            return "";
        }
        return  date;
    }
}
