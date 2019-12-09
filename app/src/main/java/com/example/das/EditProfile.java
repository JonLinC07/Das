package com.example.das;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    Bitmap image;
    String photoPath, nameImage;
    File imageFile;
    Uri selectedImage;

    boolean picOrNot = false;

    static final int CAMERA_REQUEST = 62, RESULT_LOAD_IMAGE = 98;
    static final String IMAGES_DIRECTORY = "Das_Images";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Editar Perfil");
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        //SeekBar
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

    //Actualizando la información del usuario
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
                            //goToProfile.putExtra("PATH", photoPath);
                            startActivity(goToProfile);
                        }
                    }).show();
        } else {
            Toast.makeText(this, "" + newRowID, Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para enviar inforación a la actividad anterior
    @Override
    public void onBackPressed() {
        Intent goBack = new Intent(getApplicationContext(), Profile.class);
        goBack.putExtra("USER_NAME", inputName.getText().toString());
        goBack.putExtra("USER_EXP", viewExp.getText().toString());
        startActivity(goBack);
    }

    //Verificar permisos de la camara y abrirla
    public void openCamera(View v) {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
            //Toast.makeText(this, "Permiso consedido", Toast.LENGTH_SHORT).show();
        } else {
            GoToCamera();
        }
    }

    //Pedir permisos y ejecutar la camara
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResult);

        if (requestCode == CAMERA_REQUEST && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permiso consedido", Toast.LENGTH_SHORT).show();
            GoToCamera();
        }
    }

    private void GoToCamera() {
        //Creando directorio donde guardar la imagen
        File directory = new File(Environment.getExternalStorageDirectory(), IMAGES_DIRECTORY);

        nameImage = new SimpleDateFormat("ddMMyyyy_HHmmSS").format(new Date()) + ".jpg";
        photoPath = Environment.getExternalStorageDirectory() + File.separator + IMAGES_DIRECTORY +
                File.separator + nameImage;
        imageFile = new File(photoPath);

        Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCamera, CAMERA_REQUEST);
    }

    //Imprime la imagen en el IamageView
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Si es codigo de la camara
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            MediaScannerConnection.scanFile(this, new String[]{photoPath}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("Path", photoPath);
                        }
                    });

            image = (Bitmap) data.getExtras().get("data");
            viewPhoto.setImageBitmap(image);
            viewPhoto.setRotation(90);
            picOrNot = true;

            //Si es codigo de la galeria
        } else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
                    null,null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            photoPath = cursor.getString(columnIndex);
            image = BitmapFactory.decodeFile(photoPath);
            viewPhoto.setImageBitmap(image);
            picOrNot = true;
        }
    }

    //Verificar permisos y abrir la galeria
    public void GoToGallery(View v) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
        } else {
            Intent goToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(goToGallery, RESULT_LOAD_IMAGE);
        }
    }

    //Validación de escritura externa
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
