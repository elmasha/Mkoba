package com.el.mkoba;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.ChasingDots;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    TextView toReg;
    private EditText login_email,login_password;
    private Button Submit;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference MkobaRef = db.collection("Mkoba_Account");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        mAuth = FirebaseAuth.getInstance();

        toReg = findViewById(R.id.Client_Reg);

        login_email = findViewById(R.id.Login_Email);
        login_password = findViewById(R.id.Login_Password);
        Submit = findViewById(R.id.Login_Submit);
        progressBar = findViewById(R.id.Progress112);


        ChasingDots cashingDots = new ChasingDots();
        progressBar.setIndeterminateDrawable(cashingDots);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ValidaTion()){


                }else {

                    String email = login_email.getText().toString().trim();
                    String password = login_password.getText().toString().trim();

                    LoginUser(email,password);
                }


            }
        });


        toReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toreg = new Intent(getApplicationContext(),RegActivity.class);
                startActivity(toreg);
            }
        });

    }

    private void LoginUser(String email,String password) {


        progressBar.setVisibility(View.VISIBLE);
        Submit.setVisibility(View.INVISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                   Update_TOken();

                }else {

                    Toast.makeText(LogInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Submit.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LogInActivity.this, "Error !!"+task.getException()
                            .getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Submit.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(LogInActivity.this, "Error !!"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }



    private void Update_TOken(){

      String   mUID = mAuth.getCurrentUser().getUid();

        String refreshtoken = FirebaseInstanceId.getInstance().getToken();

        Map<String,Object> log_in = new HashMap<>();
        log_in.put("device_token",refreshtoken);

        MkobaRef.document(mUID).update(log_in).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Submit.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
                    finish();


                }else {

                    Toast.makeText(LogInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Submit.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }
        });









    }

    private boolean ValidaTion() {

        String email = login_email.getText().toString().trim();
        String password = login_password.getText().toString().trim();


        if (email.isEmpty()) {
            login_email.setError("Email is empty");
            Toast.makeText(LogInActivity.this, "Email is empty.", Toast.LENGTH_SHORT).show();
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            login_email.setError("Please enter a Valid email");
            Toast.makeText(LogInActivity.this, "Please enter a Valid email.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            login_password.setError("Password Required");
            Toast.makeText(LogInActivity.this, "ID number Required", Toast.LENGTH_SHORT).show();
            return false;
        }
//        else if (Con_Password == Password) {
//            con_Password.setError("Password Don't Match");
//            return false;
//        }
        else {
            login_email.setError(null);
//            con_Password.setError(null);
            login_password.setError(null);
            return true;


        }

    }




}