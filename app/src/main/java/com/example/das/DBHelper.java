package com.example.das;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Model.SQL_DELETE_ALUMNOS);
        db.execSQL(Model.SQL_DELETE_DESTINOS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList<String> getAllDestinos() {
        ArrayList<String> listDestinos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        db.beginTransaction();

        try {
            String selectQuery = "SELECT * FROM " + Model.Destinos.TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String city = cursor.getString(cursor.getColumnIndex(Model.Destinos.COLUMN_NAME_CIUDAD));
                    String country = cursor.getString(cursor.getColumnIndex(Model.Destinos.COLUMN_NAME_PAIS));
                    String allInfo = city + ", " + country;
                    listDestinos.add(allInfo);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return listDestinos;
    }
}
