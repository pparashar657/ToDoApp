package com.example.pawan.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pawan on 16-09-2017.
 */

public class TodoOpenHelper extends SQLiteOpenHelper{

    private static TodoOpenHelper instance;

    public static TodoOpenHelper getInstance(Context context) {
       if(instance==null){
           instance= new TodoOpenHelper(context);
       }
        return instance;

    }
    private TodoOpenHelper(Context context) {
        super(context, "Todo_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + Contracts.Todo_Table_Name + " (" +
                Contracts.Todo_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Contracts.Todo_Name + " TEXT," +
                Contracts.Todo_Time + " INTEGER," +
                Contracts.Todo_Limit + " TEXT )";
        sqLiteDatabase.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
