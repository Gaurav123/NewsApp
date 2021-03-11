package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AccountActivity extends AppCompatActivity {
    TextView textView, textView2, textView3,toptext;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    private ProgressDialog mLoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mAuth = FirebaseAuth.getInstance();
        toptext=findViewById(R.id.topText);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.id_account);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.id_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.id_search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.id_account:

                        return true;
                    case R.id.id_about:
                        startActivity(new Intent(getApplicationContext(), DeveloperActivity.class));
                        overridePendingTransition(0, 0);
                        finish();

                        return true;
                }

                return false;
            }
        });
        mLoadingBar=new ProgressDialog(AccountActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mLoadingBar.setTitle("Fetching Details ");
        mLoadingBar.setMessage("please wait...");
        mLoadingBar.show();
        reference = firebaseDatabase.getReference().child("Users").child(mAuth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mLoadingBar.dismiss();
                Pojo pojo = snapshot.getValue(Pojo.class);
                textView.setText(pojo.getName());
                textView2.setText(pojo.getPhone());
                textView3.setText(pojo.getEmail());
                toptext.setText("Hello, "+pojo.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountActivity.this, "LOL", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Edit(View view) {
        startActivity(new Intent(getApplicationContext(),UpdateScreen.class));

    }

    public void Signout(View view) {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(),LoginScreen.class));
        finish();
//
    }


}

