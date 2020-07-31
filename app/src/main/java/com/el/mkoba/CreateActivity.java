package com.el.mkoba;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class CreateActivity extends AppCompatActivity {


    Button Reg,Log;


    private EditText firstName,lastName,id_NO,phoneNumber,location,Email,Password;
    private TextView toReg;
    private Button btnClientSubmit;

    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Reg = findViewById(R.id.Reg);
        Log = findViewById(R.id.LogIn);
        mAuth = FirebaseAuth.getInstance();



        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toreg = new Intent(getApplicationContext(),RegActivity.class);
                startActivity(toreg);


            }
        });

        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toreg = new Intent(getApplicationContext(),LogInActivity.class);
                startActivity(toreg);
            }
        });



    }
}