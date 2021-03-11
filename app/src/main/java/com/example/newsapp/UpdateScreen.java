package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateScreen extends AppCompatActivity {
    EditText name1,phone1;
    TextView email1;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_screen);
        name1 = findViewById(R.id.name);
        phone1=findViewById(R.id.phone);
        email1=findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase= FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference().child("Users").child(mAuth.getUid());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pojo pojo = snapshot.getValue(Pojo.class);
                name1.setText(pojo.getName());
                email1.setText(pojo.getEmail());
                phone1.setText(pojo.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateScreen.this, "LOL", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void update(View view) {
        String name = name1.getText().toString();
        String phone = phone1.getText().toString();
        String email = email1.getText().toString();
        Pojo pojo =new Pojo(name,phone,email);
        reference.setValue(pojo);
        Toast.makeText(UpdateScreen.this, "details updated", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(UpdateScreen.this,AccountActivity.class));
//        finish();
    }
}
