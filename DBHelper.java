package com.example.test8;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context,"song_db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSQL = "create table song(_id integer primary key,genre text,title text,content text)";
        try{
            db.execSQL(createSQL);
        }catch (Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropSQL = "drop table song";
        db.execSQL(dropSQL);
        onCreate(db);
    }
}
