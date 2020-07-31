package com.el.mkoba;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.annotations.NonNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TransViewActivity extends AppCompatActivity {
    private int pay_status;
    private String Category;
    private String Description;
    private String Deadline,payID,TitlePay;
    private int Status_lable;
    private double Amount,Total_price;
    private int pay_status2;
    private FirebaseAuth mAuth;
    String name;
    String mkobaID,number;
    private String  Mpesa_no;
    private RelativeLayout relativeLayout;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.168.42.98:16";

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference TransactionRef = db.collection("Mkoba_Transactions");
    CollectionReference MkobaRef = db.collection("Mkoba_Account");

    int percernt =0;
    int fundsPErcent = 0;
    int pckPErcent = 0;
    int releasePErcent = 0;

    int status_percent;

    String  Doc_id,TransUID,TransTitle;
    String  noss;
    private View view1,view2,view3;

    Daraja daraja;
    int fundsPrt = 0;
    int pckgPrt = 0;
    int releasPrt = 0;
    int CatSrtuas;
    int Btn_State;
    private ProgressBar StatusprogressBar;
    private TextView category,rcptNo,deadline,date,desc,title,
            Deposit_cash,status_percent1,payment_id,amount,status_lable,back;

    private Button finish_btn;
    private String Mpesa_number,name2,mkobaID2,number2;
    private TextView fromName,fromNumber;
    private AlertDialog dialog3;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_view);
        mAuth = FirebaseAuth.getInstance();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);


        StatusprogressBar = findViewById(R.id.Proggress_pay);
        category = findViewById(R.id.Name);
        rcptNo = findViewById(R.id.Recipient);
        deadline = findViewById(R.id.Pay_Deadline);
        desc = findViewById(R.id.Desc);
        title = findViewById(R.id.Item_name);
        status_percent1 = findViewById(R.id.Pay_status1);
        payment_id = findViewById(R.id.pay_Id);
        amount = findViewById(R.id.Paycard);
        status_lable = findViewById(R.id.Status_patyment);
        Deposit_cash = findViewById(R.id.Deposit_cash);
        finish_btn = findViewById(R.id.Btn_confirm);
        back = findViewById(R.id.Pay_back);
        relativeLayout = findViewById(R.id.Relativelayout);
        fromName = findViewById(R.id.from_Name);
        fromNumber = findViewById(R.id.from_number);

        view1 = findViewById(R.id.Pay_v1);
        view2 = findViewById(R.id.pck_V2);
        view3 = findViewById(R.id.release_V3);

        daraja = Daraja.with(getString(R.string.CONSUMER_KEY), getString(R.string.Secret_KEY), new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
//                Log.i(getContext().getClass().getSimpleName(), accessToken.getAccess_token());
               // Toast.makeText(getApplicationContext(), "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(getApplicationContext().getClass().getSimpleName(), error);
            }
        });


        if (getIntent() != null){

            Doc_id = getIntent().getStringExtra("Doc_ID").toString();


        }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent logout = new Intent(getApplicationContext(),DashBoardActivity.class);
                logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logout);
            }
        });


        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String  User_ID = mAuth.getCurrentUser().getUid();
                ProgressDialog progressDialog = new ProgressDialog(TransViewActivity.this);
                progressDialog.setMessage("Please wait.....");
                progressDialog.show();


                if (Btn_State ==0){
                    Dialog_Pay();
                }else {

                }

//                Map<String,String> stk = new HashMap<>();
//                stk.put("Phone",number2);
//
//                Call<Void> call = retrofitInterface.executestk(stk);
//                call.enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//
//                        if (response.code() ==200 ){
//
//                            Toast.makeText(TransViewActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
//                        }else  if (response.code() == 400){
//
//                            Toast.makeText(TransViewActivity.this, "Bad request", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        Toast.makeText(TransViewActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//

               if (Btn_State==1){

                   HashMap<String,Object> notify = new HashMap<>();
                   notify.put("Recipient_No",number);
                   notify.put("type",TitlePay+" Delivered");
                   notify.put("to",TransUID);
                   notify.put("body",Description);
                   notify.put("User_id",User_ID);
                   notify.put("doc_ID",Doc_id);
                   notify.put("timestamp", FieldValue.serverTimestamp());


                   WriteBatch batch = db.batch();
                    DocumentReference doc1 = TransactionRef.document(Doc_id);
                    batch.update(doc1, "Status_percent", 80);
                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<Void> task) {

                            if (task.isSuccessful()){


                                MkobaRef.document(User_ID).collection("Notification").document().set(notify)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                        Toast.makeText(TransViewActivity.this, "Delivered", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });

                            }else {

                                Toast.makeText(TransViewActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    });


                }else if (Btn_State ==2){
                   HashMap<String,Object> notify = new HashMap<>();
                   notify.put("Recipient_No",number);
                   notify.put("type","Funds release to "+number);
                   notify.put("to",TransUID);
                   notify.put("body",Description);
                   notify.put("User_id",User_ID);
                   notify.put("doc_ID",Doc_id);
                   notify.put("timestamp", FieldValue.serverTimestamp());


                    WriteBatch batch = db.batch();
                    DocumentReference doc1 = TransactionRef.document(Doc_id);
                    batch.update(doc1, "Status_percent", 100);
                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<Void> task) {

                            if (task.isSuccessful()){


                                MkobaRef.document(User_ID).collection("Notification").document().set(notify)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                                Toast.makeText(TransViewActivity.this, "Funds release..", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        });



                            }else {

                                Toast.makeText(TransViewActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    });

                }

            }
        });





        LoadDetails();
        statusTimer();
        LoadDetails2();

    }

    private void LoadDetails2() {

        String User_id = mAuth.getCurrentUser().getUid();

        MkobaRef.document(User_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                    return;
                }

                if (documentSnapshot.exists()) {

                    Mkoba_Account account = documentSnapshot.toObject(Mkoba_Account.class);
                    name2 = account.getFirst_Name()+" "+account.getLast_Name();
                    mkobaID2 = account.getMkoba_ID();
                    number2 = account.getPhone();

                } else {

                }




            }
        });


    }



    private void Dialog_Pay(){
        final  AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.deposit_dialog, null);
        mbuilder.setView(mView);

        dialog3 = mbuilder.create();
        dialog3.show();


        EditText  Tran_no = mView.findViewById(R.id.Input_number);
        Tran_no.setText(number);
        Button confirm_trans = mView.findViewById(R.id.stk_push);


        TextView name = mView.findViewById(R.id.D_name);
        TextView cancel_trans = mView.findViewById(R.id.dia_close);
        TextView id = mView.findViewById(R.id.D_id);


        name.setText(name2);
        id.setText("#"+mkobaID2);




        cancel_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog3.dismiss();

            }
        });

        confirm_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String User_ID = mAuth.getCurrentUser().getUid();


                if (Btn_State == 0){
                    HashMap<String,Object> notify = new HashMap<>();
                    notify.put("Recipient_No",number);
                    notify.put("type","Cash Deposited: "+number);
                    notify.put("to",TransUID);
                    notify.put("body",Description);
                    notify.put("User_id",User_ID);
                    notify.put("doc_ID",Doc_id);
                    notify.put("timestamp", FieldValue.serverTimestamp());






                    WriteBatch batch = db.batch();
                    DocumentReference doc1 = TransactionRef.document(Doc_id);
                    batch.update(doc1, "Status_percent", 60);
                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<Void> task) {

                            if (task.isSuccessful()){


                                MkobaRef.document(User_ID).collection("Notification").document().set(notify)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                                Toast.makeText(TransViewActivity.this, "Cash Deposited", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        });

                            }else {

                                Toast.makeText(TransViewActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    });



                }





                PayMent(Tran_no.getText().toString());

            }
        });



    }



    private void PayMent(String number2) {

        //Get Phone Number from User Input

        if (TextUtils.isEmpty(number2)) {
            Toast.makeText(getApplicationContext(), "Please Provide a Phone Number", Toast.LENGTH_SHORT).show();

        }else{

//            progressBar.setVisibility(View.VISIBLE);
//            conPayment.setEnabled(false);
//            conPayment.setVisibility(View.INVISIBLE);

            //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
            LNMExpress lnmExpress = new LNMExpress(
                    "174379",
                    "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                    //https://developer.safaricom.co.ke/test_credentials
                    TransactionType.CustomerPayBillOnline,
                    "1",
                    "254746291229",
                    "174379",
                     number2,
                    "http://169.254.164.159:90/stk_callback",
                    "174379",
                    "Mkoba enterprise"
            );


            daraja.requestMPESAExpress(lnmExpress,
                    new DarajaListener<LNMResult>() {
                        @Override
                        public void onResult(@androidx.annotation.NonNull LNMResult lnmResult) {
                            Log.i(getApplicationContext().getClass().getSimpleName(), lnmResult.ResponseDescription);

                            if (lnmResult.ResponseCode == "0") {

                                Snackbar snackbar = Snackbar.make(relativeLayout, "Amount deposited successfully", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                progressDialog.dismiss();


                            }else {



                            }


                        }

                        @Override
                        public void onError(String error) {
                            Log.i(getApplicationContext().getClass().getSimpleName(), error);
                               Toast.makeText(getApplicationContext(), "Something wrong Occurred.... \n"+
                                       "Check your balance \n Check network ", Toast.LENGTH_LONG).show();
                               progressDialog.dismiss();

                        }
                    }
            );


        }

    }




    private void statusTimer() {

        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                percernt++;


                StatusprogressBar.setProgress(percernt);

                if (percernt == status_percent){
                    t.cancel();

                }
            }
        };
        t.schedule(tt,0,70);


    }


    private void LoadDetails() {

        TransactionRef.whereEqualTo("Doc_Id",Doc_id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("ResourceAsColor")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots,
                                @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {

                    return;
                }

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {


                    Mkoba_Transactions account = documentSnapshot.toObject(Mkoba_Transactions.class);
                    Category = account.getCategory();
                    category.setText(Category);

                    Description = account.getDescription();
                    desc.setText(Description);

                    Deadline = account.getDead_line();
                    payID = account.getPayment_Id();
                    CatSrtuas = account.getStatus();
                    String namess = account.getTrans_Name();
                      noss = account.getTrans_Phone();
                    fromName.setText("Payment from: "+namess);
                    fromNumber.setText(noss);


                    payment_id.setText(payID);

                    TitlePay = account.getPayment_Title();
                    title.setText(TitlePay);
                    TransUID = account.getTrans_UID();

                    deadline.setText("Deadline "+Deadline);
                    number = account.getRecipient_No();

                    rcptNo.setText(number);

                    status_percent = account.getStatus_percent();

                    Status_lable = account.getStatus();

                    if (Status_lable == 1){

                        status_lable.setText("Waiting for seller");

                    }else if (Status_lable ==2){

                        status_lable.setText("Waiting for buyer");
                    }


                    Total_price = account.getTotal_price();
                    Amount = account.getAmount();
                    pay_status = account.getPayment_status();

                    amount.setText("Ksh "+Amount+"");
                    status_percent1.setText(status_percent+"%");


                    switch(status_percent) {
                        case 40:
                            Btn_State = 0;
                            view1.setVisibility(View.VISIBLE);
                            view2.setVisibility(View.INVISIBLE);
                            view3.setVisibility(View.INVISIBLE);
                            if (CatSrtuas == 1){
                                Deposit_cash.setText("Deposit Cash from "+number2);

                                finish_btn.setVisibility(View.VISIBLE);
                                finish_btn.setText("Deposit Cash");

                            }else if (CatSrtuas==2){

                                Deposit_cash.setText("Cash not yet Deposited");
                                finish_btn.setVisibility(View.GONE);

                            }

                            break;
                        case 60:
                            view1.setVisibility(View.VISIBLE);
                            view2.setVisibility(View.VISIBLE);
                            view2.setBackgroundColor(R.color.colorPrimary);
                            view3.setVisibility(View.INVISIBLE);
                            Btn_State = 1;
                            if (CatSrtuas == 1){
                                Deposit_cash.setText("Item not received");
                                finish_btn.setVisibility(View.VISIBLE);
                                finish_btn.setText("Confirm item received");

                            }else if (CatSrtuas==2){

                                Deposit_cash.setText("Item waiting for Delivery");
                                finish_btn.setVisibility(View.VISIBLE);
                                finish_btn.setText("Confirm item delivered");

                            }

                            break;
                        case 80:

                            if (CatSrtuas == 1){
                                Deposit_cash.setText("Package received, release funds ");
                                Btn_State = 2;
                                finish_btn.setVisibility(View.VISIBLE);
                                finish_btn.setText("Confirm funds release");

                            }else if (CatSrtuas==2){

                                Deposit_cash.setText("Item Delivered");
                                finish_btn.setVisibility(View.GONE);
                            }

                            view1.setVisibility(View.VISIBLE);
                            view2.setVisibility(View.VISIBLE);
                            view3.setVisibility(View.INVISIBLE);

                            break;
                        case 100:
                            view1.setVisibility(View.VISIBLE);
                            view2.setVisibility(View.VISIBLE);
                            view3.setVisibility(View.VISIBLE);
                            Deposit_cash.setText("This payment is complete");

                            if (CatSrtuas == 1){
                                Deposit_cash.setText("This payment is complete");
                                Btn_State = 3;
                                finish_btn.setVisibility(View.GONE);

                            }else if (CatSrtuas==2){

                                Deposit_cash.setText("Item Delivered");
                                finish_btn.setVisibility(View.GONE);
                            }
                            break;
                        default:
                            //default

                    }

                    if (pay_status == 0){

//                        Deposit_cash.setText("Cash not yet Deposited");

                    }


                }


            }
        });

    }




}
