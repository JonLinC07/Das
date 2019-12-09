package com.example.das;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    Button btnsetInfo;

    //Declaración de elementos logicos
    com.example.das.classes.DBHelper DBHelper;
    SQLiteDatabase db;
    String[] arrayDestinos, projection, unam, uag, ubc, canaada;
    ArrayAdapter<String> adapter;
    String location, currentName, currentExp;
    Bundle extras;

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
        btnsetInfo = findViewById(R.id._btnSetCityInfo);

        //Inicialización de elemtnos logicos
        DBHelper = new DBHelper(getApplicationContext());
        db = DBHelper.getReadableDatabase();

        try {
            extras = getIntent().getExtras();
            currentName = extras.getString("USER_NAME");
            currentExp = extras.getString("USER_EXP");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        webInfo.setWebViewClient(new WebViewClient());

        unam = new String[] { "Ciudad de Mexico", "México", "geo:19.332465,-99.1869765,17",
                "Universidad Nacional Autonoma de México (UNAM)",
                "https://es.wikipedia.org/wiki/Universidad_Nacional_Aut%C3%B3noma_de_M%C3%A9xico" };

        uag = new String[] { "Guadalajara", "Mexico", "geo:20.6947053,-103.4203198", "Universidad Autonoma de Guadlajara (UAG)",
                "https://es.wikipedia.org/wiki/Universidad_Aut%C3%B3noma_de_Guadalajara" };

        ubc = new String[] { "Vancuver", "Canada", "geo:49.2602794,-123.2298715", "University of British Columbia (UBC)",
                "https://es.wikipedia.org/wiki/Universidad_de_Columbia_Brit%C3%A1nica" };

        canaada = new String[] { "Toronto", "Canada", "geo:43.6628917,-79.3978504", "University of Canada",
                "https://es.wikipedia.org/wiki/Universidad_de_Toronto" };

        arrayDestinos = new String[] {"Ciudad de Mexico", "Guadalajara", "Vancuver", "Toronto"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayDestinos);
        spinDestinos.setAdapter(adapter);

        location = "";
        getCityInfo(spinDestinos.getSelectedItem().toString());

        btnsetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCityInfo(spinDestinos.getSelectedItem().toString());
            }
        });
    }

    public  void getCityInfo(String city) {

        if (city.matches("Ciudad de Mexico")) {
            //Mostrando ciudad y pais
            viewDestino.setText(unam[0] + ", " + unam[1]);
            //Mostrando universidad
            viewUniversity.setText(unam[3]);
            webInfo.loadUrl(unam[4]);

        } else if(city.matches("Guadalajara")) {
            //Mostrando ciudad y pais
            viewDestino.setText(uag[0] + ", " + uag[1]);
            //Mostrando universidad
            viewUniversity.setText(uag[3]);
            webInfo.loadUrl(uag[4]);

        } else if(city.matches("Vancuver")) {
            //Mostrando ciudad y pais
            viewDestino.setText(ubc[0] + ", " + ubc[1]);
            //Mostrando universidad
            viewUniversity.setText(ubc[3]);
            webInfo.loadUrl(ubc[4]);

        } else if(city.matches("Toronto")) {
            //Mostrando ciudad y pais
            viewDestino.setText(canaada[0] + ", " + canaada[1]);
            //Mostrando universidad
            viewUniversity.setText(canaada[3]);
            webInfo.loadUrl(canaada[4]);

        }
    }

    public void setLocation(View v) {
        if (spinDestinos.getSelectedItem().toString().matches("Ciudad de Mexico")) {
            setUNAMToMaps(spinDestinos.getSelectedItem().toString());

        } else if(spinDestinos.getSelectedItem().toString().matches("Guadalajara")) {
            setUAGToMaps(spinDestinos.getSelectedItem().toString());

        } else if(spinDestinos.getSelectedItem().toString().matches("Vancuver")) {
            setUBCToMaps(spinDestinos.getSelectedItem().toString());

        } else if(spinDestinos.getSelectedItem().toString().matches("Toronto")) {
            setCanadaToMaps(spinDestinos.getSelectedItem().toString());

        }
    }

    public void setUNAMToMaps(String geo) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(geo));
        Intent chooser = Intent.createChooser(intent, "Launch Maps");
        startActivity(chooser);
    }

    public void setUAGToMaps(String geo) {
        Intent waltHouse = new Intent(Intent.ACTION_VIEW);
        waltHouse.setData(Uri.parse(geo));
        Intent chooser = Intent.createChooser(waltHouse, "Launch Maps");
        startActivity(chooser);
    }

    public void setUBCToMaps(String geo) {
        Intent waltHouse = new Intent(Intent.ACTION_VIEW);
        waltHouse.setData(Uri.parse(geo));
        Intent chooser = Intent.createChooser(waltHouse, "Launch Maps");
        startActivity(chooser);
    }

    public void setCanadaToMaps(String geo) {
        Intent waltHouse = new Intent(Intent.ACTION_VIEW);
        waltHouse.setData(Uri.parse(geo));
        Intent chooser = Intent.createChooser(waltHouse, "Launch Maps");
        startActivity(chooser);
    }

    public void GoToSolDestinos(View v) {
        Intent goToSolDestinos = new Intent(getApplicationContext(), soldestinos.class);
        goToSolDestinos.putExtra("USER_NAME", currentName);
        goToSolDestinos.putExtra("USER_EXP", currentExp);
        startActivity(goToSolDestinos);
    }

    //Enviar información a la actividad anterior
    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(getApplicationContext(), Home.class);
        goBack.putExtra("USER_NAME", currentName);
        goBack.putExtra("USER_EXP", currentExp);
        startActivity(goBack);
    }
}
