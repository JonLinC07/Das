package com.example.das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //Declaración de elementos logicos
    DBHelper DBHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicilización de elementos logicos
        DBHelper = new DBHelper(getApplicationContext());
        db = DBHelper.getWritableDatabase();
    }

    public void GoToSignUp(View v) {
        Intent goToSignUp = new Intent(getApplicationContext(), SignUp.class);
        startActivity(goToSignUp);
    }
}
