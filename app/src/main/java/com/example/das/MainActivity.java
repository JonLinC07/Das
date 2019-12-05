package com.example.das;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Declaración de elemetos visuales
    EditText email, pass;

    //Declaración de elementos logicos
    DBHelper DBHelper;
    SQLiteDatabase db;
    String[] projection;
    String usrExp, usrPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de elementos visuales
        email = findViewById(R.id._inputEmail);
        pass = findViewById(R.id._inputPass);

        //Inicilización de elementos logicos
        DBHelper = new DBHelper(getApplicationContext());
        db = DBHelper.getReadableDatabase();
        projection =  new String[] {BaseColumns._ID, Model.Alumnos.COLUMN_NAME_EMAIL, Model.Alumnos.COLUMN_NAME_PASSWORD};

    }

    public void GoToSignUp(View v) {
        Intent goToSignUp = new Intent(getApplicationContext(), SignUp.class);
        startActivity(goToSignUp);
    }

    //Este metodo se ejecuta cuando le das click al boton de Sign Im
    public void GoToHome(View v) {
        usrExp = email.getText().toString();
        usrPass = pass.getText().toString();

        String userInfo = Model.SQL_GET_SESSION_INFO + " WHERE " + Model.Alumnos.COLUMN_NAME_EXPEDIENTE +
                " = " + usrExp;
        SQLiteDatabase sql = DBHelper.getReadableDatabase();

        if (checkEmptyFields()) {
            //Aqui ya estan verificados los campos. Aqui va el rollo del cursor
        }
    }


    public boolean checkEmptyFields() {
        boolean flag = (usrExp.isEmpty() || usrPass.isEmpty()) ? false : true;

        if (!flag) {
            new AlertDialog.Builder(this)
                    .setTitle("Falta información")
                    .setMessage("Algunos campos no han sido llenados")
                    .setPositiveButton("OK", null).show();
        }

        return flag;
    }
}
