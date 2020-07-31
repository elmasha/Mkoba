package com.el.mkoba;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_Fragment extends Fragment {
    View view;
    Context context;
    private TextView close_Mkoba,open_Mkobs,Profile_name,Mkoba_id,Balance,boostBalance,createPayment;
    private GridLayout gridLayout;
    private FirebaseAuth mAuth;
    private String name;
    public static String mkobaID;
    private double balance;
    private ProgressBar progress_bar;
    long mills;
    private AlertDialog dialog2;

    private TextView dialog_name,dialog_id,log_out,email;
    private EditText dialog_amount;
    private Button dialog_btn;
    private RelativeLayout relativeLayout;
    private RecyclerView mRecyclerView;
    private CircleImageView circleImageView;

    private TabLayout tabLayout;
    private TabItem myTrans,Incoming;

    LinearLayoutManager layoutManager;
    private MytransPagerAdapter adapter23;
    private ViewPager viewPager;



    private TransAdapter adapter;

    private String user_id;



    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference TransactionRef = db.collection("Mkoba_Transactions");
    CollectionReference MkobaRef = db.collection("Mkoba_Account");
    CollectionReference PaymentsRef = db.collection("Payments");
    CollectionReference TransactionsRef = db.collection("All_Transactions");
    public Profile_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view = inflater.inflate(R.layout.fragment_profile_, container, false);


        Profile_name = view.findViewById(R.id.Profile_name);
        Mkoba_id = view.findViewById(R.id.Mkoba_ID);
        Balance = view.findViewById(R.id.Amount12);
        log_out = view.findViewById(R.id.Logout);
        relativeLayout = view.findViewById(R.id.LayoutProfile);
        mAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.email);
        createPayment = view.findViewById(R.id.CreatePay);
        circleImageView = view.findViewById(R.id.Profile_img);
        tabLayout = view.findViewById(R.id.tab_layout2);
        myTrans = view.findViewById(R.id.My_tarnstab);
        Incoming = view.findViewById(R.id.Connect_transtab);
        viewPager = view.findViewById(R.id.ViewPager122);


        progress_bar = view.findViewById(R.id.Progress_Dbounce);

        DoubleBounce doubleBounce = new DoubleBounce();
        progress_bar.setIndeterminateDrawable(doubleBounce);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);



        adapter23 = new MytransPagerAdapter(getFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter23);


        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 1){

                }else if (tab.getPosition() == 2){

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));






        TextPaint paint = createPayment.getPaint();
        float width = paint.measureText("MariaMaria");

        Shader textShader = new LinearGradient(0, 0, width, createPayment.getTextSize(),
                new int[]{
                        Color.parseColor("#14bfd9"),
                        Color.parseColor("#7658A5"),
                        Color.parseColor("#E930B5"),
                }, null, Shader.TileMode.CLAMP);
        createPayment.getPaint().setShader(textShader);


//        setUpRecyclerView();
        LoadDetails();



        createPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(),PayMethodActivity.class));

            }
        });


        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Logout_Alert();
            }
        });




        return view;
    }








    private void Log_Out() {

        Map<String,Object> logout = new HashMap<>();
        logout.put("device_token",FieldValue.delete());

        String current_user = mAuth.getCurrentUser().getUid();

        MkobaRef.document(current_user).update(logout).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    mAuth.signOut();
                    Intent logout = new Intent(getContext(),LogInActivity.class);
                    logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(logout);


                }else{

                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });




    }







    private void LoadDetails() {


        String User_id = mAuth.getCurrentUser().getUid();

        MkobaRef.whereEqualTo("User_ID",User_id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots,
                                @javax.annotation.Nullable FirebaseFirestoreException e) {

                if (e != null) {

                    return;
                }



                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){

                    Mkoba_Account account = documentSnapshot.toObject(Mkoba_Account.class);
                    name = account.getFirst_Name()+" "+account.getLast_Name();
                    Profile_name.setText(name);
                    mkobaID = account.getMkoba_ID();
                    Mkoba_id.setText("_"+account.getPhone());
                    balance = account.getBalance();
                    email.setText(account.getEmail());
                    Balance.setText(balance+"");
                    String image = account.getProfile_Image();
                    if (image != null){

                        Picasso.with(getContext()).load(image).placeholder(R.drawable.load).error(R.drawable.errorimage).into(circleImageView);

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






                } else {

                }



            }
        });



    }



    public void Logout_Alert() {

        Date currentTime = Calendar.getInstance().getTime();
        String date = DateFormat.format("dd MMM ,yyyy | hh:mm a",new Date(String.valueOf(currentTime))).toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        dialog2 = builder.create();
        dialog2.show();
        builder.setMessage("Are you sure to Log out..\n");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Log_Out();

                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog2.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }




    public void showSnackBaroffline(Context context) {
        Snackbar snackbar = Snackbar.make(relativeLayout, "Offline: Check Network Connection", Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    public void showSnackBarOnline(Context context) {
        Snackbar snackbar = Snackbar.make(relativeLayout, "Back Online", Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo data = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


            if (data != null && data.isConnected() || wifi != null && wifi.isConnected())
                return true;
            else return false;


        } else {
            return false;
        }


    }

    private void DialogDeposit() {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.deposit_dialog, null);
        mbuilder.setView(mView);
        final AlertDialog dialog11 = mbuilder.create();
        dialog11.show();

        dialog_name = mView.findViewById(R.id.D_name);
        dialog_id = mView.findViewById(R.id.D_id);
        dialog_btn = mView.findViewById(R.id.stk_push);
        dialog_amount = mView.findViewById(R.id.Input_number);


        dialog_name.setText(name);
        dialog_id.setText("Mkoba_ID_"+mkobaID);


        dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int  Deposit = (int) Double.parseDouble(String.valueOf(Integer.parseInt(dialog_amount.getText().toString())));

                if (Deposit == 0.0){

                    Toast.makeText(getContext(), "Invalid Input 0.0 deci", Toast.LENGTH_SHORT).show();
                }
                if (Deposit == 0.0){

                    Toast.makeText(getContext(), "Invalid Input 0.0 deci", Toast.LENGTH_SHORT).show();
                }
                    else {

                        dialog11.dismiss();
                        progress_bar.setVisibility(View.VISIBLE);
                    Balance.setVisibility(View.INVISIBLE);
                    DepositBatchProccess(Deposit);


                }

            }
        });




    }

    private void DepositBatchProccess(final int deposit) {

        final String user_id = mAuth.getCurrentUser().getUid();

        double totalDeposit = balance + deposit;

        WriteBatch batch = db.batch();
        DocumentReference doc1 = MkobaRef.document(user_id);

        batch.update(doc1, "balance", totalDeposit);
        batch.commit()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            db.runTransaction(new com.google.firebase.firestore.Transaction.Function<Object>() {
                                @androidx.annotation.Nullable
                                @Override
                                public Object apply(@NonNull com.google.firebase.firestore.Transaction transaction) throws FirebaseFirestoreException {

                                    DocumentReference doc1 = PaymentsRef.document();
                                    String transaction_type = "Mpesa Payment".toString();
                                    Map<String, Object> trance = new HashMap<>();
                                    trance.put("User_name", name);
                                    trance.put("payment_method", transaction_type);
                                    trance.put("payment_description", "Complete");
                                    trance.put("mkoba_ID", deposit);
                                    trance.put("User_id", user_id);
                                    trance.put("amount", deposit);
                                    trance.put("timestamp", FieldValue.serverTimestamp());
                                    transaction.set(doc1, trance);
                                    return null;
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Object>() {
                                @Override
                                public void onComplete(@NonNull Task<Object> task) {

                                    Snackbar snackbar = Snackbar.make(relativeLayout, "Balance Boosted", Snackbar.LENGTH_LONG);
                                    snackbar.show();

                                    progress_bar.setVisibility(View.GONE);
                                    Balance.setVisibility(View.VISIBLE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Transaction Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                    progress_bar.setVisibility(View.GONE);
                                }
                            });

                        } else {
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Invalid Transaction", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            progress_bar.setVisibility(View.GONE);
                            Balance.setVisibility(View.VISIBLE);
                        }

                    }//end Task
                });

    }


//    public static class TransViewHolder  extends RecyclerView.ViewHolder{
//
//        View mView;
//
//        TextView name,date,amount,transDescription,status,deadline;
//
//        public TransViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            name = itemView.findViewById(R.id.Trans_name);
//            amount = itemView.findViewById(R.id.Trans_Cr_amount);
//            date  = itemView.findViewById(R.id.Trans_date);
//            deadline = itemView.findViewById(R.id.Trans_deadline);
//            status = itemView.findViewById(R.id.Trans_status);
//            transDescription= itemView.findViewById(R.id.Trans_description);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    mClickListener.onItemClick(v,getAdapterPosition());
//                }
//            });
//
//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    mClickListener.onItemLongClick(v,getAdapterPosition());
//                    return false;
//
//
//                }
//            });
//
//        }
//
//        private ClickListener mClickListener;
//
//        public interface ClickListener{
//            void onItemClick(View view, int position);
//            void onItemLongClick(View view, int position);
//
//        }
//
//        public void setOnClickListener(ClickListener  clickListener){
//
//            mClickListener = clickListener;
//
//        }
//
//
//
//    }


    @Override
    public void onStart() {
        super.onStart();

          //  adapter.startListening();

            //
//        String current_user = mAuth.getCurrentUser().getUid();
//
//        if (current_user != null){
//
//
//        }else {
//
//        }

    }

    @Override
    public void onStop() {
        super.onStop();
            //adapter.stopListening();

    }
}
