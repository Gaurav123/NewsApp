package com.example.newsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText sname,sphone,inputEmail,inputPassword,inputpa;
    DatabaseReference reference;
    Button btnLogin;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        sname = findViewById(R.id.sname);
        sphone = findViewById(R.id.sphone);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputpa = findViewById(R.id.inputPa);
        btnLogin=findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        mLoadingBar=new ProgressDialog(RegisterScreen.this);

    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), LoginScreen.class));
    }



    public void Register(View view) {
        final String n = sname.getText().toString();
        final   String p =sphone.getText().toString();
        final String email =inputEmail.getText().toString();
        String password =inputPassword.getText().toString();
        String s =inputpa.getText().toString();


        if(TextUtils.isEmpty(n)||TextUtils.isEmpty(p)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)||TextUtils.isEmpty(s))
        {
            Toast.makeText(this, "Please fill all the deatils", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length()<6){
            Toast.makeText(this, "Password is too short", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.equals(s)){

            mLoadingBar.setTitle("Register");
            mLoadingBar.setMessage("please wait while checking credentials");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Pojo pojo = new Pojo(n,p,email);
                                reference.child(FirebaseAuth.getInstance().getUid()).setValue(pojo);

                                mLoadingBar.dismiss();

                                mAuth.signOut();
                                startActivity(new Intent(getApplicationContext(), LoginScreen.class));

                                Toast.makeText(RegisterScreen.this, "Registered Done Please Login", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RegisterScreen.this, "Signed in failed", Toast.LENGTH_SHORT).show();
                                mLoadingBar.dismiss();

                            }

                            // ...
                        }
                    });
        }

    }
}
