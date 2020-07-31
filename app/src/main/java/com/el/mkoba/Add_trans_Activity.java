package com.el.mkoba;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Add_trans_Activity extends AppCompatActivity  {


    private TextInputLayout Input_number, Input_deadline, Input_description, Input_amount,Input_title;
    int Amount;
    private Button Brn_start;
    private String rcpt_no,dead_line,desc;

    private FirebaseAuth mAuth;
    private RelativeLayout relativeLayout;

    private TextView Rate_price,connectUser;

    double intrest;
    double rate = 0.05;
    int TOTAL;
    int RateCount = 0;
    private AlertDialog dialog2,dialog3;

    private RadioGroup radioGroup;
    private FloatingActionButton connectUsers;
    private RadioButton radioButton;

    private String category = "";
    private int status = 0;

    private EditText phone_number;
    private TextView payName,payDI,payCategory,payAmount,payTotalPrice,cancel;
    private Button conPayment;

    String name;
    String mkobaID,number;

    private String Mpesa_number;

    private ProgressBar progressBar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference TransactionRef = db.collection("Mkoba_Transactions");
    CollectionReference MkobaRef = db.collection("Mkoba_Account");
    CollectionReference Notifocation = db.collection("Mkoba_Account");


    private String Deposit_amount,TransName,TransPhone,TransImage,TransUID,TransTitle;

    Daraja daraja;

    private Uri  ImageUri;


    private TextView show_text,deposit;
    private  int pay_method;

    private CircleImageView transImage;
    private  TextView trsndName,transPhone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trans_);


        show_text = findViewById(R.id.New_trans);



        if (getIntent() != null){

            pay_method = getIntent().getIntExtra("Mpesa",0);

            if (pay_method == 1){

                show_text.setText("Mpesa");


            }else if (pay_method ==2){

                show_text.setText("Equity");
            }else if (pay_method ==3){

                show_text.setText("Visa");


            }else if (pay_method ==4){

                show_text.setText("Airtel");

            }


        }

        Input_number = findViewById(R.id.Add_no);
        Input_title = findViewById(R.id.Add_Title);
        Input_deadline = findViewById(R.id.Add_deadline);
        Input_description = findViewById(R.id.Add_description);
        Input_amount = findViewById(R.id.Add_amount);
        Brn_start = findViewById(R.id.btm_Add_trans);
        relativeLayout = findViewById(R.id.R13);
        radioGroup = findViewById(R.id.Radio_Grp);
        Rate_price = findViewById(R.id.Rate_amount);
        deposit = findViewById(R.id.Payment_method);
        connectUsers = findViewById(R.id.ConnectUser);
        connectUser = findViewById(R.id.ConnectUser2);







        daraja = Daraja.with("gd3m1FmOy9rgH2oZ8RRGrcRXStIkHtqw", "SspIror3sJ0xDurp", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
//                Log.i(getContext().getClass().getSimpleName(), accessToken.getAccess_token());
//                Toast.makeText(getContext(), "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(getApplicationContext().getClass().getSimpleName(), error);
            }
        });


        TextPaint paint = Rate_price.getPaint();
        float width = paint.measureText("10000");


        Shader textShader = new LinearGradient(0, 0, width, Rate_price.getTextSize(),
                new int[]{
                        Color.parseColor("#12A7d7"),
                        Color.parseColor("#5C68B0"),
                        Color.parseColor("#7658A5"),
                        Color.parseColor("#C227FD"),
                }, null, Shader.TileMode.CLAMP);
        Rate_price.getPaint().setShader(textShader);

        mAuth = FirebaseAuth.getInstance();

        LoadDetails();



        Input_deadline.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showdialogDate();

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId){
                    case R.id.Buyer:
                        category = "Buyer";
                        connectUser.setText("Connect Seller");
                        status = 1;

                        break;
                    case R.id.Seller:
                        category = "Seller";
                        connectUser.setText("Connect Buyer");
                        status = 2;
                        break;

                }
            }
        });

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (category.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Choose either Buyer or Seller", Toast.LENGTH_LONG).show();

                }else {

//                    showDialog();
                    calculateRates();
                }
            }
        });



        connectUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                rcpt_no = Input_number.getEditText().getText().toString();

                if (rcpt_no.isEmpty()){

                    Toast.makeText(Add_trans_Activity.this, "Please provide a recipient number", Toast.LENGTH_SHORT).show();


                }else if (category.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Choose either Buyer or Seller", Toast.LENGTH_LONG).show();

                }else {
                    calculateRates();
                    LoadRcptDetails();
                }
            }
        });





        Brn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!ValidaTion()){

                    calculateRates();
                } else if (rcpt_no.isEmpty()){

                    Toast.makeText(Add_trans_Activity.this, "Please provide a recipient number", Toast.LENGTH_SHORT).show();


                }else if (category.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Choose either Buyer or Seller", Toast.LENGTH_LONG).show();

                }else {

                    calculateRates();
                    LoadRcptDetails();

                }

            }
        });


        Rate_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (category.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Choose either Buyer or Seller", Toast.LENGTH_LONG).show();

                }else {

                    //showDialog();
                    calculateRates();
                }

            }
        });

    }









    private void showdialogDate() {

        Calendar calendar = Calendar.getInstance();
        MonthYearPickerDialog pd = MonthYearPickerDialog.newInstance(calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.YEAR));

        pd.setListener(new android.app.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedyear, int selectedmonth, int dayOfMonth) {


                String formatedDate = "";

                String currentDateFormat = selectedmonth + "/" + dayOfMonth + "/" + selectedyear;  //"MM/dd/yyyy

                Input_deadline.getEditText().setText(currentDateFormat);

                if(selectedyear == 1904){



                }else {

                }
            }
        });

        pd.show(getSupportFragmentManager(), "Pick a deadline");

    }




    private void LoadDetails() {

        String User_id = mAuth.getCurrentUser().getUid();

        MkobaRef.document(User_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                    return;
                }

                if (documentSnapshot.exists()) {

                    Mkoba_Account account = documentSnapshot.toObject(Mkoba_Account.class);
                    name = account.getFirst_Name()+" "+account.getLast_Name();
                    mkobaID = account.getMkoba_ID();
                    number = account.getPhone();

                } else {

                }



            }
        });


    }



    private void LoadRcptDetails() {

        String User_id = mAuth.getCurrentUser().getUid();

        MkobaRef.whereEqualTo("Phone",rcpt_no).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {

                    return;
                }


                for (DocumentSnapshot  doc : queryDocumentSnapshots.getDocuments()){


                    if (doc.exists()){

                        Mkoba_Account account = doc.toObject(Mkoba_Account.class);
                        TransName = account.getFirst_Name()+" "+ account.getLast_Name();
                        TransImage = account.getProfile_Image();
                        TransPhone = account.getPhone();
                        TransUID = account.getUser_ID();
                        String image = account.getProfile_Image();
                        Dialogue_connect();

                    }else {

                        Toast.makeText(Add_trans_Activity.this, "Recipient number is't available..", Toast.LENGTH_SHORT).show();
                    }




                }




            }
        });


        MkobaRef.document(User_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {

                    return;
                }

                if (documentSnapshot.exists()) {

                    Mkoba_Account account = documentSnapshot.toObject(Mkoba_Account.class);
                    name = account.getFirst_Name()+" "+account.getLast_Name();
                    mkobaID = account.getMkoba_ID();
                    number = account.getPhone();

                } else {

                }



            }
        });


    }



    //Validation
    private boolean ValidaTion() {

        rcpt_no = Input_number.getEditText().getText().toString();
        dead_line = Input_deadline.getEditText().getText().toString();
        desc = Input_description.getEditText().getText().toString();
        Amount = (int) Double.parseDouble(Input_amount.getEditText().getText().toString());
        TransTitle = Input_title.getEditText().getText().toString();

        if (rcpt_no.isEmpty()) {
            Input_number.setError("Recipient is required");

            return false;
        }
        else if (TransTitle.isEmpty()) {
            Input_title.setError("Recipient is required");

            return false;
        }

        else if (dead_line.isEmpty()) {
            Input_deadline.setError("Deadline is required");

            return false;
        }
        else if (Amount == 0) {
            Input_amount.setError("Wrong input '0' ");

            return false;
        }

        else if (desc.isEmpty()) {
            Input_description.setError("Description is Empty");

            return false;
        }

        else if (category.isEmpty()) {

            Toast.makeText(getApplicationContext(), "Choose either Buyer or Seller", Toast.LENGTH_LONG).show();


            return false;
        }

        else if (rcpt_no.length() >=12) {
            Input_description.setError("Maximum is 12 words");
            return false;

        }
        else if (desc.length() ==20) {
            Input_description.setError("Maximum is 20 words");
            return false;

        }
        else if (desc.length() ==300) {
            Input_description.setError("Maximum is 300 words");
            return false;

        }else if (TransTitle.length() == 30) {
            Input_description.setError("Maximum is 30 words");
            return false;

        }
        else {
            Input_description.setError(null);
            Input_number.setError(null);
            return true;
        }

    }



    private void calculateRates(){

        if (Amount == 0){

        }else {

            intrest = rate * Amount;
            TOTAL = (int) (intrest + Amount);

            RateCount = (int) (TOTAL - Amount);

            Rate_price.setText(TOTAL + "");
            deposit.setText("Rates @ 5% = "+RateCount+"");
        }

    }

    private void UploadTransaction() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading please wait..");
        progressDialog.show();


        rcpt_no = Input_number.getEditText().getText().toString();
        dead_line = Input_deadline.getEditText().getText().toString();
        desc = Input_description.getEditText().getText().toString();
        Amount = (int) Double.parseDouble(Input_amount.getEditText().getText().toString());

        String User_ID = mAuth.getCurrentUser().getUid();


        Random dice = new Random();
        int AccountNO =dice.nextInt(100000000)+4;
        final String pay_ID = String.valueOf(AccountNO);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        double Total_rate = Double.parseDouble(df.format(TOTAL));


        final String Doc_id = TransactionRef.document().getId();

        final HashMap<String,Object> store = new HashMap<>();
        store.put("Recipient_No",rcpt_no);
        store.put("Dead_line",dead_line);
        store.put("Description",desc);
        store.put("Amount",Amount);
        store.put("Total_price",Total_rate);
        store.put("User_ID",User_ID);
        store.put("Doc_Id",Doc_id);
        store.put("Trans_UID",TransUID);
        store.put("Trans_Name",name);
        store.put("Trans_Phone",number);
        store.put("Mkoba_ID",Profile_Fragment.mkobaID);
        store.put("Category",category);
        store.put("Status",status);
        store.put("Payment_Title",TransTitle);
        store.put("Status_percent",40);
        store.put("Payment_status",0);
        store.put("Payment_Id","#"+pay_ID);
        store.put("timestamp", FieldValue.serverTimestamp());


        HashMap<String,Object> notify = new HashMap<>();
        notify.put("Recipient_No",rcpt_no);
        notify.put("type","New payment: "+TransTitle);
        notify.put("to",TransUID);
        notify.put("body",desc);
        notify.put("User_id",User_ID);
        notify.put("doc_ID",Doc_id);
        notify.put("timestamp", FieldValue.serverTimestamp());



        MkobaRef.document(User_ID).collection("Notification").document().set(notify).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    TransactionRef.document(Doc_id).set(store);


                    Snackbar snackbar = Snackbar.make(relativeLayout, "Created successfully...", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    dialog3.dismiss();
                    progressDialog.dismiss();
                    Brn_start.setVisibility(View.VISIBLE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Brn_start.setVisibility(View.VISIBLE);
                Snackbar snackbar = Snackbar.make(relativeLayout, e.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
                dialog3.dismiss();
                progressDialog.dismiss();


            }

        });

    }


    private void Dialogue_connect(){
        final  AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_newtrans, null);
        mbuilder.setView(mView);

        dialog3 = mbuilder.create();
        dialog3.show();
        Brn_start.setVisibility(View.INVISIBLE);


        transImage = mView.findViewById(R.id.trans_image);
        trsndName = mView.findViewById(R.id.trans_name);
        transPhone = mView.findViewById(R.id.trans_number);


        Button confirm_trans = mView.findViewById(R.id.confirm_trans);
        TextView cancel_trans = mView.findViewById(R.id.cancel_trans);


        cancel_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog3.dismiss();
                Brn_start.setVisibility(View.VISIBLE);

            }
        });

        confirm_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rcpt_no = Input_number.getEditText().getText().toString();
                dead_line = Input_deadline.getEditText().getText().toString();
                desc = Input_description.getEditText().getText().toString();
                Amount = (int) Double.parseDouble(Input_amount.getEditText().getText().toString());
                TransTitle = Input_title.getEditText().getText().toString();

                if (!ValidaTion()){

                    calculateRates();
                    dialog3.dismiss();
                    Brn_start.setVisibility(View.VISIBLE);
                }else {

                    calculateRates();
                    UploadTransaction();

                }
            }
        });


        if (TransImage != null){

            Picasso.with(this).load(TransImage).placeholder(R.drawable.load).error(R.drawable.user_profile).into(transImage);

        }

        trsndName.setText(TransName);
        transPhone.setText(TransPhone);


    }

//Dialogue Mpesa

//    private void showDialog(){
//
//
//        final  AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
//        View mView = getLayoutInflater().inflate(R.layout.dialog_mpesatranaction, null);
//        mbuilder.setView(mView);
//
//        dialog2 = mbuilder.create();
//        dialog2.setCancelable(false);
//        dialog2.show();
//
//
//        payName = mView.findViewById(R.id.Payment_name);
//        payDI = mView.findViewById(R.id.Payment_id);
//        payCategory = mView.findViewById(R.id.Payment_category);
//        payAmount = mView.findViewById(R.id.Payment_amount);
//        payTotalPrice = mView.findViewById(R.id.Payment_total_price);
//        conPayment = mView.findViewById(R.id.Confirm_Pay);
//        cancel = mView.findViewById(R.id.PayCancel);
//        phone_number = mView.findViewById(R.id.Payment_number);
//        progressBar = mView.findViewById(R.id.pay_progress);
//
//        DoubleBounce doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);
//
//
//
//        payName.setText(name);
//        payDI.setText("Mkoba_ID_"+mkobaID);
//        phone_number.setText(number);
//        Mpesa_number = phone_number.getText().toString();
//
//
//        if (category == "Buyer"){
//
//            payTotalPrice.setText(TOTAL+"");
//            payCategory.setText(category);
//
//
//            Deposit_amount = String.valueOf(TOTAL);
//
//        }else if (category == "Seller"){
//
//            payAmount.setText(Amount+"");
//            payCategory.setText(category);
//
//            Deposit_amount = String.valueOf(Amount);
//
//
//        }
//
//
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog2.dismiss();
//            }
//        });
//
//        conPayment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                PayMent(Mpesa_number);
//
//            }
//        });
//
//
//    }




    private void PayMent(String mpesa_number) {

        //Get Phone Number from User Input

        if (TextUtils.isEmpty(mpesa_number)) {
            Toast.makeText(getApplicationContext(), "Please Provide a Phone Number", Toast.LENGTH_SHORT).show();

        }else{

            progressBar.setVisibility(View.VISIBLE);
            conPayment.setEnabled(false);
            conPayment.setVisibility(View.INVISIBLE);

            //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
            LNMExpress lnmExpress = new LNMExpress(
                    "174379",
                    "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",
                    //https://developer.safaricom.co.ke/test_credentials
                    TransactionType.CustomerPayBillOnline,
                    Deposit_amount,
                    "254708374149",
                    "174379",
                    mpesa_number,
                    "http://mycallbackurl.com/checkout.php",
                    "174379",
                    "Goods Payment"
            );


            daraja.requestMPESAExpress(lnmExpress,
                    new DarajaListener<LNMResult>() {
                        @Override
                        public void onResult(@NonNull LNMResult lnmResult) {
                            Log.i(getApplicationContext().getClass().getSimpleName(), lnmResult.ResponseDescription);

                            if (lnmResult.ResponseCode == "0") {
                                progressBar.setVisibility(View.GONE);

                                Snackbar snackbar = Snackbar.make(relativeLayout, "Amount deposited successfully", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                conPayment.setEnabled(true);
                                conPayment.setVisibility(View.VISIBLE);
                            }else {

                                Toast.makeText(getApplicationContext(), "Something wrong Occurred.... \n"+
                                        "Check your balance \n Check network ", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                conPayment.setEnabled(true);
                                conPayment.setVisibility(View.VISIBLE);

                            }


                        }

                        @Override
                        public void onError(String error) {
                            Log.i(getApplicationContext().getClass().getSimpleName(), error);
                            progressBar.setVisibility(View.GONE);
                            conPayment.setEnabled(true);
                            conPayment.setVisibility(View.VISIBLE);
                        }
                    }
            );


        }

    }









}
