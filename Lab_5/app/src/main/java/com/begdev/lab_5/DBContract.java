package com.begdev.lab_5;

import android.provider.BaseColumns;

public final class DBContract {
    public DBContract(){};

    public static abstract class DBEntry implements BaseColumns{
        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_ID= "_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_IS_CHECKED = "is_checked";
        public static final String COLUMN_NAME_IMAGE = "image";

    }

    public static String SQL_CREATE_ENTIRE = "CREATE TABLE "+DBEntry.TABLE_NAME+"("+
            DBEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            DBEntry.COLUMN_NAME_TITLE+ " TEXT,"+
            DBEntry.COLUMN_NAME_DESCRIPTION+" TEXT,"+
            DBEntry.COLUMN_NAME_DATE+" TEXT,"+
            DBEntry.COLUMN_NAME_IMAGE+" TEXT,"+
            DBEntry.COLUMN_NAME_IS_CHECKED+" INTEGER NOT NULL CHECK ("+DBEntry.COLUMN_NAME_IS_CHECKED+" IN (0, 1))"+")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXIST "+ DBEntry.TABLE_NAME;

}
