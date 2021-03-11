package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class contactUs extends AppCompatActivity {
    EditText mail,mesg,sub;
    Button callbtn,mailbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        sub=(EditText)findViewById(R.id.sub);
        mesg=(EditText)findViewById(R.id.msg);

    }


    public void Mail(View view) {
        mailbtn=(Button) findViewById(R.id.mail_btn);
        final String to=mail.getText().toString();
        final String subject=sub.getText().toString();
        final String mailm ="gaurav.sharma45578@gmail.com";
       final String message=mesg.getText().toString();
        if(TextUtils.isEmpty(to)||TextUtils.isEmpty(subject)||TextUtils.isEmpty(message))
        {
            Toast.makeText(this, "Please fill all the deatils", Toast.LENGTH_SHORT).show();
        }

        mailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ mailm});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                email.setType("message/rfc822");
//                Intent intomail=new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"+mailm));
//                startActivity(intomail);

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                return;
            }
        });
    }

    public void Whatsup(View view) {
        callbtn=(Button) findViewById(R.id.whatsup_btn);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobilenumber="9372165780";
                String msg="Attend for the Room Servicing Immediately";
                Intent intowa=new Intent(Intent.ACTION_VIEW);
                intowa.setData(Uri.parse("https://api.whatsapp.com/send?phone="+"+91"+mobilenumber+"&text="+msg));
                startActivity(intowa);

            }
        });
    }

}