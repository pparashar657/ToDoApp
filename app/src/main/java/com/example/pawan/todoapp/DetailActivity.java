package com.example.pawan.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView title,details,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        title=(TextView)findViewById(R.id.todo_title);
        details=(TextView)findViewById(R.id.todo_details);
        time=(TextView)findViewById(R.id.todo_time);
        Intent i=getIntent();
        title.setText(i.getStringExtra("title"));
        details.setText(i.getStringExtra("details"));
        time.setText(i.getStringExtra("time"));
    }
}
