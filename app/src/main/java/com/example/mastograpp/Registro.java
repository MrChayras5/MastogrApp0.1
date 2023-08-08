package com.example.mastograpp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    private EditText nombre_r;
    private EditText correo_r;
    private EditText contrasena_r;
    private EditText contrasena_v_r;
    private Button btnRegistro;

    //variables
    private String nombreR = "";

    private String correoR = "";

    private String contraR = "";
    private String contraV_V="";
    private Boolean AsistntM=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre_r = (EditText) findViewById(R.id.nombre_registro);
        correo_r = (EditText) findViewById(R.id.correo_registro);
        contrasena_r = (EditText) findViewById(R.id.contrasena_registro);
        contrasena_v_r = (EditText) findViewById(R.id.contrasena_verificacion);
        btnRegistro = (Button) findViewById(R.id.registrart_btn);



        mAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
/*
        correo_r = findViewById(R.id.correo_registro);
        contrasena_registro = findViewById(R.id.contrasena_registro);
*/
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreR = nombre_r.getText().toString();
                correoR = correo_r.getText().toString();
                contraR = contrasena_r.getText().toString();
                contraV_V = contrasena_v_r.getText().toString();

                if (!nombreR.isEmpty() && !correoR.isEmpty() && !contraR.isEmpty() && !contraV_V.isEmpty()) {
                    if (contraR.length() >= 6 && contraR.equals(contraV_V)) {
                        registrarUser();
                    } else {
                        if (contraR.length() < 6) {
                            Toast.makeText(Registro.this, "Contraseña debe contener minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Registro.this, "Las contraseñas no son iguales", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(Registro.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void registrarUser(){

            mAuth.createUserWithEmailAndPassword(correoR, contraR).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Map<String, Object> map = new HashMap<>();
                                map.put("nombre", nombreR);
                                map.put("correo", correoR);
                                map.put("contra", contraR);
                                map.put("AsistntM", AsistntM);

                                String id = mAuth.getCurrentUser().getUid();

                                mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> nose) {
                                        if (nose.isSuccessful()) {
                                            //si se crearon datos de la base de datos y nos mandara a otra pagina
                                            // el metodo es de la pagina de donde estamos a la pantalla a la que se le mandara el usuario
                                            startActivity(new Intent(Registro.this, Menu_Paciente.class));
                                            //para evitar que el usuario regrese a la pantalla de registro
                                            finish();
                                        } else {
                                            Toast.makeText(Registro.this, "No se pudo crear los datos correctamente", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "No se pudo registrar este usuario,", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }

}