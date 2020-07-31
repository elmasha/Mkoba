package com.el.mkoba;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class PendingPay_Fragment extends Fragment {


    private TransAdapter adapter;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference TransactionRef = db.collection("Mkoba_Transactions");

    public PendingPay_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_pending_, container, false);

        mAuth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.Pending_Recyclerview);


        setUpRecyclerView();
        return root;
    }


    private void setUpRecyclerView() {

        String user_id = mAuth.getCurrentUser().getUid();

        Query query = TransactionRef.orderBy("timestamp", Query.Direction.DESCENDING)
                .whereEqualTo("Trans_UID" ,user_id)
                .whereEqualTo("Status_percent" ,40)
                .limit(20);
        FirestoreRecyclerOptions<Mkoba_Transactions> options = new FirestoreRecyclerOptions.Builder<Mkoba_Transactions>()
                .setQuery(query, Mkoba_Transactions.class)
                .build();
        adapter = new TransAdapter(options);


        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new TransAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Mkoba_Transactions note = documentSnapshot.toObject(Mkoba_Transactions.class);
                String id = documentSnapshot.getId();
                Intent toTrnsView = new Intent(getContext(),TransViewActivity.class);
                toTrnsView.putExtra("Doc_ID",id);
                startActivity(toTrnsView);
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
