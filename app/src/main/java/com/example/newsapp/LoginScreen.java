package com.example.newsapp;


import android.app.ProgressDialog;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {
    EditText inputEmail,inputPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        mAuth = FirebaseAuth.getInstance();
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        mLoadingBar=new ProgressDialog(LoginScreen.this);
        if (mAuth.getCurrentUser()!=null){ startActivity(new Intent(this,MainActivity.class));finish(); }

    }



    public void signup(View view) {
        startActivity(new Intent(getApplicationContext(),RegisterScreen.class));
    }

    public void reset(View view) {
        startActivity(new Intent(getApplicationContext(),ResetScreen.class));
    }

    public void login(View view) {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please fill the email Correctly", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please fill the password correctly", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.length()<6){
            Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show();
            return;

        }
        mLoadingBar.setTitle("Login");
        mLoadingBar.setMessage("please wait while checking credentials");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mLoadingBar.dismiss();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Toast.makeText(LoginScreen.this, "Logged in succssfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            mLoadingBar.dismiss();
                            inputEmail.setText("");
                            inputPassword.setText("");
                            Toast.makeText(LoginScreen.this, "User does not exist", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }
}