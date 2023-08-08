package com.example.mastograpp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText correo_r;
    private EditText contrasena_registro;
    private EditText contrasena_verificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth=FirebaseAuth.getInstance();

        correo_r = findViewById(R.id.correo_registro);
        contrasena_registro = findViewById(R.id.contrasena_registro);

    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void registrarUser(View v){

            mAuth.createUserWithEmailAndPassword(correo_r.getText().toString(),contrasena_registro.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Registro.this, "Authenticatio", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), Prueba.class);
                                startActivity(i);
                            }else {
                                Toast.makeText(Registro.this, "Authenticatio fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
       
    }

}