package com.example.das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.Security;

public class Home extends AppCompatActivity {

    //Declaració de elementos visuales
    TextView viewWelcome;

    //Declaración de elementos logicos
    Bundle extras;
    String currentName, currentExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Inicialización de elementos visuales
        viewWelcome = findViewById(R.id._viewWelcome);

        //Inicialización de elementos logicos
        extras = getIntent().getExtras();

        try {
            currentName = extras.getString("USER_NAME");
            currentExp = extras.getString("USER_EXP");
            viewWelcome.setText("Bienvenido " + currentName);

        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Algo anda mal", Toast.LENGTH_SHORT).show();
        }
    }

    public void GoToDestinos(View v) {
        Intent goToDestinos = new Intent(getApplicationContext(), Destinos.class);
        goToDestinos.putExtra("USER_NAME", currentName);
        goToDestinos.putExtra("USER_EXP", currentExp);
        startActivity(goToDestinos);
    }

    public void GoToProfile(View v) {
        Intent goToProfile = new Intent(getApplicationContext(), Profile.class);
        goToProfile.putExtra("USER_NAME", currentName);
        goToProfile.putExtra("USER_EXP", currentExp);
        startActivity(goToProfile);
    }
}
