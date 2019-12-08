package com.example.das;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.das.classes.DBHelper;
import com.example.das.classes.Model;
import com.example.das.classes.Sender;

public class SignUp extends AppCompatActivity {

    //Declaración de elementos visuales
    EditText exp, name, lic, email, phone, pass, confirmPass;
    CheckBox terms;

    //Declaración de elementos logicos
    com.example.das.classes.DBHelper DBHelper;
    SQLiteDatabase db;
    ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Inicialización de elementos logicos
        exp = findViewById(R.id._inputExp);
        name = findViewById(R.id._inputFullName);
        lic = findViewById(R.id._inputLic);
        email = findViewById(R.id._inputEmail);
        phone = findViewById(R.id._inputPhone);
        pass = findViewById(R.id._inputPass);
        confirmPass = findViewById(R.id._confirmPass);
        terms = findViewById(R.id._checkTerms);

        //Inicilización de elementos logicos
        DBHelper = new DBHelper(getApplicationContext());
        db = DBHelper.getWritableDatabase();
        values = new ContentValues();
    }

    public void sendUser(View v) {

        if (checkEmptyFields() && checkTerms() && checkConfirmPass(checkEmptyFields())) {

            values.put(Model.Alumnos.COLUMN_NAME_EXPEDIENTE, Integer.valueOf(exp.getText().toString()));
            values.put(Model.Alumnos.COLUMN_NAME_NOMBRE, name.getText().toString());
            values.put(Model.Alumnos.COLUMN_NAME_LIC, lic.getText().toString());
            values.put(Model.Alumnos.COLUMN_NAME_PHONE, phone.getText().toString());
            values.put(Model.Alumnos.COLUMN_NAME_EMAIL, email.getText().toString());
            values.put(Model.Alumnos.COLUMN_NAME_PASSWORD, pass.getText().toString());

            long newRowID = db.insert(Model.Alumnos.TABLE_NAME, null, values);

            if (newRowID == Long.valueOf(exp.getText().toString())) {
                sendEmail();

                new AlertDialog.Builder(this)
                        .setTitle("Rsgistro Existoso")
                        .setMessage("El registro se a completado. Ya puedes iniciar seción")
                        .setPositiveButton("Iniciar Sesión", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent goToLogin = new Intent(getApplicationContext(), MainActivity.class);
                                goToLogin.putExtra("EXP", exp.getText().toString());
                                startActivity(goToLogin);
                            }
                        }).show();

                } else if (newRowID == -1) {
                new AlertDialog.Builder(this)
                        .setTitle("Error en el registro")
                        .setMessage("No se ha podido registrar. Puede que el expediente " +
                        exp.getText().toString() + " ya este en uso")
                        .setPositiveButton("OK", null).show();
            }
        }
    }

    public void sendEmail() {
        String mailTo = email.getText().toString();
        String subject = "Confirmación de registro DAD";
        String message = "Bienvenido a DAS " + name.getText().toString() + ". Ahora podras acceder" +
                "a nuestro portal, desde donde podras consultar cualquier información que desees con" +
                "respecto a intercambios. Te esperamos en nuestro portal.";

        Sender sender = new Sender(this, mailTo, subject.trim(), message);
        sender.execute();
    }

    //VALIDACIONES

    //Validar que no haya campos vacios
    private boolean checkEmptyFields() {
        boolean flag =  (exp.getText().toString().matches("") || name.getText().toString().matches("") ||
                lic.getText().toString().matches("") || email.getText().toString().matches("") ||
                email.getText().toString().matches("") || phone.getText().toString().matches("") ||
                pass.getText().toString().matches("") || confirmPass.getText().toString().matches("")) ? false : true;

        if (!flag) {
            new AlertDialog.Builder(this)
                    .setTitle("Falta Información")
                    .setMessage("Has dejado campos sin llenar")
                    .setPositiveButton("OK", null).show();
        }

        return flag;
    }

    //Confirmando que las contraseñas sean iguales
    private boolean checkConfirmPass(boolean flagEmpty) {
        boolean flag = (flagEmpty && pass.getText().toString().equals(confirmPass.getText().toString()))
                ? true : false;

        if (!flag){
            new AlertDialog.Builder(this)
                    .setTitle("Las contraseñas no coinsiden")
                    .setMessage("Debes de confirmas correctamente las contraseñas")
                    .setPositiveButton("OK", null).show();
        }

        return flag;
    }

    //Validar aceptación de terminos y condiciones de uso
    private boolean checkTerms() {
        boolean flag = (terms.isChecked()) ? true : false;

        if (!flag) {
            new AlertDialog.Builder(this)
                    .setTitle("Terminos de condicionas")
                    .setMessage("Debes estar de acuerda con nuestras condiciones de uso para tu registro")
                    .setPositiveButton("OK", null).show();
        }

        return flag;
    }
}
