package com.example.das;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Declaración de elemetos visuales
    EditText exp, pass;
    TextView text;

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
        exp = findViewById(R.id._inputExp);
        pass = findViewById(R.id._inputPass);
        text = findViewById(R.id.textView);

        //Inicilización de elementos logicos
        DBHelper = new DBHelper(getApplicationContext());
        db = DBHelper.getReadableDatabase();
        projection =  new String[] {Model.Alumnos.COLUMN_NAME_EXPEDIENTE, Model.Alumnos.COLUMN_NAME_PASSWORD};

    }

    public void GoToSignUp(View v) {
        Intent goToSignUp = new Intent(getApplicationContext(), SignUp.class);
        startActivity(goToSignUp);
    }

    //Este metodo se ejecuta cuando le das click al boton de Sign Im
    public void GoToHome(View v) {
        usrExp = exp.getText().toString();
        usrPass = pass.getText().toString();

        String selection = Model.Alumnos.COLUMN_NAME_EXPEDIENTE + " = ? AND " +
                Model.Alumnos.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {usrExp, usrPass};

        Cursor fila = db.query(Model.Alumnos.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        while (fila.moveToNext()) {
            String expediente = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_EXPEDIENTE));
            String contraseña = fila.getString(fila.getColumnIndex(Model.Alumnos.COLUMN_NAME_PASSWORD));

            if (expediente.matches(usrExp) && contraseña.matches(usrPass)) {
                Intent goToHome = new Intent(getApplicationContext(), Home.class);
                startActivity(goToHome);
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Datos incorrectos")
                        .setMessage("No existe una cuenta con los datos proporcionados")
                        .setPositiveButton("OK", null).show();
            }
        }
        fila.close();
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
