package com.example.mastograpp;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IniciarSesion extends AppCompatActivity {

    public EditText correoI;
    public EditText contrasenaI;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        mAuth=FirebaseAuth.getInstance();
        correoI = findViewById(R.id.correo);
        contrasenaI = findViewById(R.id.contrasena);

    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void iniciarSesion(View v){
        mAuth.signInWithEmailAndPassword(correoI.getText().toString().trim(), contrasenaI.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"Listo", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Prueba.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"Fallido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}