package com.example.mastograpp;

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

    public void registro(View v){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }
    public void inisiarsesion(View view){
        Intent i = new Intent(this, IniciarSesion.class);
        startActivity(i);
    }
}