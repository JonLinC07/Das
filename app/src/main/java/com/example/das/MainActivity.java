package com.example.das;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void GoToSignUp(View v) {
        Intent goToSignUp = new Intent(getApplicationContext(), SignUp.class);
        startActivity(goToSignUp);
    }
}
