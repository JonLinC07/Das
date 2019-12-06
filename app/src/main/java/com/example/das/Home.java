package com.example.das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    //Declaració de elementos visuales


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void GoToDestinos(View v) {
        Intent goToFestinos = new Intent(getApplicationContext(), Destinos.class);
        startActivity(goToFestinos);
    }
}
