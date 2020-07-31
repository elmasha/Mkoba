package com.el.mkoba;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {





    private RecyclerView recyclerView;
    private RecyclerView mRecyclerView;
    private FirebaseAuth mAuth;

    private FirestoreRecyclerAdapter<Payments, PaymentViewHolder> adapter1;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference CategoriesnRef = db.collection("Categories");

    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_, container, false);


//        recyclerView = view.findViewById(R.id.Categories_recyclerView);
//        mRecyclerView = view.findViewById(R.id.Home_recyclerView);
//        mAuth = FirebaseAuth.getInstance();
//        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getContext(),
//                LinearLayoutManager.HORIZONTAL, false);
//
//        recyclerView.setLayoutManager(horizontalLayoutManagaer);
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//


        return view;


    }





    @Override
    public void onStart() {
        super.onStart();
//        adapter.startListening();
//        adapter1.startListening();

    }


    @Override
    public void onStop() {
        super.onStop();
//        adapter.stopListening();
//        adapter1.stopListening();

    }
}

