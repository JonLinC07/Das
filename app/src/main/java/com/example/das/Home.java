package com.example.das;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das.classes.DBHelper;
import com.example.das.classes.Model;

import org.w3c.dom.Text;

import java.security.Security;

public class Home extends AppCompatActivity {

    //Declaració de elementos visuales
    TextView viewWelcome;

    MediaPlayer click, welcome;

    //Declaración de elementos logicos
    Bundle extras;
    String currentName, currentExp;
    DBHelper DBHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Inicio");
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Inicialización de elementos visuales
        viewWelcome = findViewById(R.id._viewWelcome);

        click = MediaPlayer.create(this, R.raw.click);
        welcome = MediaPlayer.create(this, R.raw.welcome);

        //Inicialización de elementos logicos
        extras = getIntent().getExtras();
        DBHelper = new DBHelper(getApplicationContext());
        db = DBHelper.getReadableDatabase();

        currentName = extras.getString("USER_NAME");
        currentExp = extras.getString("USER_EXP");
        viewWelcome.setText("Bienvenido " + currentName);

        welcome.start();
    }

    public void GoToDestinos(View v) {
        click.start();
        Intent goToDestinos = new Intent(getApplicationContext(), Destinos.class);
        goToDestinos.putExtra("USER_NAME", currentName);
        goToDestinos.putExtra("USER_EXP", currentExp);
        startActivity(goToDestinos);
    }

    public void GoToProfile(View v) {
        click.start();
        Intent goToProfile = new Intent(getApplicationContext(), Profile.class);
        goToProfile.putExtra("USER_NAME", currentName);
        goToProfile.putExtra("USER_EXP", currentExp);
        startActivity(goToProfile);
    }

    /*
    public void sessionValidator(String exp, String pass) {
        String[] projection = { Model.Alumnos.COLUMN_NAME_EXPEDIENTE, Model.Alumnos.COLUMN_NAME_PASSWORD };
        String selection = Model.Alumnos.COLUMN_NAME_EXPEDIENTE + " = ? AND " +
                            Model.Alumnos.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = { exp, pass };
        Cursor fila = db.query(Model.Alumnos.TABLE_NAME, projection, selection, selectionArgs,
                                null, null, null);

        while (fila.moveToNext()) {
            String expediente = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_EXPEDIENTE));
            String password = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_PASSWORD));
            String name = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_NOMBRE));

        }
    }
     */

    //Guardando estado de la actividad
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("NAME", extras.getString("USER_NAME"));
        outState.putString("EXP", extras.getString("USER_EXP"));
    }

    //Reestableciendo el estado de la actividad
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currentExp = savedInstanceState.getString("EXP");
        viewWelcome.setText("Bievenido " + savedInstanceState.getString("NAME"));
    }

    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(getApplicationContext(), MainActivity.class);
        goBack.putExtra("USER_NAME", currentName);
        goBack.putExtra("USER_EXP", currentExp);
        startActivity(goBack);
    }
}
