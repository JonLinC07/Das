package com.example.das;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das.classes.DBHelper;
import com.example.das.classes.Model;

public class EditProfile extends AppCompatActivity {

    //Declaración de elementos visuales
    EditText inputName, inputEmail, inputPhone, inputLic, inputCredits;
    TextView viewExp, viewProm;
    SeekBar seekProm;
    Spinner spinCampus;
    ImageView viewPhoto;

    //Declaración de elementos logicos
    DBHelper DBHelper;
    SQLiteDatabase db;
    Bundle extras;
    String[] arrayCampus;
    ArrayAdapter<String> adapter;
    ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Inicialización de elementos visiales
        inputName = findViewById(R.id._inputName);
        inputEmail = findViewById(R.id._inputEmail);
        inputPhone = findViewById(R.id._inputPhone);
        inputLic = findViewById(R.id._inputLic);
        inputCredits = findViewById(R.id._inputCredits);
        viewExp = findViewById(R.id._viewExp);
        viewProm = findViewById(R.id._viewProm);
        seekProm = findViewById(R.id._seekProm);
        spinCampus = findViewById(R.id._spinCampus);
        viewPhoto = findViewById(R.id._viewPhoto);

        //Inicialización de elementos logicos
        DBHelper = new DBHelper(getApplicationContext());
        db = DBHelper.getReadableDatabase();
        extras = getIntent().getExtras();
        arrayCampus = new String[] {"Hermosullo", "Nogales", "Ciudad Obregon", "Caborca", "Santa ana"};
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayCampus);

        //Acomodando la información
        spinCampus.setAdapter(adapter);
        inputName.setText(extras.getString("USER_NAME"));
        inputEmail.setText(extras.getString("EMAIL"));
        inputPhone.setText(extras.getString("PHONE"));
        inputLic.setText(extras.getString("LIC"));
        inputCredits.setText(extras.getString("CREDITS"));
        viewExp.setText(extras.getString("USER_EXP"));
        viewProm.setText(extras.getString("PROM"));

        if (viewProm.getText().toString().matches("")) {
            viewProm.setText("Promedio");
        }

        seekProm.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float prom = ((float)progress / (float)10.0);
                viewProm.setText(String.valueOf(prom));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void sendUpdateUser(View v) {
        values = new ContentValues();
        String  name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String phone = inputPhone.getText().toString();
        String lic = inputLic.getText().toString();
        String credits = inputCredits.getText().toString();
        String prom = viewProm.getText().toString();
        String campus = spinCampus.getSelectedItem().toString();

        values.put(Model.Alumnos.COLUMN_NAME_NOMBRE, name);
        values.put(Model.Alumnos.COLUMN_NAME_EMAIL, email);
        values.put(Model.Alumnos.COLUMN_NAME_PHONE, phone);
        values.put(Model.Alumnos.COLUMN_NAME_LIC, lic);
        values.put(Model.Alumnos.COLUMN_NAME_CREDITOS, credits);
        values.put(Model.Alumnos.COLUMN_NAME_PROMEDIO, prom);
        values.put(Model.Alumnos.COLUMN_NAME_CAMPUS, campus);

        long newRowID = db.update(Model.Alumnos.TABLE_NAME, values,
                    Model.Alumnos.COLUMN_NAME_EXPEDIENTE + " = " +
                               viewExp.getText().toString(), null);

        if (newRowID == 1) {
            new AlertDialog.Builder(this)
                    .setTitle("Actualización exitosa")
                    .setMessage("Tu información ha sido actualizada con exito")
                    .setPositiveButton("Ver Perfil", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent goToProfile = new Intent(getApplicationContext(), Profile.class);
                            goToProfile.putExtra("USER_NAME", inputName.getText().toString());
                            goToProfile.putExtra("USER_EXP", viewExp.getText().toString());
                            goToProfile.putExtra("LIC", inputLic.getText().toString());
                            goToProfile.putExtra("EMAIL", inputEmail.getText().toString());
                            goToProfile.putExtra("PHONE", inputPhone.getText().toString());
                            goToProfile.putExtra("CREDITS", inputCredits.getText().toString());
                            goToProfile.putExtra("PROM", viewProm.getText().toString());
                            goToProfile.putExtra("CAMPUS", spinCampus.getSelectedItem().toString());
                            startActivity(goToProfile);
                        }
                    }).show();
        } else {
            Toast.makeText(this, "" + newRowID, Toast.LENGTH_SHORT).show();
        }

    }
}
