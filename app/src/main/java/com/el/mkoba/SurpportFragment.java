package com.el.mkoba;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurpportFragment extends Fragment {
View root;

        private TextView email_us,chat_us,call_us,terms,back;
        FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference MkobaRef = db.collection("Mkoba_Account");
    private String name;

    public SurpportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_surpport, container, false);

        email_us = root.findViewById(R.id.Email_us);
        chat_us = root.findViewById(R.id.Chat_us);
        call_us = root.findViewById(R.id.Call_us);
        terms = root.findViewById(R.id.terms_condition);
        back = root.findViewById(R.id.Support_back);




        mAuth = FirebaseAuth.getInstance();




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });


        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Terms_and_coditions.class));
            }
        });

        email_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goTOUrl("https://mkobaapp@gmail.com");


            }
        });

        chat_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto:0700290968");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", "Hi! "+name +" talk to us...");
                startActivity(intent);
            }
        });

        call_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0700290968"));
                startActivity(intent);
            }
        });


        LoadDetails();


        return root;
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
                    name = account.getFirst_Name();

                    String image = account.getProfile_Image();
                    if (image != null){

                      //  Picasso.with(getContext()).load(image).placeholder(R.drawable.load).error(R.drawable.errorimage).into(circleImageView);

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


    private void goTOUrl(String s) {
    Uri uri = Uri.parse(s);
    startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }

}
