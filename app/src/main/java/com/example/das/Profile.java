package com.example.das;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.das.classes.DBHelper;
import com.example.das.classes.Model;

import java.io.File;

public class Profile extends AppCompatActivity {

    //Declaración de elementos visuales
    TextView viewName, viewExp, viewLic, viewEmail, viewPhone, viewCredits, viewProm, viewCampus;
    ImageView profilePhoto;

    //Declaración de elementos logicos
    DBHelper DBHelper;
    SQLiteDatabase db;
    Bundle extras;
    String name = "", exp = "", lic = "", email = "", phone = "", credits = "", prom = "", campus = "";
    //String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Perfil");

        //Inicialización de elementos visuales
        viewName = findViewById(R.id._viewName);
        viewExp = findViewById(R.id._viewExp);
        viewLic = findViewById(R.id._viewLic);
        viewEmail = findViewById(R.id._viewEmail);
        viewPhone = findViewById(R.id._viewPhone);
        viewCredits = findViewById(R.id._viewCredits);
        viewProm = findViewById(R.id._viewProm);
        viewCampus = findViewById(R.id._viewCampus);
        profilePhoto = findViewById(R.id._viewPhoto);

        //Inicialización de elementos logicos
        DBHelper = new DBHelper(getApplicationContext());
        db = DBHelper.getReadableDatabase();


        try {
            extras = getIntent().getExtras();
            name = extras.getString("USER_NAME");
            exp = extras.getString("USER_EXP");
            //photoPath = extras.getString("PATH");
            getProfileInfo(exp);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Algo anda mal", Toast.LENGTH_SHORT).show();
        }
    }

    public void getProfileInfo(String expediente) {
        String[] projection = { Model.Alumnos.COLUMN_NAME_LIC, Model.Alumnos.COLUMN_NAME_EMAIL,
                                Model.Alumnos.COLUMN_NAME_PHONE, Model.Alumnos.COLUMN_NAME_PROMEDIO,
                                Model.Alumnos.COLUMN_NAME_CREDITOS, Model.Alumnos.COLUMN_NAME_CAMPUS };
        String selection = Model.Alumnos.COLUMN_NAME_EXPEDIENTE + " = ?";
        String[] selectionArgs = { expediente };
        Cursor fila = db.query(Model.Alumnos.TABLE_NAME, projection, selection, selectionArgs,
                                null, null, null);

        while (fila.moveToNext()) {
            lic = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_LIC));
            email = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_EMAIL));
            phone = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_PHONE));
            credits = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_CREDITOS));
            prom = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_PROMEDIO));
            campus = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_CAMPUS));
        }
        fila.close();

        viewName.setText(name);
        viewExp.setText("Expediente: " + expediente);
        viewLic.setText("Licenciatura: " + lic);
        viewEmail.setText("Correo: " + email);
        viewPhone.setText("Numero de Telefono: " + phone);
        viewCredits.setText("Creditos Aprobados: " + credits);
        viewProm.setText("Promedio: " + prom);
        viewCampus.setText("Campus: " + campus);

/*
        File imageFile = new File(photoPath);

        if (imageFile.exists()) {
            Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            profilePhoto.setImageBitmap(image);
        }
*/
        if (credits == null || prom == null || campus == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Perfil Incompleto")
                    .setMessage("Actualmente tu perfil no esta completo, necesitaras tener tu perfil" +
                            "completo para solicitar un intercambio. ¿Deseas terminar de llenarlo?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent goToEdit = new Intent(getApplicationContext(), EditProfile.class);
                            goToEdit.putExtra("USER_NAME", name);
                            goToEdit.putExtra("USER_EXP", exp);
                            goToEdit.putExtra("LIC", lic);
                            goToEdit.putExtra("EMAIL", email);
                            goToEdit.putExtra("PHONE", phone);
                            goToEdit.putExtra("CREDITS", credits);
                            goToEdit.putExtra("PROM", prom);
                            //goToEdit.putExtra("PHOTO_PATH", photoPath);
                            startActivity(goToEdit);
                        }
                    })
                    .setNegativeButton("NO", null).show();
        }
    }

    //Envia y direcciona a la actividad para editar el perfil
    public void GoToEdit(View v) {
        Intent goToEdit = new Intent(getApplicationContext(), EditProfile.class);
        goToEdit.putExtra("USER_NAME", name);
        goToEdit.putExtra("USER_EXP", exp);
        goToEdit.putExtra("LIC", lic);
        goToEdit.putExtra("EMAIL", email);
        goToEdit.putExtra("PHONE", phone);
        goToEdit.putExtra("CREDITS", credits);
        goToEdit.putExtra("PROM", prom);
        //goToEdit.putExtra("PHOTO_PATH", photoPath);
        startActivity(goToEdit);
    }


    //Enviar información a la actividad anterior
    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(getApplicationContext(), Home.class);
        goBack.putExtra("USER_NAME", viewName.getText().toString());
        goBack.putExtra("USER_EXP", exp);
        startActivity(goBack);
    }
}
