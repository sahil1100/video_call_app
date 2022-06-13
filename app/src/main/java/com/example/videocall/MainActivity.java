package com.example.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText name, email,password, loginEmail,loginPass;
    Button btn,btnLogin;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btnLogin);

        btn = findViewById(R.id.btn);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();




        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading....");



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.show();
                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    database = FirebaseDatabase.getInstance();
                                    myRef = database.getReference("User");

                                    MyData data = new MyData(name.getText().toString(), email.getText().toString(), password.getText().toString(), currentUser.getUid());
                                    myRef.child(currentUser.getUid()).setValue(data);
                                    dialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Success....", Toast.LENGTH_SHORT).show();

                                }
                                else{

                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(loginEmail.getText().toString(), loginPass.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(MainActivity.this, "Login...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, showData_Activity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Fail...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}