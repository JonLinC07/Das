package com.example.das;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.das.classes.DBHelper;
import com.example.das.classes.Model;

public class MisSolicitudes extends AppCompatActivity {

    TextView tv_exp, tv_reuslt;
    DBHelper DBHelper;
    SQLiteDatabase db;
    ContentValues values;
    String[] projection;
    String expediente;
    String nombre;
    String universidad;
    String campus;
    String edad;
    String genero;
    String licenciatura;
    String telefono;
    String promedio, currentName, currentExp;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_solicitudes);

        tv_exp = findViewById(R.id.tv_exp);
        tv_reuslt = findViewById(R.id.tv_reuslt);

        DBHelper = new DBHelper(getApplicationContext());
        db = DBHelper.getWritableDatabase();
        values = new ContentValues();
        projection =  new String[] {Model.Solicitudes.COLUMN_NAME_EXPEDIENTE, Model.Solicitudes.COLUMN_NAME_NOMBRE, Model.Solicitudes.COLUMN_NAME_UNIVERSIDAD,
                Model.Solicitudes.COLUMN_NAME_CAMPUS, Model.Solicitudes.COLUMN_NAME_EDAD, Model.Solicitudes.COLUMN_NAME_GENERO,
                Model.Solicitudes.COLUMN_NAME_LICENCIATURA, Model.Solicitudes.COLUMN_NAME_TELEFONO, Model.Solicitudes.COLUMN_NAME_PROMEDIO};
    }

    public void result(View view){
        if(checkEmptyFields()){
            String expR = tv_exp.getText().toString();
            String selection = Model.Solicitudes.COLUMN_NAME_EXPEDIENTE + " = ? ";
            String[] selectionArgs = {expR};

            Cursor fila2 = db.query(Model.Solicitudes.TABLE_NAME2, projection, selection, selectionArgs, null, null, null);
            while (fila2.moveToNext()){
                expediente = fila2.getString(fila2.getColumnIndex(Model.Solicitudes.COLUMN_NAME_EXPEDIENTE));
                nombre = fila2.getString(fila2.getColumnIndex(Model.Solicitudes.COLUMN_NAME_NOMBRE));
                universidad = fila2.getString(fila2.getColumnIndex(Model.Solicitudes.COLUMN_NAME_UNIVERSIDAD));
                campus = fila2.getString(fila2.getColumnIndex(Model.Solicitudes.COLUMN_NAME_CAMPUS));
                edad = fila2.getString(fila2.getColumnIndex(Model.Solicitudes.COLUMN_NAME_EDAD));
                genero = fila2.getString(fila2.getColumnIndex(Model.Solicitudes.COLUMN_NAME_GENERO));
                licenciatura = fila2.getString(fila2.getColumnIndex(Model.Solicitudes.COLUMN_NAME_LICENCIATURA));
                telefono = fila2.getString(fila2.getColumnIndex(Model.Solicitudes.COLUMN_NAME_TELEFONO));
                promedio = fila2.getString(fila2.getColumnIndex(Model.Solicitudes.COLUMN_NAME_PROMEDIO));
            }
            fila2.close();
            tv_reuslt.setText("Expediente: "+ expediente + "\n" + "Nombre: "+nombre+"\n"+"Universidad: "+universidad+"\n"+"Campus: "
                    + campus + "\n" + "Edad: "+ edad + "\n" + "Genero: " + genero + "\n" + "Licenciatura: " + licenciatura +
                    "\n" + "Teléfono: " + telefono + "\n" + "Promedio: " + promedio);
        }

        try {
            extras = getIntent().getExtras();
            currentName = extras.getString("USER_NAME");
            currentExp = extras.getString("USER_EXP");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkEmptyFields() {
        boolean flag =  (tv_exp.getText().toString().matches("") ) ? false : true;

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
        goBack.putExtra("USER_NAME", nombre);
        goBack.putExtra("USER_EXP", expediente);
        startActivity(goBack);
    }
}