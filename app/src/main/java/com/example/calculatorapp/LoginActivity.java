package com.example.calculatorapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    Button signIn;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    TextInputEditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signIn=findViewById(R.id.signIn);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        firebaseAuth = FirebaseAuth.getInstance();
//        email.setText("user1@gmail.com");
//        password.setText("user@1");
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkConnection.showLoader(LoginActivity.this);


                String userEmail = email.getText().toString();
                String userPaswd = password.getText().toString();
                if (userEmail.isEmpty()||userPaswd.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Enter Email and Password", Toast.LENGTH_SHORT).show();

                }else {
                firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                            NetworkConnection.progressDialog.dismiss();
                        } else {
                            NetworkConnection.progressDialog.dismiss();

                            showAlert("Logged In Successfully", "Success");
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                public void run() {

                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));

                                }
                            }, 2000);
                        }
                    }
                });}
            }
        });

    }
    private void showAlert(String msg, String status) {

        Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.alert_layout);
        dialog.setTitle(status);
        dialog.show();

        TextView tvMsg = dialog.findViewById(R.id.tvMsg);
        tvMsg.setText(msg);


    }
}