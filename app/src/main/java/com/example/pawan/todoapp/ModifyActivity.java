package com.example.pawan.todoapp;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyActivity extends AppCompatActivity implements Serializable {

    EditText todoname;
    EditText todolimit;
    int position;
    int Id;
    String t1;
    String newTodo;
    String newLimit;
    Long time;
    Todo t;
    static boolean istime;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        istime=false;
        Intent intent=getIntent();
        Todo t=(Todo) intent.getSerializableExtra(Constants.TODO);
        position=intent.getIntExtra(Constants.POSITION_KEY,0);
        todoname=(EditText)findViewById(R.id.todoname);
        todolimit=(EditText)findViewById(R.id.todolimit);
        todoname.setText(t.getTodo());
        todolimit.setText(t.getDetails());
    }
    public void Buttonclicked(View view) throws ParseException {
        TodoOpenHelper openHelper = TodoOpenHelper.getInstance(getApplicationContext());
        SQLiteDatabase db=openHelper.getWritableDatabase();
        newTodo=todoname.getEditableText().toString();
        newLimit=todolimit.getEditableText().toString();
        if(istime){
            t1=DatePickerFragment.get()+" ";
            t1+=TimePickerFragment.get();
            SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy HH:mm:ss");
            Date date = null;
            try {
                date = df.parse(t1);
            } catch (ParseException e) {
                Toast.makeText(this,"Error in parsing time", Toast.LENGTH_SHORT).show();;
            }
            time = date.getTime();
            sendBroadcast(new Intent());
        }
        else{
            time= Long.valueOf(0);
        }
        ContentValues contentValues=new ContentValues();
        contentValues.put(Contracts.Todo_Name,newTodo);
        contentValues.put(Contracts.Todo_Time,time);
        contentValues.put(Contracts.Todo_Limit,newLimit);
        Long id=db.insert(Contracts.Todo_Table_Name,null,contentValues);
        Id= (int) (id*1);
        if(id==-1){
            Toast.makeText(this, "Error in adding values", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i=new Intent();
            Todo t1=new Todo(newTodo,newLimit,id,time);
            i.putExtra(Constants.TODO,t1);
            i.putExtra(Constants.POSITION_KEY,position);
            setResult(1,i);
        }
        db.close();
        finish();
    }


    @Override
    public void sendBroadcast(Intent intent) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent1= new Intent(this,AlarmReciever.class);
        intent1.putExtra(Constants.TODO,newTodo);
        intent1.putExtra(Constants.LIMIT,newLimit);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this,Id,intent1,0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
        super.sendBroadcast(intent);
    }

    public void showTimePickerDialog(View v) {
        istime= true;
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(),"Pick a Time");
        DialogFragment newFragment1 = new DatePickerFragment();
        newFragment1.show(getFragmentManager(), "Pick A Date");

    }
}
