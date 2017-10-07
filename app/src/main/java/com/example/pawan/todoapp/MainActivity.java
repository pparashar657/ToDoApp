package com.example.pawan.todoapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {

    TextView Notodo;
    ListView listView;
    ArrayList<Todo> list;
    CustomAdaptor adapter;
    LocalBroadcastManager broadcastManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        broadcastManager=LocalBroadcastManager.getInstance(this);
        listView=(ListView)findViewById(R.id.list);
        Notodo=(TextView) findViewById(R.id.notodo);
        Notodo.setVisibility(View.GONE);
        list=new ArrayList<>();
        TodoOpenHelper openHelper=TodoOpenHelper.getInstance(getApplicationContext());
        SQLiteDatabase db=openHelper.getReadableDatabase();
        Cursor cursor=db.query(Contracts.Todo_Table_Name,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String tod=cursor.getString(cursor.getColumnIndex(Contracts.Todo_Name));
            String lim=cursor.getString(cursor.getColumnIndex(Contracts.Todo_Limit));
            Long id=cursor.getLong(cursor.getColumnIndex(Contracts.Todo_ID));
            Long time = cursor.getLong(cursor.getColumnIndex(Contracts.Todo_Time));
            Todo tem=new Todo(tod,lim,id,time);
            list.add(tem);
        }
        cursor.close();
        adapter=new CustomAdaptor(this, list, new CustomAdaptor.Deletebutton() {
            @Override
            public void delete(final int position, View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete ToDo");
                builder.setMessage("Are you sure ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(list.size()>0) {
                            TodoOpenHelper openHelper = TodoOpenHelper.getInstance(getApplicationContext());
                            SQLiteDatabase db = openHelper.getWritableDatabase();
                            Long id = list.get(list.size() - 1).getId();
                            db.delete(Contracts.Todo_Table_Name, Contracts.Todo_ID + " = " + id, null);
                            db.close();
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                            if(list.size()==0){
                                Notodo.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                });
                builder.setNegativeButton("No",null);
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        listView.setAdapter(adapter);
        if(list.size()==0){
            Notodo.setVisibility(View.VISIBLE);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Todo t=list.get(i);
                Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("title",t.getTodo());
                intent.putExtra("details",t.getDetails());
                if(t.getTime()!=0) {
                    SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy HH:mm:ss");
                    String time = df.format(new Date(t.getTime()));
                    intent.putExtra("time", time);
                }
                else{
                    intent.putExtra("time","");
                }
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Todo to=list.get(i);
                Intent intent =new Intent(MainActivity.this,ModifyActivity.class);
                intent.putExtra(Constants.TODO,to);
                intent.putExtra(Constants.POSITION_KEY,i);
                startActivityForResult(intent,1);
                list.remove(i);
                return true;
            }
        });
    }
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter= new IntentFilter("my_action");
        broadcastManager.registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onStop() {
        broadcastManager.unregisterReceiver(broadcastReceiver);
        super.onStop();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.browse){
            Intent browse=new Intent();
            browse.setAction(Intent.ACTION_VIEW);
            String url="http://google.com";
            browse.setData(Uri.parse(url));
            startActivity(browse);
        }
        else if(id==R.id.mail){
            Intent mail=new Intent();
            mail.setAction(Intent.ACTION_SENDTO);
            mail.setData(Uri.parse("mailto:pparashar657@gmail.com"));
            mail.putExtra(Intent.EXTRA_SUBJECT,"HEllo World");
            if(mail.resolveActivity(getPackageManager())!=null){
                startActivity(mail);
            }
        }
        else if(id==R.id.call){
            Intent call=new Intent();
            call.setAction(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:8010358736"));
            if(call.resolveActivity(getPackageManager())!=null){
                startActivity(call);
            }
        }
        else if(id==R.id.deleteall){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Delete ToDo");
            builder.setMessage("Are you sure ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(list.size()>0) {
                        TodoOpenHelper openHelper = TodoOpenHelper.getInstance(getApplicationContext());
                        SQLiteDatabase db = openHelper.getWritableDatabase();
                        db.delete(Contracts.Todo_Table_Name, null, null);
                        db.close();
                        list.clear();
                        adapter.notifyDataSetChanged();
                        if(list.size()==0){
                            Notodo.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
            builder.setNegativeButton("No",null);
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        if(id==R.id.add){
            Intent intent =new Intent(MainActivity.this,Add_Activity.class);
            startActivityForResult(intent,1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode==1){
            Todo temp=list.get(data.getIntExtra(Constants.POSITION_KEY,0));
            Todo t=(Todo) data.getSerializableExtra(Constants.TODO);
            temp.setDetails(t.getDetails());
            temp.setTodo(t.getTodo());
            adapter.notifyDataSetChanged();
        }
        if(requestCode==1&&resultCode==2){
            Todo temp=(Todo) data.getSerializableExtra(Constants.TODO);
            list.add(temp);
            adapter.notifyDataSetChanged();
            if(list.size()!=0){
                Notodo.setVisibility(View.GONE);
            }
        }
    }
}
