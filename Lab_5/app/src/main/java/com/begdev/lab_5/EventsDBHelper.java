package com.begdev.lab_5;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.versionedparcelable.VersionedParcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventsDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Lab7_DB.db";
    private static final int DB_VERSION = 1;
    public Cursor viewCursor = this.getWritableDatabase().rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME, null);


    public EventsDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DBContract.SQL_CREATE_ENTIRE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DBContract.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public boolean addEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.DBEntry.COLUMN_NAME_TITLE, event.title);
        values.put(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION, event.description);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        values.put(DBContract.DBEntry.COLUMN_NAME_DATE, sdf.format(event.date));
        values.put(DBContract.DBEntry.COLUMN_NAME_IMAGE, event.image);
        values.put(DBContract.DBEntry.COLUMN_NAME_IS_CHECKED, event.isChecked);
//        long rowId = db.insert(DBContract.DBEntry.TABLE_NAME, null, values);
        return db.insert(DBContract.DBEntry.TABLE_NAME, null, values) != -1;
    }

    public boolean editEvent(@NonNull Integer id, @NonNull Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBContract.DBEntry.COLUMN_NAME_TITLE, event.title);
        values.put(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION, event.description);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        values.put(DBContract.DBEntry.COLUMN_NAME_DATE, sdf.format(event.date));
        values.put(DBContract.DBEntry.COLUMN_NAME_IMAGE, event.image);
        values.put(DBContract.DBEntry.COLUMN_NAME_IS_CHECKED, event.isChecked);
        return db.update(DBContract.DBEntry.TABLE_NAME, values, "_id == ?", new String[]{id.toString()}) != 0;

    }

    public boolean deleteEvent(@NonNull Integer id) throws SQLiteConstraintException {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DBContract.DBEntry.TABLE_NAME, "_id == ?", new String[]{id.toString()}) != 0;
    }

    public Cursor getAllEvents() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + DBContract.DBEntry.TABLE_NAME, null);
    }
    public Event getEventByID(int id) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DBContract.DBEntry.TABLE_NAME, null, DBContract.DBEntry.COLUMN_NAME_ID + " == " + id, null, null, null, null);
        if(!cursor.moveToFirst()){
            return null;
        }
        String title = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_TITLE));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DESCRIPTION));

        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_DATE)));
        String image =  cursor.getString(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_IMAGE));
        int isChecked =  cursor.getInt(cursor.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_IS_CHECKED));
        return new Event(title, description, date, image ,isChecked!=0);
    }


}
