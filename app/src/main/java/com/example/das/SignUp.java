package com.example.das;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {

    EditText exp, name, lic, email, phone, pass, confirmPass;
    CheckBox terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        exp = findViewById(R.id._inputExp);
        name = findViewById(R.id._inputFullName);
        lic = findViewById(R.id._inputLic);
        email = findViewById(R.id._inputEmail);
        phone = findViewById(R.id._inputPhone);
        pass = findViewById(R.id._inputPass);
        confirmPass = findViewById(R.id._confirmPass);
        terms = findViewById(R.id._checkTerms);

    }

    public void sendUser(View v) {
        if (checkEmptyFields() && checkTerms()) {
            
        }
    }

    //VALIDACIONES

    //Validar que no haya campos vacios
    private boolean checkEmptyFields() {
        boolean flag =  (exp.getText().length() != 0 || name.getText().length() != 0 || lic.getText().length() != 0 ||
                         email.getText().length() != 0 || email.getText().length() != 0 || phone.getText().length() != 0 ||
                pass.getText().length() != 0 || confirmPass.getText().length() != 0) ? true : false;

        if (!flag) {
            new AlertDialog.Builder(this)
                    .setTitle("Falta Información")
                    .setMessage("Has dejado campos sin llenar")
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
