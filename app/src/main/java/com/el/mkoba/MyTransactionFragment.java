package com.el.mkoba;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTransactionFragment extends Fragment {
View view;


    private Button dialog_btn;
    private RelativeLayout relativeLayout;
    private RecyclerView mRecyclerView;
    private CircleImageView circleImageView;
    private FirebaseAuth mAuth;

    private TabLayout tabLayout;
    private TabItem myTrans,Incoming;

    LinearLayoutManager layoutManager;
    private MytransPagerAdapter adapter23;
    private ViewPager viewPager;



    private MyTransAdapter adapter;

    private String user_id;



    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference TransactionRef = db.collection("Mkoba_Transactions");
    public MyTransactionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_transaction, container, false);
        mAuth = FirebaseAuth.getInstance();

        mRecyclerView = view.findViewById(R.id.MyTransactions);

        setUpRecyclerView();


        return view;
    }


    private void setUpRecyclerView() {

        String user_id = mAuth.getCurrentUser().getUid();

        Query query = TransactionRef.orderBy("timestamp", Query.Direction.DESCENDING)
                .whereEqualTo("User_ID" ,user_id)
                .limit(20);
        FirestoreRecyclerOptions<Mkoba_Transactions> options = new FirestoreRecyclerOptions.Builder<Mkoba_Transactions>()
                .setQuery(query, Mkoba_Transactions.class)
                .build();
        adapter = new MyTransAdapter(options);


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Item");
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
        }).attachToRecyclerView(mRecyclerView);



        adapter.setOnItemClickListener(new MyTransAdapter.OnItemClickListener() {
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
