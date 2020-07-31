package com.el.mkoba;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;

public class MpesaPaymentActivity extends AppCompatActivity {

    private TextView back;
    private EditText name,number,Amount;

    private Button btnStkpush;

    private Daraja daraja;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpesa_payment);

        back = findViewById(R.id.Back);
        name = findViewById(R.id.Mpesa_name);
        number = findViewById(R.id.Mpesa_number);
        Amount = findViewById(R.id.amount);
        btnStkpush = findViewById(R.id.confirm_stk);





        daraja = Daraja.with("ZVsXqGcCtkcbe5VzpQAsgxxHeTrT41KY", "a1WcCqq2QY34hLmV", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(getApplicationContext().getClass().getSimpleName(), accessToken.getAccess_token());
                Toast.makeText(getApplicationContext(), "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(getApplicationContext().getClass().getSimpleName(), error);
            }
        });









        btnStkpush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!ValidaTion()){

                }else {

                    String PhoneNumber = number.getText().toString().trim();
                    String amount = Amount.getText().toString().trim();
                    Deposit(amount,PhoneNumber);

                }

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),PayMethodActivity.class));
                finish();
            }
        });


    }



    private  void Deposit(String amount,String PhoneNumber){


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();




        if (TextUtils.isEmpty(PhoneNumber)) {
            Toast.makeText(getApplicationContext(), "Please Provide a Phone Number", Toast.LENGTH_SHORT).show();

        }else{


            //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO


            LNMExpress lnmExpress = new LNMExpress(
                    "600368",
                    "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                    TransactionType.CustomerPayBillOnline,
                    amount,
                    "254708374149",
                    "174379",
                    PhoneNumber,
                    "http://mycallbackurl.com/checkout.php",
                    "600000",
                    "Goods Payment"
            );


            daraja.requestMPESAExpress(lnmExpress,
                    new DarajaListener<LNMResult>() {
                        @Override
                        public void onResult(@NonNull LNMResult lnmResult) {
                            Log.i(getApplicationContext().getClass().getSimpleName(), lnmResult.ResponseDescription);

                            progressDialog.dismiss();


                        }

                        @Override
                        public void onError(String error) {
                            Log.i(getApplicationContext().getClass().getSimpleName(), error);

                            progressDialog.dismiss();
                        }
                    }
            );


        }


    }


    private boolean ValidaTion() {

        String Name = name.getText().toString().trim();
        String Number = number.getText().toString().trim();
        String amount = Amount.getText().toString().trim();


        if (Name.isEmpty()) {
            name.setError("Name is empty");
            Toast.makeText(MpesaPaymentActivity.this, "Name is empty.", Toast.LENGTH_SHORT).show();
            return false;

        }  else if (Number.isEmpty()) {
            number.setError("Number Required");
            Toast.makeText(MpesaPaymentActivity.this, "Number Required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (amount.isEmpty()) {
            Amount.setError("Amount Required");
            Toast.makeText(MpesaPaymentActivity.this, "Amount Required", Toast.LENGTH_SHORT).show();
            return false;
        }
//        else if (Con_Password == Password) {
//            con_Password.setError("Password Don't Match");
//            return false;
//        }
        else {
            name.setError(null);
            number.setError(null);
            Amount.setError(null);
            return true;


        }

    }


}
