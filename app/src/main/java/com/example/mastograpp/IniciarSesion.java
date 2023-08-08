package com.example.mastograpp;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IniciarSesion extends AppCompatActivity {

    public EditText correoI;
    public EditText contrasenaI;
    //cambiar, esto esta mal
    public Boolean AsistntM;
public Button inisiarsesionbt;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        mAuth=FirebaseAuth.getInstance();

        correoI = findViewById(R.id.correo);
        contrasenaI = findViewById(R.id.contrasena);

        Button inisiarsesionbt = findViewById(R.id.inisiarsesionbtn);
        inisiarsesionbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inisiarSesion();
            }
        });
    }

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void inisiarSesion() {
        String correo = correoI.getText().toString();
        String contrasena = contrasenaI.getText().toString();


        if (!correo.isEmpty() && !contrasena.isEmpty()) {
            mAuth.signInWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();

                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot Snapshot) {
                                    if (Snapshot.exists()) {
                                        Boolean asistente = Snapshot.child("AsistntM").getValue(Boolean.class);

                                        if (asistente != null) {
                                            if (asistente) {
                                                // El usuario es un asistente
                                                Toast.makeText(IniciarSesion.this, "Bienvenido, Asistente", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(IniciarSesion.this, Menu_Asistente.class));
                                            } else {
                                                // El usuario no es un asistente
                                                Toast.makeText(IniciarSesion.this, "Bienvenido, Paciente", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(IniciarSesion.this, Menu_Paciente.class));
                                            }
                                            finish(); // Opcionalmente, finaliza la actividad actual para que el usuario no pueda regresar

                                        }else{
                                            Toast.makeText(IniciarSesion.this, "El valor el null", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Error en el inicio de sesión", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(IniciarSesion.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }


    public void registrarse(View view){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }

    public void prueba(View v){
        Intent i = new Intent(this, Menu_Paciente.class);
        startActivity(i);
    }
}