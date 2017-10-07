package com.example.pawan.todoapp;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Add_Activity extends FragmentActivity  {

    EditText todoname;
    EditText todolimit;
    int position;
    String newTodo;
    String newLimit;
    Button button;
    String t;
    Long time;
    int Id;
    static boolean istime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addactivity);
        todoname=(EditText)findViewById(R.id.todoname);
        todolimit=(EditText)findViewById(R.id.todolimit);
        button=(Button)findViewById(R.id.delete);
        istime=false;
    }
    public void Buttonclicked(View view) throws ParseException {
        newTodo=todoname.getEditableText().toString();
        newLimit=todolimit.getEditableText().toString();
        TodoOpenHelper openHelper = TodoOpenHelper.getInstance(getApplicationContext());
        SQLiteDatabase db=openHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contracts.Todo_Name,newTodo);
        contentValues.put(Contracts.Todo_Time,time);
        contentValues.put(Contracts.Todo_Limit,newLimit);
        Long id=db.insert(Contracts.Todo_Table_Name,null,contentValues);
        Id= (int) (id*1);
        if(istime){
            t=DatePickerFragment.get()+" ";
            t+=TimePickerFragment.get();
            SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy HH:mm:ss");
            Date date = df.parse(t);
            time = date.getTime();
            sendBroadcast(new Intent());
        }
        else{
            time= Long.valueOf(0);
        }
        if(id==-1){
            Toast.makeText(this, "Error in adding values", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i=new Intent();
            Todo t=new Todo(newTodo,newLimit,id,time);
            i.putExtra(Constants.TODO,t);
            setResult(2,i);
        }
        db.close();
        finish();
    }

    @Override
    public void sendBroadcast(Intent intent) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent1=new Intent(this,AlarmReciever.class);
        intent1.putExtra(Constants.TODO,newTodo);
        intent1.putExtra(Constants.LIMIT,newLimit);
        intent1.putExtra(Constants.POSITION_KEY,Id);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this,Id,intent1,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
        super.sendBroadcast(intent);
    }

    public void showTimePickerDialog(View v) {
        istime=true;
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"Pick a Time");
        DialogFragment newFragment1 = new DatePickerFragment();
        newFragment1.show(getFragmentManager(), "Pick a Date");
    }

}
