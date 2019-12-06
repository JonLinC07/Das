package com.example.das;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Display;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Destinos extends AppCompatActivity {

    //Declaración de elementos visuales
    TextView destino, university;
    Spinner spinDestinos;

    //Declaración de elementos logicos
    DBHelper DBHelper;
    SQLiteDatabase db;
    ArrayList<String> listDestinos, destinosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinos);

        //Inicialización de elemenos visuales
        destino = findViewById(R.id._viewDestino);
        university = findViewById(R.id._viewUniversity);
        spinDestinos = findViewById(R.id._spinDestinos);

        //Inicialización de elemtnos logicos
        DBHelper = new DBHelper(getApplicationContext());

        getDestinos();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listDestinos);
        spinDestinos.setAdapter(adapter);
    }

    public void getDestinos() {
        db = DBHelper.getReadableDatabase();

        Model.Destinos newDestino = null;
        destinosList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Model.Destinos.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            String city = cursor.getString(cursor.getColumnIndexOrThrow(Model.Destinos.COLUMN_NAME_CIUDAD));
            String country = cursor.getString(cursor.getColumnIndexOrThrow(Model.Destinos.COLUMN_NAME_PAIS));
            String all = city + ", " + country;

            destinosList.add(all);
        }
        getList();
    }

    public void getList() {
        listDestinos = new ArrayList<String>();
        listDestinos.add("Seleccionar");

        for (int i=0; i<destinosList.size(); i++) {
            listDestinos.add(destinosList.get(i));
        }
    }



}
