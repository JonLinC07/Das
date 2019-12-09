package com.example.das.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Das_DB";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Model.SQL_CREATE_ALUMNOS);
        db.execSQL(Model.SQL_CREATE_DESTINOS);
        db.execSQL(Model.SQL_INSERT_DESTINOS);
        db.execSQL(Model.SQL_CREATE_SOLICITUDES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Model.SQL_DELETE_ALUMNOS);
        db.execSQL(Model.SQL_DELETE_DESTINOS);
        db.execSQL(Model.SQL_DELETE_SOLICITUDES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
