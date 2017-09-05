package com.example.hasalp.notepad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hasalp on 29.08.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public final String DATABASE_NAME = "notes";

    public DBHelper(Context context) {
        super(context, "notes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE "+ DATABASE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, note TEXT);";
        Log.d("DBHelper", "SQL: "+sql);
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST "+DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}
