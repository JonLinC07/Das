package com.example.das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das.classes.DBHelper;
import com.example.das.classes.Model;

public class soldestinos extends AppCompatActivity {

    EditText et_nombre, et_expediente, et_universidad, et_campus, et_edad, et_genero, et_licenciatura, et_telefono, et_promedio;
    TextView tv_pais;
    SeekBar seek;
    ImageView imgPais;

    //Declaración de elementos logicos
    DBHelper DBHelper;
    SQLiteDatabase db;
    ContentValues values;
    RadioButton radioBF;
    RadioButton radioBM;
    RadioGroup radioG;
    String selectedR, currentName, currentExp, campus = "", credits = "", prom = "", phone = "", email = "", lic = "";
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soldestinos);

        //Inicialización de elementos logicos
        et_nombre = findViewById(R.id.et_nombre);
        et_expediente = findViewById(R.id.et_expediente);
        et_universidad= findViewById(R.id.et_universidad);
        et_campus = findViewById(R.id.et_campus);
        et_edad = findViewById(R.id.et_edad);
        radioBF = findViewById(R.id.radioBF);
        radioBM = findViewById(R.id.radioBM);
        et_licenciatura = findViewById(R.id.et_licenciatura);
        et_telefono = findViewById(R.id.et_telefono);
        et_promedio= findViewById(R.id.et_promedio);
        tv_pais = findViewById(R.id.tv_pais);
        seek = findViewById(R.id.seekBar);
        imgPais = findViewById(R.id.imageView2);
        radioG = findViewById(R.id.radioG);

        //Inicilización de elementos logicos
        DBHelper = new DBHelper(getApplicationContext());
        db = DBHelper.getWritableDatabase();
        values = new ContentValues();

        seek.setProgress(1);
        seek.setMax(4);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i == 1){
                    tv_pais.setText("UNAM (DF México)");
                    imgPais.setImageResource(R.drawable.mexico);
                }else if(i == 2){
                    tv_pais.setText("UBC (Vancouver Canadá)");
                    imgPais.setImageResource(R.drawable.canada);
                }else if(i == 3){
                    tv_pais.setText("UAG (Guadalajara México)");
                    imgPais.setImageResource(R.drawable.mexico);
                }else if(i == 4){
                    tv_pais.setText("UC (Toronto Canadá)");
                    imgPais.setImageResource(R.drawable.canada);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        try {
            extras = getIntent().getExtras();
            currentName = extras.getString("USER_NAME");
            currentExp = extras.getString("USER_EXP");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        userInfo(currentExp);
    }

    public String RBSelected(){

        if(radioBF.isActivated()){
            selectedR=radioBF.getText().toString();
        }else {
            selectedR=radioBM.getText().toString();
        }
        return selectedR;
    }

    public void guardarS(View v) {

        if (checkEmptyFields()) {

            values.put(Model.Solicitudes.COLUMN_NAME_EXPEDIENTE, Integer.valueOf(et_expediente.getText().toString()));
            values.put(Model.Solicitudes.COLUMN_NAME_NOMBRE, et_nombre.getText().toString());
            values.put(Model.Solicitudes.COLUMN_NAME_UNIVERSIDAD, et_universidad.getText().toString());
            values.put(Model.Solicitudes.COLUMN_NAME_CAMPUS, et_campus.getText().toString());
            values.put(Model.Solicitudes.COLUMN_NAME_EDAD, Integer.valueOf(et_edad.getText().toString()));
            values.put(Model.Solicitudes.COLUMN_NAME_GENERO, RBSelected());
            values.put(Model.Solicitudes.COLUMN_NAME_LICENCIATURA, et_licenciatura.getText().toString());
            values.put(Model.Solicitudes.COLUMN_NAME_TELEFONO, et_telefono.getText().toString());
            values.put(Model.Solicitudes.COLUMN_NAME_PROMEDIO, Float.valueOf(et_promedio.getText().toString()));

            long newRowID = db.insert(Model.Solicitudes.TABLE_NAME2, null, values);

            if (newRowID == Long.valueOf(et_expediente.getText().toString())) {
                new AlertDialog.Builder(this)
                        .setTitle("Rsgistro Existoso")
                        .setMessage("El registro se a completado exitosamente")
                        .setPositiveButton("Volver a Inicio", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent goToHome = new Intent(getApplicationContext(), Home.class);
                                startActivity(goToHome);
                            }
                        }).show();

            } else if (newRowID == -1) {
                new AlertDialog.Builder(this)
                        .setTitle("Error en el registro")
                        .setMessage("No se ha podido registrar. Puede que el expediente " +
                                et_expediente.getText().toString() + " ya este en uso")
                        .setPositiveButton("OK", null).show();
            }
        }
    }

    private boolean checkEmptyFields() {
        boolean flag =  (et_nombre.getText().toString().matches("") || et_expediente.getText().toString().matches("") ||
                et_universidad.getText().toString().matches("") || et_campus.getText().toString().matches("") ||
                et_edad.getText().toString().matches("")  || et_licenciatura.getText().toString().matches("") ||
                et_telefono.getText().toString().matches("")) ? false : true;

        if (!flag) {
            new AlertDialog.Builder(this)
                    .setTitle("Falta Información")
                    .setMessage("Has dejado campos sin llenar")
                    .setPositiveButton("OK", null).show();
        }

        return flag;
    }

    //Enviar información a la actividad anterior
    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(getApplicationContext(), Home.class);
        goBack.putExtra("USER_NAME", et_nombre.getText().toString());
        goBack.putExtra("USER_EXP", et_expediente.getText().toString());
        startActivity(goBack);
    }

    public void userInfo(String exp) {
        String[] projection = { Model.Alumnos.COLUMN_NAME_EXPEDIENTE, Model.Alumnos.COLUMN_NAME_CAMPUS,
                                Model.Alumnos.COLUMN_NAME_CREDITOS, Model.Alumnos.COLUMN_NAME_PROMEDIO,
                                Model.Alumnos.COLUMN_NAME_PHONE, Model.Alumnos.COLUMN_NAME_EMAIL,
                                Model.Alumnos.COLUMN_NAME_LIC, Model.Alumnos.COLUMN_NAME_NOMBRE };
        String selection = Model.Alumnos.COLUMN_NAME_EXPEDIENTE + " = ?";
        String[] selectionArgs = { currentExp };
        Cursor fila = db.query(Model.Alumnos.TABLE_NAME, projection, selection, selectionArgs,
                                null, null, null);

        while (fila.moveToNext()) {
            campus = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_CAMPUS));
            //credits = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_CREDITOS));
            prom = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_PROMEDIO));
            phone = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_PHONE));
            email = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_EMAIL));
            lic = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_LIC));
        }
        fila.close();

        et_nombre.setText(currentName);
        et_expediente.setText(currentExp);
        et_campus.setText(campus);
        et_promedio.setText(prom);
        et_telefono.setText(phone);
        et_licenciatura.setText(lic);

    }
}