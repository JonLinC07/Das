package com.example.das;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das.classes.DBHelper;
import com.example.das.classes.Model;

public class Destinos extends AppCompatActivity {

    //Declaración de elementos visuales
    TextView viewDestino, viewUniversity;
    Spinner spinDestinos;
    WebView webInfo;

    //Declaración de elementos logicos
    com.example.das.classes.DBHelper DBHelper;
    SQLiteDatabase db;
    String[] arrayDestinos, projection, unam, uag, ubc, canaada;
    ArrayAdapter<String> adapter;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destinos);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Inicialización de elemenos visuales
        viewDestino = findViewById(R.id._viewDestino);
        viewUniversity = findViewById(R.id._viewUniversity);
        spinDestinos = findViewById(R.id._spinDestinos);
        webInfo = findViewById(R.id._webInfo);

        //Inicialización de elemtnos logicos
        DBHelper = new DBHelper(getApplicationContext());
        db = DBHelper.getReadableDatabase();
        arrayDestinos = new String[] {"Ciudad de Mexico", "Guadalajara", "Vancuver", "Toronto"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayDestinos);
        spinDestinos.setAdapter(adapter);
        location = "";
        projection = new String[] {Model.Destinos.COLUMN_NAME_CIUDAD, Model.Destinos.COLUMN_NAME_PAIS,
                                   Model.Destinos.COLUMN_NAME_UNIVERSIDAD, Model.Destinos.COLUMN_NAME_COORDENADAS,
                                   Model.Destinos.COLUMN_NAME_LINK};

        //getCityInfo();

        getToast(spinDestinos.getSelectedItem().toString());
    }

    private void getToast(String city) {
        Toast.makeText(getApplicationContext(), city, Toast.LENGTH_SHORT).show();
        setTitle("Destinos");
    }

    public void getCityInfo() {
        String place = "", university = "", url = "";
        String city = spinDestinos.getSelectedItem().toString();
        String selection = Model.Destinos.COLUMN_NAME_CIUDAD + " = ?";
        String[] selectionArgs = { city };

        Cursor cursor = db.query(Model.Destinos.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, null);

        while (cursor.moveToNext()) {
            place = cursor.getString(cursor.getColumnIndexOrThrow(Model.Destinos.COLUMN_NAME_CIUDAD)) +
                    ", " + cursor.getString(cursor.getColumnIndexOrThrow(Model.Destinos.COLUMN_NAME_PAIS));
            university = cursor.getString(cursor.getColumnIndexOrThrow(Model.Destinos.COLUMN_NAME_UNIVERSIDAD));
            url = cursor.getString(cursor.getColumnIndexOrThrow(Model.Destinos.COLUMN_NAME_LINK));
        }
        cursor.close();

        viewDestino.setText(place);
        viewUniversity.setText(university);
        webInfo.loadUrl(url);
        location = cursor.getString(cursor.getColumnIndexOrThrow(Model.Destinos.COLUMN_NAME_COORDENADAS));
    }


}
