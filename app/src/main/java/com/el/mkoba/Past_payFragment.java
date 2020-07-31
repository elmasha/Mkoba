package com.el.mkoba;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class Past_payFragment extends Fragment {
    View root;
    private TransAdapter adapter;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference TransactionRef = db.collection("Mkoba_Transactions");

    public Past_payFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_past_pay, container, false);
        mAuth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.Past_recycler);


        setUpRecyclerView();

        return root;
    }

    private void setUpRecyclerView() {


        String user_id = mAuth.getCurrentUser().getUid();

        Query query = TransactionRef.orderBy("timestamp", Query.Direction.DESCENDING)
                .whereEqualTo("Trans_UID" ,user_id)
                .whereEqualTo("Status_percent",100)
                .limit(15);
        FirestoreRecyclerOptions<Mkoba_Transactions> options = new FirestoreRecyclerOptions.Builder<Mkoba_Transactions>()
                .setQuery(query, Mkoba_Transactions.class)
                .build();
        adapter = new TransAdapter(options);


        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        adapter.notifyDataSetChanged();




        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Payment");
                builder.setMessage("Are you sure you want to delete this item ?");
                builder.setIcon(R.drawable.trash);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.deleteItem(viewHolder.getAdapterPosition());
                                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.setCancelable(false);
                builder.show();


            }
        }).attachToRecyclerView(recyclerView);





        adapter.setOnItemClickListener(new TransAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Payment");
                builder.setMessage("Are you sure you want to delete this item ?");
                builder.setIcon(R.drawable.trash);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.deleteItem(position);
                                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.setCancelable(false);
                builder.show();

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
