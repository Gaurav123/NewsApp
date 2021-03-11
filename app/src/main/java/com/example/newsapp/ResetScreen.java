package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetScreen extends AppCompatActivity {
    EditText inputEmail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_screen);
        mAuth = FirebaseAuth.getInstance();
        inputEmail = findViewById(R.id.inputEmail);
    }

    public void reset(View view) {

        String emailAddress = inputEmail.getText().toString().trim();

        if(TextUtils.isEmpty(emailAddress)){
            Toast.makeText(this, "Please the email field properly", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ResetScreen.this, "Password is sent to your gmail", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ResetScreen.this,LoginScreen.class));
                            finish();
                        }
                    }
                });
    }
}