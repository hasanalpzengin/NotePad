package com.example.hasalp.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by hasalp on 29.08.2017.
 */

public class DBCrud{

    private DBHelper dbhelper;
    private SQLiteDatabase db, dbw;

    public DBCrud(Context context) {
        dbhelper = new DBHelper(context);
        db = dbhelper.getReadableDatabase();
        dbw = dbhelper.getWritableDatabase();
    }

    public String[][] getAll(){
        Cursor cursor = db.rawQuery("SELECT * FROM "+ dbhelper.DATABASE_NAME, null);
        String[][] list = new String[cursor.getCount()][2];
        while(cursor.moveToNext()){
            list[cursor.getPosition()][0] = String.valueOf(cursor.getInt(0));
            list[cursor.getPosition()][1] = cursor.getString(1);
            Log.i("getValue", "SELECTED ID : "+list[cursor.getPosition()][0]);
        }
        return list;
    }

    public boolean insert(String title, String message){
        try{
            ContentValues values = new ContentValues();
            values.put("title", title);
            values.put("note", message);
            dbw.insert(dbhelper.DATABASE_NAME, null, values);
            Log.i("insertValue", "INSERTED NOTE = "+title);
            return true;
        }catch (SQLiteException e){
            Log.e("insertValue", "Error While Inserting Note");
            return false;
        }
    }

    public boolean remove(int id){
        try{
            dbw.delete(dbhelper.DATABASE_NAME, "id=="+id, null);
            Log.i("deleteValue", "DELETED ID: "+id);
            return true;
        }catch(SQLiteException e){
            Log.e("deleteValue", "Error While Deleting Note");
            return false;
        }
    }

    public void setNote(int id){
        String query = "SELECT * FROM "+dbhelper.DATABASE_NAME+" WHERE id="+id;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        SelectedNote.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        SelectedNote.setNote(cursor.getString(cursor.getColumnIndex("note")));

        ContentFragment.UpdateContent();

        cursor.close();
    }

    public void setEditNote(int id){
        String query = "SELECT * FROM "+dbhelper.DATABASE_NAME+" WHERE id="+id;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        EditNote.setId(cursor.getInt(cursor.getColumnIndex("id")));
        EditNote.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        EditNote.setNote(cursor.getString(cursor.getColumnIndex("note")));

        cursor.close();
    }

    public boolean update(int id, String title, String note){
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("note", note);

        try {
            db.update(dbhelper.DATABASE_NAME, contentValues, "id="+id,null);
        }catch (SQLiteException e) {
            return false;
        }
        //set Selected note
        setNote(id);

        Log.i("Update Worked","Update SQL worked");

        return true;
    }

    @Override
    protected void finalize() throws Throwable {
        db.close();
        dbw.close();
        super.finalize();
    }
}
